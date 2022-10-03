package de.ion.coinTrackerApp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import de.ion.coinTrackerApp.animation.AnimationImageZoomIn;
import de.ion.coinTrackerApp.background.BackgroundService;
import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.database.DatabaseCryptoHelper;
import de.ion.coinTrackerApp.database.DatabaseNotificationHelper;
import de.ion.coinTrackerApp.notification.valueObject.NotificationData;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;

public class CryptoNotificationActivity extends AppCompatActivity implements Activity {
    private EditText inputBitcoinPrice;
    private ImageView toolbarBitcoinImg;
    private Button trackBtn;
    private Button endTrackBtn;

    private CryptoSingleton cryptoSingleton;
    private SettingsSingleton settingsSingleton;
    private NotificationSingleton notificationSingleton;

    private DatabaseCryptoHelper databaseCryptoHelper;
    private DatabaseNotificationHelper databaseNotificationHelper;

    private AnimationImageZoomIn animationImageZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notification);

        init();

        if (this.settingsSingleton.getSettingsData().getPriceOption().equals("USD ($)")) {
            Drawable img = this.inputBitcoinPrice.getContext().getResources().getDrawable(R.drawable.ic_currency_dollar);
            this.inputBitcoinPrice.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        } else if (this.settingsSingleton.getSettingsData().getPriceOption().equals("Prozent (%)")) {
            Drawable img = this.inputBitcoinPrice.getContext().getResources().getDrawable(R.drawable.ic_offer_9678);
            this.inputBitcoinPrice.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!notificationSingleton.getNotificationData().getInputCryptoLimit().equals("0")) {
                                    trackBtn.setText("Warnung updaten");
                                    endTrackBtn.setVisibility(View.VISIBLE);
                                } else {
                                    trackBtn.setText("Warnung aktivieren");
                                    endTrackBtn.setVisibility(View.GONE);
                                }
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
        if (!this.inputBitcoinPrice.getText().toString().equals("")) {
            NotificationData notificationData = notificationSingleton.getNotificationData();
            if (this.settingsSingleton.getSettingsData().getPriceOption().equals("USD ($)")) {
                notificationData.setInputCryptoPrice(this.cryptoSingleton.getBitcoinData().getCurrentCryptoPrice());
                notificationData.setInputCryptoLimit(this.inputBitcoinPrice.getText().toString());
            } else if (this.settingsSingleton.getSettingsData().getPriceOption().equals("Prozent (%)")) {
                double currentPrice = Double.parseDouble(this.cryptoSingleton.getBitcoinData().getCurrentCryptoPrice());
                double inputLimit = Double.parseDouble(this.inputBitcoinPrice.getText().toString());
                notificationData.setInputCryptoPrice(String.valueOf(currentPrice));
                notificationData.setInputCryptoLimit(String.valueOf(Double.parseDouble(String.valueOf(inputLimit / 100 * currentPrice))));
            }

            notificationData.setCryptoName(cryptoSingleton.getBitcoinData().getCryptoName());
            notificationData.setEndAllBackgroundServices("true");
            notificationData.shouldWaitingForWarning(true);

            this.notificationSingleton.setNotificationData(notificationData);

            this.databaseCryptoHelper.addCryptoDataToDatabase(this.cryptoSingleton.getBitcoinData());
            this.databaseNotificationHelper.addWarningDataToDatabase(this.notificationSingleton.getNotificationData());

            startService(new Intent(this, BackgroundService.class));

            Intent cryptoNotificationIntent = new Intent(CryptoNotificationActivity.this, MainActivity.class);
            startActivity(cryptoNotificationIntent);
        }
    }

    public void stopNewService(View view) {
        NotificationData notificationData = notificationSingleton.getNotificationData();
        notificationData.setInputCryptoPrice("0");
        notificationData.setInputCryptoLimit("0");
        notificationData.setEndAllBackgroundServices("true");
        notificationData.shouldWaitingForWarning(true);

        this.notificationSingleton.setNotificationData(notificationData);
        this.databaseNotificationHelper.addWarningDataToDatabase(notificationSingleton.getNotificationData());

        Intent cryptoNotificationIntent = new Intent(CryptoNotificationActivity.this, MainActivity.class);
        startActivity(cryptoNotificationIntent);
    }


    @Override
    public void loadViews() {
        this.inputBitcoinPrice = (EditText) findViewById(R.id.inputBitcoinPrice);
        this.trackBtn = (Button) findViewById(R.id.trackBtn);
        this.endTrackBtn = (Button) findViewById(R.id.endTrackBtn);
        this.toolbarBitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
    }

    @Override
    public void initComponents() {
        this.animationImageZoomIn = new AnimationImageZoomIn(this, this.toolbarBitcoinImg);
    }

    @Override
    public void initDatabase() {
        this.databaseCryptoHelper = new DatabaseCryptoHelper(this);
        this.databaseNotificationHelper = new DatabaseNotificationHelper(this);
    }

    @Override
    public void initSingleton() {
        this.cryptoSingleton = BitcoinSingleton.getInstance();
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