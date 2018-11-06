package com.android.bignerdranch.shiftmark;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.bignerdranch.shiftmark.data.Premium.DialogPremium;
import com.android.bignerdranch.shiftmark.data.Premium.PremAdapter;
import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.data.Premium.Premium;
import com.android.bignerdranch.shiftmark.R;

import java.util.Calendar;

public class PremiumActivity extends AppCompatActivity {
    private DBEditor editor;
    Calendar calendar;
    ListView lv;
    PremAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lv = findViewById(R.id.listViewPrem);
        editor = new DBEditor(this);
        Intent intent = getIntent();
        calendar = Calendar.getInstance();
        calendar.set(intent.getIntExtra("y",-10),intent.getIntExtra("m",-10),intent.getIntExtra("d",-10));
        adapter = new PremAdapter(this, R.layout.prem_item, editor.getPremList(calendar));
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Premium selected = adapter.getItem(i);
                adapter.remove(selected);
                editor.deletePrem(selected.getIndex());
                return true;
            }
        });


    }

    public void addPrem(View view) {
        callDialog(true);
    }

    private void callDialog(Boolean isPositive){
        DialogPremium dialog = new DialogPremium();
        Bundle b = new Bundle();
        b.putBoolean("mod",isPositive);
        b.putInt("y",calendar.get(Calendar.YEAR));
        b.putInt("m",calendar.get(Calendar.MONTH));
        b.putInt("d",calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setArguments(b);
        FragmentManager fm = getFragmentManager();
        dialog.show(fm,"sss");

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.addAll(editor.getPremList(calendar));
        lv.setAdapter(adapter);
    }



    public void addUnPrem(View view) {
        callDialog(false);
    }
}

