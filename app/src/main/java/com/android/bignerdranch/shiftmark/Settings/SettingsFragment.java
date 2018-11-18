package com.android.bignerdranch.shiftmark.Settings;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.android.bignerdranch.shiftmark.R;

public class SettingsFragment extends PreferenceFragment {
    CheckBoxPreference hour, mph;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        mph = (CheckBoxPreference) findPreference("pref_mph");
        hour = (CheckBoxPreference) findPreference("pref_hour");
        if(mph.isChecked()){
            hour.setChecked(true);
            hour.setEnabled(false);
        }else {
            hour.setEnabled(true);
        }
        mph.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(mph.isChecked()) {
                    hour.setChecked(true);
                    hour.setEnabled(false);
                }else {
                    hour.setEnabled(true);
                }
                return false;
            }
        });
    }



}
