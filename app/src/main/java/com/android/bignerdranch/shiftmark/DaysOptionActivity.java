package com.android.bignerdranch.shiftmark;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.bignerdranch.shiftmark.data.DataEditor;
import com.android.bignerdranch.shiftmark.data.DateManager;
import com.android.bignerdranch.shiftmark.data.DayData.Day;
import com.android.bignerdranch.shiftmark.data.DayData.DaySettings;
import com.android.bignerdranch.shiftmark.data.DataBase.MyDatabaseHelper;
import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.data.ModelMonth;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import static android.app.TimePickerDialog.*;

public class DaysOptionActivity extends AppCompatActivity {

    private OnTimeSetListener onTimeSetListener1;
    private OnTimeSetListener onTimeSetListener2;
    private TextView tvStart;
    private TextView tvEnd;
    private EditText etAmount, etSalary;
    private EditText etPercent;
    private EditText etTips;
    private EditText etMph;
    private TextView tvIncrHour;
    private LinearLayout deletingLiner1;
    private Calendar date;
    private DBEditor editor;
    private boolean dayIsExist;
    LinearLayout hour1Cont, hour2Cont, percentCont, mphCont, salaryCont,tipsCont,incrCont;


    boolean mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_option);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        mod = intent.getBooleanExtra("mod",false);
        dayIsExist = false;
        initializeWievs();
        initializeTimeListeners();
        updateViews();
        if(mod){
            deletingLiner1.setGravity(0);
            deletingLiner1.setVisibility(View.GONE);
            Button btn = (Button) findViewById(R.id.button);
            btn.setVisibility(View.GONE);
            setTitle(getResources().getString(R.string.set_message_to_settings));
        }
        else{
            editor = new DBEditor(this);
            date = Calendar.getInstance();
            date.set(Calendar.YEAR,intent.getIntExtra("y",-1));
            date.set(Calendar.MONTH,intent.getIntExtra("m",-1));
            date.set(Calendar.DAY_OF_MONTH,intent.getIntExtra("d",-1));
            Day day = editor.selectDayForEdite(date);
            SimpleDateFormat format = new SimpleDateFormat("dd MMMM");
            format.setCalendar(date);
            setTitle(format.format(date.getTime()));
            if(day!=null){
                tvStart.setText(day.getStartTime());
                tvEnd.setText(day.getEndTime());
                tvIncrHour.setText(day.getIncrHour());
                setTextToEditeText(etMph,day.getMoneyPerHour());
                setTextToEditeText(etPercent,day.getPercent());
                setTextToEditeText(etAmount,day.getAmount());
                setTextToEditeText(etTips,day.getTips());
                setTextToEditeText(etSalary,day.getTips());
                dayIsExist = true;
            }
            else {

                DaySettings pathern = DataEditor.intentFromPathern(this);
                setTextToEditeText(etPercent,pathern.getPercent());
                setTextToEditeText(etMph,pathern.getMoneyPerHour());
                tvIncrHour.setText(pathern.getIncrHour());
                tvStart.setText(pathern.getStartTime());
                tvEnd.setText(pathern.getEndTime());
                Button btn = (Button) findViewById(R.id.button);
                btn.setVisibility(View.GONE);
            }
            }
    }

    private void updateViews(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        MainActivity.isViewActive(pref.getBoolean("pref_mph",true), mphCont);
        MainActivity.isViewActive(pref.getBoolean("pref_percent",true), percentCont);
        MainActivity.isViewActive(pref.getBoolean("pref_tips",true), tipsCont);
        MainActivity.isViewActive(pref.getBoolean("pref_salary",true), salaryCont);
        MainActivity.isViewActive(pref.getBoolean("pref_hour",true), hour1Cont);
        MainActivity.isViewActive(pref.getBoolean("pref_hour",true), hour2Cont);
        MainActivity.isViewActive(pref.getBoolean("pref_incrh",true), incrCont);
    }

    private void setTextToEditeText(EditText tv, String text){
        if(text.equals("0")) tv.setText("");
        else tv.setText(text);
    }

    private void initializeTimeListeners(){
        onTimeSetListener1 = new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = DateManager.formateTime(i,i1);
                tvStart.setText(time);
            }
        };
        onTimeSetListener2 = new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = DateManager.formateTime(i,i1);
                tvEnd.setText(time);
            }
        };
    }

    private void initializeWievs(){
        tvStart = findViewById(R.id.textViewStart) ;
        tvEnd = findViewById(R.id.textViewEnd) ;
        etAmount = findViewById(R.id.editTextAmount);
        etPercent = findViewById(R.id.editTextPercent);
        etTips = findViewById(R.id.editTextTips);
        etMph = findViewById(R.id.editTextMph);
        tvIncrHour =  findViewById(R.id.textViewHour);
        etSalary = findViewById(R.id.editTextSalary);

        deletingLiner1 = findViewById(R.id.deleting1);

        mphCont = findViewById(R.id.mph_cont2);
        salaryCont = findViewById(R.id.salary_cont2);
        hour1Cont = findViewById(R.id.hour1_cont);
        hour2Cont = findViewById(R.id.hour2_cont);
        percentCont = findViewById(R.id.percent_cont2);
        tipsCont = findViewById(R.id.tips_cont2);
        incrCont = findViewById(R.id.incr_cont2);
    }


    public void selectDialogueTimePicker1(View view) {
        Calendar cal = Calendar.getInstance();
        OnTimeSetListener otl;
        if(view.getId()==R.id.btnStart) otl=onTimeSetListener1;
        else otl=onTimeSetListener2;
        TimePickerDialog tp = new TimePickerDialog(this, otl, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
        tp.show();
    }

    public void saveDay(View view) {
        if(mod) {
            saveToTxt();
        }
        else {
            saveToDB();
            addMonthToSave();
        }

        finish();
    }

    private void saveToTxt(){
        Day day = new Day();
        day.setStartTime(tvStart.getText().toString());
        day.setEndTime(tvEnd.getText().toString());

        if(etPercent.length()!=0)
            day.setPercent(etPercent.getText().toString());
        if(etMph.length()!=0)
            day.setMoneyPerHour(etMph.getText().toString());
        if(tvIncrHour.length()!=0)
            day.setIncrHour(tvIncrHour.getText().toString());
        if(etSalary.length()!=0)
            day.setSalary(etSalary.getText().toString());
        DataEditor.savePathern(this,day);
    }

    private void saveToDB(){
        Day day = new Day();
        day.setStartTime(tvStart.getText().toString());
        day.setEndTime(tvEnd.getText().toString());
        day.setDate(date);
        day.setAmount(etAmount.getText().toString());
        day.setPercent(etPercent.getText().toString());
        day.setTips(etTips.getText().toString());
        day.setMoneyPerHour(etMph.getText().toString());
        day.setIncrHour(tvIncrHour.getText().toString());
        day.setSalary(etSalary.getText().toString());
        if(dayIsExist) editor.deleteDay(date);
        editor.addToDb(day);
    }

    private void addMonthToSave(){
        for (ModelMonth m: MainActivity.saveList
                ) {
            if(m.getDate().get(Calendar.MONTH)==date.get(Calendar.MONTH) && m.getDate().get(Calendar.YEAR)==date.get(Calendar.YEAR)){
                MainActivity.saveList.remove(m);
            }
        }
        ModelMonth month = new ModelMonth(date);
        MainActivity.saveList.add(month);
        }


    public void hourOperation(View view) {
        Button btn = (Button) view;
        if(!tvIncrHour.equals(""));{
            double d = Double.parseDouble(tvIncrHour.getText().toString()) +
                    Double.parseDouble(btn.getText().toString());
            tvIncrHour.setText(String.valueOf(d));
        }
    }

    public void deleteDay(View view) {
        editor.deleteDay(date);
        Intent intent = new Intent();
        intent.putExtra("isDelete",true);
        intent.putExtra("number", date.get(Calendar.DAY_OF_MONTH)-1);
        setResult(RESULT_OK,intent);
        Toast.makeText(getApplication().getApplicationContext(),"Удаленно",Toast.LENGTH_SHORT).show();
        addMonthToSave();
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
