package de.ion.coinTrackerApp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.ion.coinTrackerApp.animation.AnimationImageZoomIn;
import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.database.BitcoinDataInitializer;
import de.ion.coinTrackerApp.bitcoin.CryptoDataInitializer;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.error.singleton.StringRequestErrorSingleton;
import de.ion.coinTrackerApp.error.singleton.ErrorSingleton;
import de.ion.coinTrackerApp.notification.database.NotificationDataInitializer;
import de.ion.coinTrackerApp.notification.database.NotificationInitializer;
import de.ion.coinTrackerApp.settings.database.SettingsDatabaseInitializer;
import de.ion.coinTrackerApp.settings.database.SettingsInitializer;
import de.ion.coinTrackerApp.threads.CryptoAPIHandler;

public class LoadingActivity extends AppCompatActivity implements Activity {
    private TextView errorTxt;
    private ImageView bitcoinImg;

    private ErrorSingleton errorSingleton;

    private NotificationInitializer notificationLoader;
    private SettingsInitializer settingsLoader;
    private CryptoDataInitializer cryptoLoader;

    private CryptoAPIHandler cryptoAPIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        init();

        this.notificationLoader.prepare();
        this.settingsLoader.prepare();
        this.cryptoLoader.initialize();

        cryptoAPIHandler.requestApi();

        HandlerThread startThread = new HandlerThread("Crypto API Request");
        startThread.start();
        Handler startThreadHandler = new Handler(startThread.getLooper());

        startThreadHandler.postDelayed(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    final CryptoSingleton cryptoSingleton = BitcoinSingleton.getInstance();

                    @Override
                    public void run() {
                        while (true) {
                            if (!this.cryptoSingleton.getBitcoinData().getCurrentCryptoPrice().equals("") &&
                                    !this.cryptoSingleton.getBitcoinData().getFearAndGreedIndex().equals("")) {
                                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActivityIntent);
                                break;
                            }

                            if (!errorSingleton.getErrorMessage().equals("")) {
                                errorTxt.setText(errorSingleton.getErrorMessage());
                                errorTxt.setTextColor(Color.parseColor("#FF9999"));
                                break;
                            }
                        }
                    }
                });
            }

        }, 3000);
    }

    public void loadViews() {
        this.errorTxt = (TextView) findViewById(R.id.errorTxt);
        this.bitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
    }

    @Override
    public void initComponents() {
        AnimationImageZoomIn animationImageZoomIn = new AnimationImageZoomIn(this, this.bitcoinImg);

        this.notificationLoader = new NotificationDataInitializer(this);
        this.settingsLoader = new SettingsDatabaseInitializer(this);
        this.cryptoLoader = new BitcoinDataInitializer(this);

        this.cryptoAPIHandler = new CryptoAPIHandler(this);
    }

    @Override
    public void initDatabase() {}

    @Override
    public void initSingleton() {
        this.errorSingleton = StringRequestErrorSingleton.getInstance();
    }

    @Override
    public void initToolbar() {}

    @Override
    public void init() {
        loadViews();
        initComponents();
        initDatabase();
        initSingleton();
        initToolbar();
    }
}