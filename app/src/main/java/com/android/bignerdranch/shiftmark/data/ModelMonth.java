package com.android.bignerdranch.shiftmark.data;

import android.app.Application;
import android.content.Context;

import com.android.bignerdranch.shiftmark.MainActivity;
import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.data.DayData.Day;
import com.google.firebase.database.IgnoreExtraProperties;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.List;
@IgnoreExtraProperties
public class ModelMonth {
    private Calendar name; //year+":"+month
    private List<Day> days;

    public ModelMonth(Calendar date) {
        name =  date;
    }

    public Calendar getDate() {
        return name;
    }
    public String getStringDate() {
        return name.get(Calendar.YEAR)+":"+name.get(Calendar.MONTH)+":0";
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
