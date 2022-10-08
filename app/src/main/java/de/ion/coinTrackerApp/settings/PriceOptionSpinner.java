package de.ion.coinTrackerApp.settings;

import android.view.View;
import android.widget.AdapterView;

import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;
import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class PriceOptionSpinner implements AdapterView.OnItemSelectedListener {
    private SettingsSingleton settingsSingleton;

    public PriceOptionSpinner() {
        this.settingsSingleton = SettingsSingleton.getInstance();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SettingsData settingsData = this.settingsSingleton.getSettingsData();
        this.settingsSingleton.setSettingsData(new SettingsData(settingsData.isMuting(), adapterView.getSelectedItem().toString()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
