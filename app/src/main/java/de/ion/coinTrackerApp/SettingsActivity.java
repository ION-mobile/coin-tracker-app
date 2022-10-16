package de.ion.coinTrackerApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import de.ion.coinTrackerApp.database.DatabaseSettingsRepository;
import de.ion.coinTrackerApp.database.SQLiteSettingsRepository;
import de.ion.coinTrackerApp.settings.PriceOptionSpinner;
import de.ion.coinTrackerApp.settings.frontend.SettingsAlert;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;
import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class SettingsActivity extends AppCompatActivity implements Activity {
    private ImageView toolbarBitcoinImg;
    private ImageButton toolbarBack;
    private TextView toolbarHeading;
    private CheckBox isMutingCheckbox;
    private TextView settingsMusicLabel;
    private ImageView settingsVolumeImg;
    private Spinner settingsPriceOptions;
    private Button saveChangesBtn;

    private DatabaseSettingsRepository sqLiteSettingsRepository;

    private HashMap<String, String> currentSettings;

    private SettingsSingleton settingsSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();

        settingsPriceOptions.setOnItemSelectedListener(new PriceOptionSpinner());

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(() -> {
                            if (isMutingCheckbox.isChecked()) {
                                settingsMusicLabel.setText("Erinnerungsmusik aus");
                                settingsVolumeImg.setImageResource(R.drawable.ic_volume_mute);
                            } else {
                                settingsMusicLabel.setText("Erinnerungsmusik ein");
                                settingsVolumeImg.setImageResource(R.drawable.ic_volume_up);
                            }

                            saveChangesBtn.setEnabled(false);
                            if (!String.valueOf(isMutingCheckbox.isChecked()).equals(currentSettings.get(SQLiteSettingsRepository.COL_IS_MUTING)) ||
                                    !String.valueOf(settingsPriceOptions.getSelectedItem()).equals(currentSettings.get(SQLiteSettingsRepository.COL_PRICE_OPTION))) {
                                saveChangesBtn.setEnabled(true);
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

    public void goBackWithToolbar(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        if (saveChangesBtn.isEnabled()) {
            AlertDialog.Builder alertUserSaveChanges = new AlertDialog.Builder(this);
            alertUserSaveChanges.setTitle(SettingsAlert.TITLE)
                    .setMessage(SettingsAlert.MESSAGE)
                    .setPositiveButton(SettingsAlert.POSITIVE_BUTTON_TEXT, (dialogInterface, i) -> startActivity(mainActivityIntent))
                    .setNegativeButton(SettingsAlert.NEGATIVE_BUTTON_TEXT, (dialogInterface, i) -> {
                    })
                    .show();
        } else {
            startActivity(mainActivityIntent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        if (saveChangesBtn.isEnabled()) {
            AlertDialog.Builder alertUserSaveChanges = new AlertDialog.Builder(this);
            alertUserSaveChanges.setTitle(SettingsAlert.TITLE)
                    .setMessage(SettingsAlert.MESSAGE)
                    .setPositiveButton(SettingsAlert.POSITIVE_BUTTON_TEXT, (dialogInterface, i) -> startActivity(mainActivityIntent))
                    .setNegativeButton(SettingsAlert.NEGATIVE_BUTTON_TEXT, (dialogInterface, i) -> {
                    })
                    .show();
        } else {
            startActivity(mainActivityIntent);
            super.onBackPressed();
        }
    }

    public void saveChanges(View view) {
        boolean isMuting = true;
        if (!isMutingCheckbox.isChecked()) {
            isMuting = false;
        }

        SettingsData settingsData = new SettingsData(isMuting, settingsSingleton.getSettingsData().getPriceOption());
        settingsSingleton.setSettingsData(settingsData);
        sqLiteSettingsRepository.persist(settingsData);

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void initializeBySettingsSingleton() {
        if (settingsSingleton.getSettingsData().getPriceOption().equals("Prozent")) {
            this.currentSettings.put(SQLiteSettingsRepository.COL_PRICE_OPTION, settingsSingleton.getSettingsData().getPriceOption());
            this.settingsPriceOptions.setSelection(1);
        } else if (settingsSingleton.getSettingsData().getPriceOption().equals("USD")) {
            this.currentSettings.put(SQLiteSettingsRepository.COL_PRICE_OPTION, "USD");
            this.settingsPriceOptions.setSelection(0);
        }

        if (settingsSingleton.getSettingsData().isMuting()) {
            this.isMutingCheckbox.setChecked(true);
            this.settingsMusicLabel.setText("Erinnerungsmusik aus");
            this.settingsVolumeImg.setImageResource(R.drawable.ic_volume_mute);
            this.currentSettings.put(SQLiteSettingsRepository.COL_IS_MUTING, "true");
        } else {
            this.isMutingCheckbox.setChecked(false);
            this.settingsMusicLabel.setText("Erinnerungsmusik an");
            this.settingsVolumeImg.setImageResource(R.drawable.ic_volume_up);
            this.currentSettings.put(SQLiteSettingsRepository.COL_IS_MUTING, "false");
        }
    }

    public void loadViews() {
        this.isMutingCheckbox = (CheckBox) findViewById(R.id.isMutingCheckbox);
        this.settingsMusicLabel = (TextView) findViewById(R.id.settingsMusicLabel);
        this.settingsVolumeImg = (ImageView) findViewById(R.id.settingsVolumeImg);
        this.toolbarBitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
        this.toolbarBack = (ImageButton) findViewById(R.id.toolbarBack);
        this.toolbarHeading = (TextView) findViewById(R.id.toolbarHeading);
        this.settingsPriceOptions = (Spinner) findViewById(R.id.settingsPriceSpinner);
        this.saveChangesBtn = (Button) findViewById(R.id.saveChangesBtn);
    }

    @Override
    public void initComponents() {
        this.currentSettings = new HashMap<>();
    }

    @Override
    public void initDatabase() {
        this.sqLiteSettingsRepository = new SQLiteSettingsRepository(this);
    }

    @Override
    public void initSingleton() {
        this.settingsSingleton = SettingsSingleton.getInstance();
    }

    @Override
    public void initToolbar() {
        this.toolbarHeading.setVisibility(View.VISIBLE);
        this.toolbarBack.setVisibility(View.VISIBLE);
        this.toolbarBitcoinImg.setVisibility(View.GONE);

        this.toolbarHeading.setText("Einstellungen");
    }

    @Override
    public void init() {
        loadViews();
        initComponents();
        initDatabase();
        initSingleton();
        initToolbar();
        initializeBySettingsSingleton();
    }
}