package com.android.bignerdranch.shiftmark.AnotherClasses.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Created by X1opya on 29.05.2018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shift.db";
    public static final String TABLE = "days";
    private final int DB_VERSION = 1;

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
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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

    public void addToDb(SQLiteDatabase db, Day day){
        ContentValues item = new ContentValues();
        item.put(YEAR,day.getSpecificDate(Day.YEAR));
        item.put(MONTH,day.getSpecificDate(Day.MONTH));
        item.put(DAY,day.getSpecificDate(Day.DAY));
        item.put(START_TIME,day.getStartTime());
        item.put(END_TIME,day.getEndTime());
        item.put(AMOUNT,day.getAmount());
        item.put(PERCENT,day.getPercent());
        item.put(TIPS,day.getTips());
        item.put(MPH,day.getMoneyPerHour());
        item.put(INCR_HOUR,day.getIncrHour());
        db.insert(DB_NAME,null,item);
    }

    public int selectFromDb(SQLiteDatabase db, Cursor cursor, Calendar date){
        cursor = db.rawQuery("SELECT * FROM "
                + DB_NAME + " WHERE "
                + YEAR+" = "+Integer.toString(date.get(Calendar.YEAR))
                + " AND "
                + MONTH + " = "+Integer.toString(date.get(Calendar.MONTH)),null);
        cursor.close();
        return cursor.getCount();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}