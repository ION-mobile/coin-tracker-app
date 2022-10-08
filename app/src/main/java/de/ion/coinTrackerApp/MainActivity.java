package de.ion.coinTrackerApp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.ion.coinTrackerApp.animation.AnimationImageZoomInService;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;

public class MainActivity extends AppCompatActivity implements Activity {
    private TextView currentPriceTxt;
    private TextView currentFearAndGreedTxt;
    private TextView inputPriceTxt;
    private ImageView toolbarBitcoinImg;
    private ImageButton toolbarOptions;

    private CryptoSingleton cryptoSingleton;
    private NotificationSingleton notificationSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        this.createNotificationChannel();

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(() -> {
                            currentPriceTxt.setText("Derzeitiger Stand: " + cryptoSingleton.getCryptoData().getCurrentCryptoPrice() + " $");
                            inputPriceTxt.setText("Bitcoin Warnung: -/+ " + notificationSingleton.getNotificationData().getInputCryptoLimit() + " $");
                            currentFearAndGreedTxt.setText("Fear And Greed Index: " + cryptoSingleton.getCryptoData().getFearAndGreedIndex());
                        });

                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void createNewNotification(View view) {
        Intent cryptoNotificationIntent = new Intent(MainActivity.this, CryptoNotificationActivity.class);
        startActivity(cryptoNotificationIntent);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("cryptoPriceNotification", "cryptoPriceNotificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel for bitcoin price notification");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displayOptionsMenu(View view) {
        PopupMenu optionsMenu = new PopupMenu(this, view);
        optionsMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.action_help) {
                Intent helpActivityIntent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helpActivityIntent);
            }

            if (id == R.id.action_settings) {
                Intent helpActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(helpActivityIntent);
            }
            return true;
        });
        optionsMenu.inflate(R.menu.menu_toolbar_crypto);
        optionsMenu.show();
    }

    public void loadViews() {
        this.currentPriceTxt = (TextView) findViewById(R.id.currentPriceTxt);
        this.currentFearAndGreedTxt = (TextView) findViewById(R.id.currentFearAndGreedValueTxt);
        this.inputPriceTxt = (TextView) findViewById(R.id.inputPriceTxt);
        this.currentPriceTxt = (TextView) findViewById(R.id.currentPriceTxt);
        this.currentFearAndGreedTxt = (TextView) findViewById(R.id.currentFearAndGreedValueTxt);
        this.toolbarBitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
        this.toolbarOptions = (ImageButton) findViewById(R.id.toolbarOptions);
    }

    @Override
    public void initComponents() {
        new AnimationImageZoomInService(this, this.toolbarBitcoinImg);
    }

    @Override
    public void initDatabase() {
    }

    @Override
    public void initSingleton() {
        this.cryptoSingleton = CryptoDataSingleton.getInstance();
        this.notificationSingleton = NotificationSingleton.getInstance();
    }

    @Override
    public void initToolbar() {
        this.toolbarOptions.setVisibility(View.VISIBLE);
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