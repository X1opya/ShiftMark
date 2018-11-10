package com.android.bignerdranch.shiftmark.data;

import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by X1opya on 17.02.2018.
 */

public class DateManager {

    static public String formateTime(int hour, int minutes) {
        String sMinutes;
        String sHour;
        if(minutes>=15) sMinutes = "30";
        else sMinutes = "00";
        if(hour<10)  sHour="0"+String.valueOf(hour);
        else sHour = String.valueOf(hour);
        return sHour+':'+sMinutes;
    }

    public static int[] splitTime(String s){
        String[] parts = s.split(":");
        int[] intParts = new int[]{Integer.parseInt(parts[0]),Integer.parseInt(parts[1])};
        //Log.println(Log.ASSERT,"String - "+s, parts[0]+"  "+parts[1]);
        return  intParts;
    }

}

