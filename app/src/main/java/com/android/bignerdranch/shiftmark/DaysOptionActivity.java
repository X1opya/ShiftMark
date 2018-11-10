package com.android.bignerdranch.shiftmark;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
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
import com.android.bignerdranch.shiftmark.data.DayData.CulcData;
import com.android.bignerdranch.shiftmark.data.DayData.Day;
import com.android.bignerdranch.shiftmark.data.DayData.DaySettings;
import com.android.bignerdranch.shiftmark.data.DataBase.MyDatabaseHelper;
import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import static android.app.TimePickerDialog.*;

public class DaysOptionActivity extends AppCompatActivity {

    private String pickedTime1;
    private String pickedTime2;
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
                setTextToTV(etMph,day.getMoneyPerHour());
                setTextToTV(etPercent,day.getPercent());
                setTextToTV(etAmount,day.getAmount());
                setTextToTV(etTips,day.getTips());
                dayIsExist = true;
            }
            else {

                DaySettings pathern = DataEditor.intentFromPathern(this);
                setTextToTV(etPercent,pathern.getPercent());
                setTextToTV(etMph,pathern.getMoneyPerHour());
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

    private void setTextToTV(TextView tv, String text){
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
        tvStart = (TextView) findViewById(R.id.textViewStart) ;
        tvEnd = (TextView) findViewById(R.id.textViewEnd) ;
        etAmount = (EditText) findViewById(R.id.editTextAmount);
        etPercent = (EditText) findViewById(R.id.editTextPercent);
        etTips = (EditText) findViewById(R.id.editTextTips);
        etMph = (EditText) findViewById(R.id.editTextMph);
        tvIncrHour = (TextView) findViewById(R.id.textViewHour);
        etSalary = findViewById(R.id.editTextSalary);

        deletingLiner1 = (LinearLayout) findViewById(R.id.deleting1);

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
        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        Day day = new Day();;
        if(mod) {

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
        else {
            day = new Day();
            day.setStartTime(tvStart.getText().toString());
            day.setEndTime(tvEnd.getText().toString());
            day.setDate(date);
            //if(etAmount.length()!=0 && etPercent.length()!=0){
            day.setAmount(etAmount.getText().toString());
            day.setPercent(etPercent.getText().toString());
            day.setTips(etTips.getText().toString());
            day.setMoneyPerHour(etMph.getText().toString());
            day.setIncrHour(tvIncrHour.getText().toString());
            day.setSalary(etSalary.getText().toString());
            if(dayIsExist) editor.deleteDay(date);

            editor.addToDb(day);
        }
        finish();
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
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
