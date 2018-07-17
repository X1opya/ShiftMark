package com.android.bignerdranch.shiftmark.AnotherClasses.Pager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.bignerdranch.shiftmark.AnotherActivityes.DaysOptionActivity;
import com.android.bignerdranch.shiftmark.AnotherActivityes.PremiumActivity;
import com.android.bignerdranch.shiftmark.AnotherClasses.DateManager;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.DayData.CulcData;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.DayData.Day;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.android.bignerdranch.shiftmark.R.color.colorWhite;


public class Fragment extends android.support.v4.app.Fragment {

    private  Button loadDb;
    private GridLayout gridCalendar;

    private Calendar[] calendars;
    private View.OnClickListener onClickDayListener;
    private View res;

    private int pageNumber;
    private String strDate;
    private ArrayList<Button> listBtns;

    private final int DELETE_DAY = 1;

    private DBEditor editor;

    public static Fragment newInstance(int page) {
        Fragment fragment = new Fragment();
        Bundle args=new Bundle();
        args.putInt("num",page);
        fragment.setArguments(args);

        return fragment;
    }


    public Fragment() {
        onClickDayListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                Gson json = new Gson();
                Calendar c = calendars[pageNumber];
                c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(btn.getText().toString()));
                Intent intent = new Intent(res.getContext(), DaysOptionActivity.class);
                intent.putExtra("y", calendars[pageNumber].get(Calendar.YEAR));
                intent.putExtra("m", calendars[pageNumber].get(Calendar.MONTH));
                intent.putExtra("d", calendars[pageNumber].get(Calendar.DAY_OF_MONTH));
                Button button = (Button) view;
                //if(button.getTextColors().equals(getResources().getColorStateList(colorDark)))
                startActivityForResult(intent,DELETE_DAY,null);
//                else {
//                    startActivity(intent.setClass(res.getContext(),ReportActivity.class));
//                }
            }
        };

        }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
        listBtns = new ArrayList<>();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        res = inflater.inflate(R.layout.fragment_calendar, container, false);
        //btnSelectDays = res.findViewById(R.id.btn_select_days);
        //btnSelectDays.setOnClickListener(onClickSelectListener);
        gridCalendar =  res.findViewById(R.id.calendarGrid);
        calendars = DateManager.getListDates();
        generateCalendar(res, calendars[pageNumber]);//создается каледарь
        Button prem = res.findViewById(R.id.btnPremium);
        prem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(res.getContext(), PremiumActivity.class);
                intent.putExtra("y", calendars[pageNumber].get(Calendar.YEAR));
                intent.putExtra("m", calendars[pageNumber].get(Calendar.MONTH));
                intent.putExtra("d", calendars[pageNumber].get(Calendar.DAY_OF_MONTH));
                startActivity(intent);
            }
        });
        editor = new DBEditor(res.getContext());

        return res;

    }

    @Override
    public void onStart() {
        super.onStart();

        TextView tvForH =  res.findViewById(R.id.for_hour);
        TextView tvForP =  res.findViewById(R.id.for_percent);
        TextView tvTips =  res.findViewById(R.id.tips);
        TextView tvAll =  res.findViewById(R.id.all);
        TextView tvHours =  res.findViewById(R.id.hours);
        TextView tvprem =  res.findViewById(R.id.premium);
        //tvprem.setText(editor.findeTable());
        List<Day> list;
        list = editor.listDaysInMonth(calendars[pageNumber]);
        CulcData culc = new CulcData(list,getContext());
        tvForH.setText(culc.getEarnings(CulcData.FOR_HOUR));
        tvForP.setText(culc.getEarnings(CulcData.FOR_PERCENT));
        tvTips.setText(culc.getEarnings(CulcData.TIPS));
        tvAll.setText(culc.getEarnings(CulcData.ALL));
        tvHours.setText(culc.getEarnings(CulcData.HOURS));
        Log.println(Log.ASSERT,"Data pakeg", Environment.getRootDirectory().toString());
        //Log.println(Log.ASSERT,"Номер страници - "+Integer.toString(pageNumber), "номер месяца с фрагмента :"+Integer.toString(calendars[pageNumber].get(Calendar.MONTH))+"----  номер месяца со списка :"+ Integer.toString(list.get(0).getDate().get(Calendar.MONTH)));
//        Log.println(Log.ASSERT,"Номер страници - "+Integer.toString(pageNumber), "Количество кнопок :"+Integer.toString(listBtns.size()));
        if(list.size()!=0) {
            //Log.println(Log.ASSERT,"Номер страници - "+Integer.toString(pageNumber), "номер месяца с фрагмента :"+Integer.toString(calendars[pageNumber].get(Calendar.MONTH))+"----  номер месяца со списка :"+ Integer.toString(list.get(0).getDate().get(Calendar.MONTH)));
            for (Day day : list)
                for (Button btn: listBtns) {
                    if (day.getSpecificDate(Day.DAY).equals(btn.getText().toString())) {
                        btn.setBackgroundColor(getResources().getColor(R.color.theme));
                        btn.setTextColor(getResources().getColor(R.color.colorWhite));
                        if(pageNumber==5)  selectCheckedTodayButton(btn);
                        break;
                    }
                }
        }

    }

    //-----------------------------------------------------------------------------



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==DELETE_DAY){
            if(resultCode==RESULT_OK){
                int n = data.getIntExtra("number",-1);
                listBtns.get(n).setBackgroundColor(getResources().getColor(colorWhite));
                listBtns.get(n).setTextColor(getResources().getColor(R.color.colorDark));
                if(pageNumber==5) selectTodayButton(listBtns.get(n));
            }
        }
    }

    private void generateCalendar(View view, Calendar date){
        TextView head =  view.findViewById(R.id.head);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy");
        dateFormat.setCalendar(date);
        strDate = dateFormat.format(date.getTime());
        head.setText(strDate );
        int daysInM = date.getActualMaximum(date.DAY_OF_MONTH);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
        lp.width = 0;
        lp.height = GridLayout.LayoutParams.WRAP_CONTENT;
        lp.columnSpec = GridLayout.spec(DateManager.getFirstDayOfMonth(date), 1f);
        gridCalendar.addView(createBtn("1",view), lp);
        for(int i = 1; i<daysInM; i++){
            Button btn = createBtn(String.valueOf(i+1),view);
            if(pageNumber==5) selectTodayButton(btn);
            addViewToGrid(gridCalendar,btn);

        }
    }

    private void selectTodayButton(Button btn) {
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.DAY_OF_MONTH)==Integer.parseInt(btn.getText().toString())){
            btn.setBackground(getResources().getDrawable(R.drawable.bourder_btn));
        }
    }

    private void selectCheckedTodayButton(Button btn) {
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.DAY_OF_MONTH)==Integer.parseInt(btn.getText().toString())){
            btn.setBackground(getResources().getDrawable(R.drawable.select_bourder_btn));
        }
    }

    private void addViewToGrid(GridLayout field, View btn) {
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
        lp.width = 0;
        lp.height = GridLayout.LayoutParams.WRAP_CONTENT;
        lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        //lp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        field.addView(btn, lp);
    }

        private Button createBtn(String text, View view){
            Button btn ;
            btn = new Button(view.getContext());
            btn.setText(text);
            //btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.caledar_btn));
            btn.setBackgroundColor(getResources().getColor(colorWhite));
            btn.setTextColor(getResources().getColor(R.color.colorDark));
            btn.setOnClickListener(onClickDayListener);
            listBtns.add(btn);
            return btn;
    }


}