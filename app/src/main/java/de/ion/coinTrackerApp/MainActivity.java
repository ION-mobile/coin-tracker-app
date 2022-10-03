package de.ion.coinTrackerApp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.ion.coinTrackerApp.animation.AnimationImageZoomIn;
import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;

public class MainActivity extends AppCompatActivity implements Activity {
    private TextView currentPriceTxt;
    private TextView currentFearAndGreedTxt;
    private TextView inputPriceTxt;
    private ImageView toolbarBitcoinImg;
    private ImageButton toolbarOptions;

    private CryptoSingleton cryptoSingleton;
    private SettingsSingleton settingsSingleton;
    private NotificationSingleton notificationSingleton;

    private AnimationImageZoomIn animationImageZoomIn;

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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentPriceTxt.setText("Derzeitiger Stand: " + cryptoSingleton.getBitcoinData().getCurrentCryptoPrice() + " $");
                                inputPriceTxt.setText("Bitcoin Warnung: -/+ " + notificationSingleton.getNotificationData().getInputCryptoLimit() + " $");
                                currentFearAndGreedTxt.setText("Fear And Greed Index: " + cryptoSingleton.getBitcoinData().getFearAndGreedIndex());
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

    public void createNewNotification(View view) {
        Intent cryptoNotificationIntent = new Intent(MainActivity.this, CryptoNotificationActivity.class);
        startActivity(cryptoNotificationIntent);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("bitcoinNotification", "bitcoinNotificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel for bitcoin price notification");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displayOptionsMenu(View view) {
        PopupMenu optionsMenu = new PopupMenu(this, view);
        optionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
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
            }
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
        this.animationImageZoomIn = new AnimationImageZoomIn(this, this.toolbarBitcoinImg);
    }

    @Override
    public void initDatabase() {}

    @Override
    public void initSingleton() {
        this.cryptoSingleton = BitcoinSingleton.getInstance();
        this.settingsSingleton = SettingsSingleton.getInstance();
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