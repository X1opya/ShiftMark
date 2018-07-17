package com.android.bignerdranch.shiftmark.AnotherActivityes;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.bignerdranch.shiftmark.AnotherClasses.data.Premium.DialogPremium;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.Premium.PremAdapter;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.R;

import java.util.Calendar;

public class PremiumActivity extends AppCompatActivity {
    private DBEditor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //ListView lv = findViewById(R.id.listViewPrem);
        editor = new DBEditor(this);
        Intent intent = getIntent();
        Calendar c = Calendar.getInstance();
        c.set(intent.getIntExtra("y",-10),intent.getIntExtra("m",-10),intent.getIntExtra("d",-10));
        PremAdapter adapter = new PremAdapter(this,R.layout.prem_item,editor.getPremList(c));
        //lv.setAdapter(adapter);
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                return true;
//            }
//        });

    }

    public void addPrem(View view) {
        DialogPremium dialog = new DialogPremium();
        FragmentManager fm = getFragmentManager();
        dialog.show(fm,"sss");;
    }

    public void addUnPrem(View view) {
        Toast t = Toast.makeText(this,"Пидор",Toast.LENGTH_LONG);
        t.show();
    }
}
