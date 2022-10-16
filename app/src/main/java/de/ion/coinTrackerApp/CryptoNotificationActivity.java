package de.ion.coinTrackerApp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import de.ion.coinTrackerApp.animation.AnimationImageZoomInService;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.database.DatabaseCryptoRepository;
import de.ion.coinTrackerApp.database.DatabaseNotificationRepository;
import de.ion.coinTrackerApp.database.SQLiteCryptoRepository;
import de.ion.coinTrackerApp.database.SQLiteNotificationRepository;
import de.ion.coinTrackerApp.notification.entity.NotificationData;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.service.background.BackgroundService;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;

public class CryptoNotificationActivity extends AppCompatActivity implements Activity {
    private EditText inputCryptoPrice;
    private ImageView toolbarBitcoinImg;
    private Button trackBtn;
    private Button endTrackBtn;

    private CryptoSingleton cryptoSingleton;
    private SettingsSingleton settingsSingleton;
    private NotificationSingleton notificationSingleton;

    private DatabaseCryptoRepository sqLiteCryptoRepository;
    private DatabaseNotificationRepository sqLiteNotificationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notification);

        init();

        if (this.settingsSingleton.getSettingsData().getPriceOption().equals("USD")) {
            Drawable img = this.inputCryptoPrice.getContext().getResources().getDrawable(R.drawable.ic_currency_dollar);
            this.inputCryptoPrice.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        } else if (this.settingsSingleton.getSettingsData().getPriceOption().equals("Prozent")) {
            Drawable img = this.inputCryptoPrice.getContext().getResources().getDrawable(R.drawable.ic_offer_9678);
            this.inputCryptoPrice.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(() -> {
                            if (notificationSingleton.getNotificationData().getInputCryptoLimit() != 0.0 ||
                                    !notificationSingleton.getNotificationData().isWaitingForWarning()) {
                                inputCryptoPrice.setVisibility(View.GONE);
                                trackBtn.setVisibility(View.GONE);
                                endTrackBtn.setVisibility(View.VISIBLE);
                            } else {
                                inputCryptoPrice.setVisibility(View.VISIBLE);
                                trackBtn.setVisibility(View.VISIBLE);
                                trackBtn.setText("Warnung erstellen");
                                endTrackBtn.setVisibility(View.GONE);
                            }
                        });

                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * @param view
     */
    public void startNewService(View view) {
        if (!this.inputCryptoPrice.getText().toString().equals("")) {
            NotificationData notificationData = notificationSingleton.getNotificationData();
            if (this.settingsSingleton.getSettingsData().getPriceOption().equals("USD")) {
                notificationData.setInputCryptoPrice(this.cryptoSingleton.getCryptoData().getCurrentCryptoPrice());
                notificationData.setInputCryptoLimit(Integer.valueOf(this.inputCryptoPrice.getText().toString()));
            } else if (this.settingsSingleton.getSettingsData().getPriceOption().equals("Prozent")) {
                double currentPrice = this.cryptoSingleton.getCryptoData().getCurrentCryptoPrice();
                double inputLimit = Double.parseDouble(this.inputCryptoPrice.getText().toString());
                notificationData.setInputCryptoPrice(currentPrice);
                notificationData.setInputCryptoLimit((int) (inputLimit / 100 * currentPrice));
            }

            notificationData.setCryptoName(cryptoSingleton.getCryptoData().getCryptoName());
            notificationData.shouldWaitingForWarning(true);

            this.notificationSingleton.setNotificationData(notificationData);

            this.sqLiteCryptoRepository.persist(this.cryptoSingleton.getCryptoData());
            this.sqLiteNotificationRepository.persist(this.notificationSingleton.getNotificationData());

            startService(new Intent(this, BackgroundService.class));

            Intent cryptoNotificationIntent = new Intent(CryptoNotificationActivity.this, MainActivity.class);
            startActivity(cryptoNotificationIntent);
        }
    }

    public void stopNewService(View view) {
        NotificationData notificationData = notificationSingleton.getNotificationData();
        notificationData.setInputCryptoPrice(0.0);
        notificationData.setInputCryptoLimit(0);
        notificationData.shouldWaitingForWarning(true);

        this.notificationSingleton.setNotificationData(notificationData);
        this.sqLiteNotificationRepository.persist(notificationSingleton.getNotificationData());

        Intent cryptoNotificationIntent = new Intent(CryptoNotificationActivity.this, MainActivity.class);
        startActivity(cryptoNotificationIntent);
    }


    @Override
    public void loadViews() {
        this.inputCryptoPrice = (EditText) findViewById(R.id.inputCryptoPrice);
        this.trackBtn = (Button) findViewById(R.id.trackBtn);
        this.endTrackBtn = (Button) findViewById(R.id.endTrackBtn);
        this.toolbarBitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
    }

    @Override
    public void initComponents() {
        new AnimationImageZoomInService(this, this.toolbarBitcoinImg);
    }

    @Override
    public void initDatabase() {
        this.sqLiteCryptoRepository = new SQLiteCryptoRepository(this);
        this.sqLiteNotificationRepository = new SQLiteNotificationRepository(this);
    }

    @Override
    public void initSingleton() {
        this.cryptoSingleton = CryptoDataSingleton.getInstance();
        this.settingsSingleton = SettingsSingleton.getInstance();
        this.notificationSingleton = NotificationSingleton.getInstance();
    }

    @Override
    public void initToolbar() {
    }

    @Override
    public void init() {
        loadViews();
        initComponents();
        initDatabase();
        initSingleton();
        initToolbar();
    }
}