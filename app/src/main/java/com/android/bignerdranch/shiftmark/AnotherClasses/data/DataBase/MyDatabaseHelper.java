package com.android.bignerdranch.shiftmark.AnotherClasses.data.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.bignerdranch.shiftmark.AnotherClasses.data.DayData.Day;

import junit.framework.Assert;

import java.util.Calendar;

/**
 * Created by X1opya on 29.05.2018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shift.db";
    public static final String TABLE = "days";
    public static final String TABLE1 = "PREM";
    private final static int DB_VERSION = 2;

    private final String ID = "_id";
    private final String YEAR = "year";
    private final String MONTH = "month";
    private final String DAY = "day";
    private final String START_TIME = "start_time";
    private final String END_TIME = "end_time";
    private final String AMOUNT = "amount";
    private final String PERCENT = "percent";
    private final String TIPS = "tips";
    private final String MPH = "mph";
    private final String INCR_HOUR = "incr_hour";



    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        creatDaysTable(db);
        creatPremiumTable(db);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newV) {
        Log.println(Log.ASSERT, "sss"," --- onUpgrade database from " + old
                + " to " + newV + " version --- ");
        if(old==1 && newV==2){
            creatPremiumTable(db);
        }
    }

    private void creatDaysTable(@NonNull SQLiteDatabase db){
        db.execSQL("CREATE TABLE "
                + TABLE + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + YEAR + " INTEGER, "
                + MONTH + " INTEGER, "
                + DAY + " INTEGER, "
                + START_TIME + " TEXT, "
                + END_TIME+" TEXT, "
                + AMOUNT + " TEXT, "
                + PERCENT + " TEXT, "
                + TIPS +" TEXT, "
                + MPH +" TEXT, "
                + INCR_HOUR+" TEXT);");
    }

    private void creatPremiumTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE1+" (_id INTEGER PRIMARY KEY AUTOINCREMENT,year INTEGER,month INTEGER,many INTEGER, des TEXT);");

    }
}
