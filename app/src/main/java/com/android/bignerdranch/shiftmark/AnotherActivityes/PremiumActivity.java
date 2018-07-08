package com.android.bignerdranch.shiftmark.AnotherActivityes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.bignerdranch.shiftmark.AnotherClasses.data.DBEditor;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.Premium;
import com.android.bignerdranch.shiftmark.R;

import java.util.Calendar;

public class PremiumActivity extends AppCompatActivity {
    private DBEditor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ListView lv = findViewById(R.id.listViewPrem);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2);
        lv.setAdapter(adapter);
        editor = new DBEditor(this);
        Intent intent = getIntent();
        Calendar c = Calendar.getInstance();
        c.set(intent.getIntExtra("year",-10),intent.getIntExtra("month",-10),intent.getIntExtra("day",-10));
        adapter.addAll(editor.selectListPrem(c));
    }

    public void addPrem(View view) {
    }

    public void addUnPrem(View view) {
    }
}
