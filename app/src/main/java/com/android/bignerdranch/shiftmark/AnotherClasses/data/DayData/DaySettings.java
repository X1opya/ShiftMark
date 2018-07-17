package com.android.bignerdranch.shiftmark.AnotherClasses.data.DayData;

import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;

/**
 * Created by X1opya on 09.04.2018.
 */

public class DaySettings {

    protected String startTime;
    protected String endTime;
    protected double moneyPerHour;
    protected int percent;
    protected double incrHour;


    public String getIncrHour() {
        return String.valueOf(incrHour);
    }

    public void setIncrHour(String incrHour) {
        this.incrHour = Double.parseDouble(incrHour);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime=startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime=endTime;
    }

    public String getMoneyPerHour() {
        if(moneyPerHour==0) return "0";
        return String.valueOf(moneyPerHour);
    }

    public void setMoneyPerHour(String moneyPerHour) {
        if(moneyPerHour.length()!=0)
        this.moneyPerHour = Double.parseDouble(moneyPerHour);
    }

    public String getPercent() {
        if(percent==0) return "0";
        return String.valueOf(percent);
    }

    public void setPercent(String percent) {
        if(percent.length()!=0)
        this.percent = Integer.parseInt(percent);
    }

    public DaySettings() {
        startTime = "00:00";
        endTime = "00:00";
    }
}
