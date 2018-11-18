package com.android.bignerdranch.shiftmark.Settings;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.bignerdranch.shiftmark.R;
import com.android.bignerdranch.shiftmark.Spotlite.SpotliteMarks;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getFragmentManager().beginTransaction()
                .add(R.id.pref_container, new SettingsFragment())
                .commit();
        if(getIntent().getBooleanExtra("hint",false)) {
            SpotliteMarks.globalMark(this, findViewById(R.id.pref_container),
                    "Настройки",
                    "Для вашего удобства в приложении есть тонкая настройка пунктов оплаты. Выбирите только те, которые нужны вам. Если чего-то не хватает - напишите разработчику");
        }
    }


}
