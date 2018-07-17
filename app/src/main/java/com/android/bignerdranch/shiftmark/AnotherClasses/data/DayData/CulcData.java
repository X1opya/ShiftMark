package com.android.bignerdranch.shiftmark.AnotherClasses.data.DayData;

import android.content.Context;
import android.util.Log;

import com.android.bignerdranch.shiftmark.AnotherClasses.DateManager;
import com.android.bignerdranch.shiftmark.AnotherClasses.data.DayData.Day;

import java.util.List;

/**
 * Created by X1opya on 18.06.2018.
 */

public class CulcData {
    private List<Day> list;
    private Context context;
    public static final int FOR_HOUR=1;
    public static final int FOR_PERCENT=2;
    public static final int TIPS=3;
    public static final int ALL=4;
    public static final int HOURS=5;

    public CulcData(List<Day> list , Context context) {
        this.context=context;
        this.list = list;
    }

    public CulcData(){

    }

    public String getEarnings(int code){
        double manyForHours=0;
        double manyForPercent=0;
        double tips = 0;
        double hours = 0;
        for (Day d: list){
            manyForHours += getForHour(d);
            manyForPercent += getForPercent(d);
            tips += Double.parseDouble(d.getTips());
            hours+=getHours(d);
        }
        double all = manyForHours+ manyForPercent+tips;
        switch (code){
            case 1: return Double.toString(manyForHours);
            case 2: return Double.toString(manyForPercent);
            case 3: return Double.toString(tips);
            case 4: return Double.toString(all);
            case 5: return Double.toString(hours);
            default: return "-666";
        }
    }

    public double getHours(Day d){
        int[] start = DateManager.splitTime(d.getStartTime());
        int[] end = DateManager.splitTime(d.getEndTime());
        double value=0;
        int hour = 0;
        if(end[0]<start[0]){
            hour = 24 - start[0];
            hour+=end[0];
        }
        else hour = end[0]-start[0];
        if(start[1]-end[1]>0)  value = (hour - 1) + 0.5;
        else if(start[1]-end[1]<0)  value = hour  + 0.5;
        else value = hour;
        return  value+Double.parseDouble(d.getIncrHour());
    }

    public double getForHour(Day d){
        double value = Double.parseDouble(d.getMoneyPerHour()) * getHours(d);
        //Log.println(Log.ASSERT,"----","деньги за часы "+value);
        return value;
    }

    public double getForPercent(Day d){
        if(d.getPercent()!="0" && d.getAmount()!="0") {
            double value = Double.parseDouble(d.getAmount()) * (Double.parseDouble(d.getPercent()) / 100);
            return (double) Math.round((float) value);
        }
        else return 0;
    }
}