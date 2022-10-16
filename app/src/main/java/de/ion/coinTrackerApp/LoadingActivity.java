package de.ion.coinTrackerApp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.ion.coinTrackerApp.animation.AnimationImageZoomInService;
import de.ion.coinTrackerApp.service.CryptoAPIService;
import de.ion.coinTrackerApp.service.foreground.ForegroundCryptoAPIService;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.crypto.utility.CryptoSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.crypto.utility.CryptoSingletonProvider;
import de.ion.coinTrackerApp.error.singleton.ErrorSingleton;
import de.ion.coinTrackerApp.error.singleton.StringRequestErrorSingleton;
import de.ion.coinTrackerApp.notification.utility.NotificationSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.notification.utility.NotificationSingletonProvider;
import de.ion.coinTrackerApp.settings.utility.SettingsSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.settings.utility.SettingsSingletonProvider;

public class LoadingActivity extends AppCompatActivity implements Activity {
    private TextView errorTxt;
    private ImageView toolbarBitcoinImg;

    private ErrorSingleton errorSingleton;

    private NotificationSingletonProvider notificationSingletonProvider;
    private SettingsSingletonProvider settingsSingletonProvider;
    private CryptoSingletonProvider cryptoSingletonProvider;

    private CryptoAPIService cryptoAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        init();

        this.notificationSingletonProvider.prepare();
        this.settingsSingletonProvider.prepare();
        this.cryptoSingletonProvider.prepare();

        cryptoAPIService.requestApi();

        HandlerThread startThread = new HandlerThread("Crypto API Request");
        startThread.start();
        Handler startThreadHandler = new Handler(startThread.getLooper());

        startThreadHandler.postDelayed(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    final CryptoSingleton cryptoSingleton = CryptoDataSingleton.getInstance();
                    @Override
                    public void run() {
                        while (true) {
                            if (this.cryptoSingleton.getCryptoData().getCurrentCryptoPrice() != 0.0 &&
                                    this.cryptoSingleton.getCryptoData().getFearAndGreedIndex() != 0.0) {
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
        this.toolbarBitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
    }

    @Override
    public void initComponents() {
        new AnimationImageZoomInService(this, this.toolbarBitcoinImg);

        this.notificationSingletonProvider = new NotificationSingletonBySqLiteProvider(this);
        this.settingsSingletonProvider = new SettingsSingletonBySqLiteProvider(this);
        this.cryptoSingletonProvider = new CryptoSingletonBySqLiteProvider(this);

        this.cryptoAPIService = new ForegroundCryptoAPIService(this);
    }

    @Override
    public void initDatabase() {
    }

    @Override
    public void initSingleton() {
        this.errorSingleton = StringRequestErrorSingleton.getInstance();
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