package de.ion.coinTrackerApp;

import android.content.DialogInterface;
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

import de.ion.coinTrackerApp.database.DatabaseSettingsHelper;
import de.ion.coinTrackerApp.settings.PriceOptionSpinner;
import de.ion.coinTrackerApp.settings.valueObject.SettingsData;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;

public class SettingsActivity extends AppCompatActivity implements Activity {
    private ImageView toolbarBitcoinImg;
    private ImageButton toolbarBack;
    private TextView toolbarHeading;
    private CheckBox isMutingCheckbox;
    private TextView settingsMusicLabel;
    private ImageView settingsVolumeImg;
    private Spinner settingsPriceOptions;
    private Button saveChangesBtn;

    private DatabaseSettingsHelper databaseSettingsHelper;

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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isMutingCheckbox.isChecked()) {
                                    settingsMusicLabel.setText("Erinnerungsmusik an");
                                    settingsVolumeImg.setImageResource(R.drawable.ic_volume_up);
                                } else {
                                    settingsMusicLabel.setText("Erinnerungsmusik aus");
                                    settingsVolumeImg.setImageResource(R.drawable.ic_volume_mute);
                                }

                                if (String.valueOf(isMutingCheckbox.isChecked()).equals(currentSettings.get("muting")) ||
                                        !String.valueOf(settingsPriceOptions.getSelectedItem()).equals(currentSettings.get("priceOptions"))) {
                                    saveChangesBtn.setEnabled(true);
                                } else if (!String.valueOf(isMutingCheckbox.isChecked()).equals(currentSettings.get("muting")) &&
                                        String.valueOf(settingsPriceOptions.getSelectedItem()).equals(currentSettings.get("priceOptions"))) {
                                    saveChangesBtn.setEnabled(false);
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

    public void goBackWithToolbar(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        if (saveChangesBtn.isEnabled()) {
            AlertDialog.Builder alertUserSaveChanges = new AlertDialog.Builder(this);
            alertUserSaveChanges.setTitle("ACHTUNG!!!")
                    .setMessage("Wenn du diese Seite jetzt verlässt, werden deine Änderungen nicht übernommen.")
                    .setPositiveButton("Seite verlassen, ohne zu speichern", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(mainActivityIntent);
                        }
                    })
                    .setNegativeButton("Auf der Seite bleiben", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
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
            alertUserSaveChanges.setTitle("ACHTUNG!!!")
                    .setMessage("Wenn du diese Seite jetzt verlässt, werden deine Änderungen nicht übernommen.")
                    .setPositiveButton("Seite verlassen, ohne zu speichern", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(mainActivityIntent);
                        }
                    })
                    .setNegativeButton("Auf der Seite bleiben", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .show();
        } else {
            startActivity(mainActivityIntent);
            super.onBackPressed();
        }
    }

    public void saveChanges(View view) {
        String isMuting = "true";
        if (isMutingCheckbox.isChecked()) {
            isMuting = "false";
        }

        SettingsData settingsData = new SettingsData(isMuting, settingsSingleton.getSettingsData().getPriceOption());
        settingsSingleton.setSettingsData(settingsData);
        databaseSettingsHelper.addSettingsDataToDatabase(settingsData);

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void initializeOldSettings() {
        if (settingsSingleton.getSettingsData().getPriceOption().equals("Prozent (%)")) {
            this.currentSettings.put("priceOptions", settingsSingleton.getSettingsData().getPriceOption());
            this.settingsPriceOptions.setSelection(1);
        } else if (settingsSingleton.getSettingsData().getPriceOption().equals("USD ($)")) {
            this.currentSettings.put("priceOptions", "USD ($)");
            this.settingsPriceOptions.setSelection(0);
        }
        if (settingsSingleton.getSettingsData().isMuting().equals("true")) {
            this.isMutingCheckbox.setChecked(false);
            this.settingsMusicLabel.setText("Erinnerungsmusik aus");
            this.settingsVolumeImg.setImageResource(R.drawable.ic_volume_mute);
            this.currentSettings.put("muting", "true");
        } else if (settingsSingleton.getSettingsData().isMuting().equals("false")) {
            this.isMutingCheckbox.setChecked(true);
            this.settingsMusicLabel.setText("Erinnerungsmusik an");
            this.settingsVolumeImg.setImageResource(R.drawable.ic_volume_up);
            this.currentSettings.put("muting", "false");
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
        this.databaseSettingsHelper = new DatabaseSettingsHelper(this);
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
        initializeOldSettings();
    }
}