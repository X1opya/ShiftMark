package com.android.bignerdranch.shiftmark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bignerdranch.shiftmark.Settings.SettingsActivity;
import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.data.DayData.CulcData;
import com.android.bignerdranch.shiftmark.data.DayData.Day;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;

    TextView tvForH,tvForP,tvTips,tvAll,tvHours,tvPrem, tvSalary;
    LinearLayout cHour, cMph, cSalary, cTips, cPercent, cPrem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        updateUi(calendarView.getCurrentDate());

    }

    private void initViews(){
        calendarView = findViewById(R.id.cw);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        setCalendarListeners();

        tvForH =  findViewById(R.id.for_hour);
        tvForP =  findViewById(R.id.for_percent);
        tvTips =  findViewById(R.id.tips);
        tvAll =  findViewById(R.id.all);
        tvHours =  findViewById(R.id.hours);
        tvPrem =  findViewById(R.id.premium);
        tvSalary =  findViewById(R.id.salary);

        cHour = findViewById(R.id.hour_cont);
        cMph = findViewById(R.id.mph_cont);
        cPercent = findViewById(R.id.percent_cont);
        cPrem = findViewById(R.id.prem_cont);
        cSalary = findViewById(R.id.salary_cont);
        cTips = findViewById(R.id.tips_cont);
    }

    private void setCalendarListeners(){
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getBaseContext(),date.toString(),Toast.LENGTH_SHORT).show();
                widget.setDateSelected(date,false);
                startOprionsActivity(date);
            }
        });
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                //проверить методы на смену месяца
                updateUi(calendarView.getCurrentDate());
            }
        });
    }

    private void startOprionsActivity(CalendarDay date){
        Intent intent = new Intent(this, DaysOptionActivity.class);
        intent.putExtra("y", date.getYear());
        intent.putExtra("m", date.getMonth());
        intent.putExtra("d", date.getDay());
        startActivity(intent);
    }

    private void updateUi(CalendarDay d){
        Calendar date = Calendar.getInstance();
        date.set(d.getYear(),d.getMonth(),d.getDay());
        DBEditor editor = new DBEditor(this);
        List<Day> list = editor.selectedDays(date);
        //calendarView.setSelectionColor(R.color.theme);
        for(Day day: list) {
            calendarView.setDateSelected(day.getDate(),true);
        }
        CulcData culc = new CulcData(list,this);
        tvForH.setText(culc.getEarnings(CulcData.FOR_HOUR));
        tvForP.setText(culc.getEarnings(CulcData.FOR_PERCENT));
        tvTips.setText(culc.getEarnings(CulcData.TIPS));
        tvAll.setText(culc.getEarnings(CulcData.ALL));
        tvHours.setText(culc.getEarnings(CulcData.HOURS));
        tvSalary.setText(culc.getEarnings(CulcData.SALARY));
    }

    private void updateViews(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        isViewActive(pref.getBoolean("pref_mph",true), cMph);
        isViewActive(pref.getBoolean("pref_percent",true), cPercent);
        isViewActive(pref.getBoolean("pref_tips",true), cTips);
        isViewActive(pref.getBoolean("pref_salary",true), cSalary);
        isViewActive(pref.getBoolean("pref_hour",true), cHour);
        isViewActive(pref.getBoolean("pref_mph",true), cMph);

    }

    static public void isViewActive(boolean prefer, View view){
        if(prefer) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUi(calendarView.getCurrentDate());
        updateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            startActivity(new Intent(this,SettingsActivity.class));
        } else {
            Intent intent = new Intent(this, DaysOptionActivity.class);
            intent.putExtra("date", getResources().getString(R.string.set_message_to_settings));
            intent.putExtra("mod", true);
            startActivity(intent);
        }
        return true;

    }
}
