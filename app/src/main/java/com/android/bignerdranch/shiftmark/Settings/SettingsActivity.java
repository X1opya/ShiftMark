package com.android.bignerdranch.shiftmark.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bignerdranch.shiftmark.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .add(R.id.pref_content, new SettingsFragment())
                .commit();
    }
}
