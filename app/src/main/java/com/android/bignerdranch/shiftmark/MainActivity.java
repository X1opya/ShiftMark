package com.android.bignerdranch.shiftmark;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.android.bignerdranch.shiftmark.data.ModelMonth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.SharedPreferences.*;

public class MainActivity extends AppCompatActivity {
    public static final String REF = "Users/" ;
    public static final String APP_PREFERENCES = "guid" ;
    public static final String IS_END = "is_end" ;
    SharedPreferences mSettings;

    MaterialCalendarView calendarView;

    TextView tvForH,tvForP,tvTips,tvAll,tvHours,tvPrem, tvSalary;
    LinearLayout cHour, cMph, cSalary, cTips, cPercent, cPrem;
    static public List<ModelMonth> saveList; //добавляются месяцы подвергнувшиеся редактированию. После отдаются на сохранение в FireBase
    DBEditor editor;

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(!mSettings.getBoolean(IS_END,false)){
            startActivity(new Intent(this,SettingsActivity.class));
        }
        startActivity(new Intent(this,AuthorizeActivity.class));
        editor = new DBEditor(this);
        initViews();
        database = FirebaseDatabase.getInstance();
        updateUi(calendarView.getCurrentDate());


    }

    private void initViews(){
        calendarView = findViewById(R.id.cw);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        setCalendarListeners();

        saveList = new ArrayList<>();

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
                widget.setDateSelected(date,false);
                startOprionsActivity(date);
            }
        });
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            startActivity(new Intent(this,SettingsActivity.class));
        } else if(id == R.id.template) {
            Intent intent = new Intent(this, DaysOptionActivity.class);
            intent.putExtra("date", getResources().getString(R.string.set_message_to_settings));
            intent.putExtra("mod", true);
            startActivity(intent);
        }else if(id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,AuthorizeActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi(calendarView.getCurrentDate());
        updateViews();
    }

    private void writeOneToFirebase(ModelMonth m){
        DatabaseReference myRef = database.getReference(REF+FirebaseAuth.getInstance().getUid());
        Gson gson = new GsonBuilder().create();
        String d = gson.toJson(m,ModelMonth.class);
        Map<String,Object> items = new HashMap<>();
        items.put(m.getStringDate(),d);
        myRef.updateChildren(items);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeAllModelsTodb(saveList);
        //load to firebase database
    }

    private void writeAllModelsTodb(List<ModelMonth> saveList){
        for (ModelMonth m: saveList
                ) {
            m.setDays(editor.selectedDays(m.getDate()));
            writeOneToFirebase(m);
        }
        saveList.clear();
    }


}
