package com.android.bignerdranch.shiftmark.data.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.bignerdranch.shiftmark.data.DayData.Day;
import com.android.bignerdranch.shiftmark.data.Premium.Premium;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBEditor {

    public static final String TABLE = "days";
    public static final String TABLE1 = "PREM";
    static private final String ID = "_id";
    static private final String YEAR = "year";
    static private final String MONTH = "month";
    static private final String DAY = "day";
    static private final String START_TIME = "start_time";
    static private final String END_TIME = "end_time";
    static private final String AMOUNT = "amount";
    static private final String PERCENT = "percent";
    static private final String TIPS = "tips";
    static private final String MPH = "mph";
    static private final String INCR_HOUR = "incr_hour";
    private final String SALARY = "salary";

    private Context context;
    private SQLiteDatabase db;
    private MyDatabaseHelper helper;
    private Cursor cursor;

    public DBEditor(Context context) {
        this.context=context;

        helper = new MyDatabaseHelper(context);
    }

    public void addToDb(Day day){
        db = helper.getWritableDatabase();
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
        item.put(SALARY, day.getSalary());
        db.insert(TABLE,null,item);
        db.close();
        //Toast.makeText(context.getApplicationContext(),"Сохраненно",Toast.LENGTH_SHORT).show();
    }

    public void addList(List<Day> list){
        for (Day d: list){
            addToDb(d);
        }

    }

    public Day selectDayForEdite(Calendar date){
        db = helper.getReadableDatabase();
        cursor = db.query(TABLE,new String[]{"*"},YEAR+" = "+date.get(Calendar.YEAR)+" AND "+MONTH+" = "+date.get(Calendar.MONTH)+" AND "+
                DAY+" = "+date.get(Calendar.DAY_OF_MONTH),null,null,null,null);
        Day day = new Day();
        Calendar value = Calendar.getInstance();
        if(cursor.moveToFirst()){
            value.set(cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
            day.setDate(value);
            day.setStartTime(cursor.getString(4));
            day.setEndTime(cursor.getString(5));
            day.setAmount(cursor.getString(6));
            day.setPercent(cursor.getString(7));
            day.setTips(cursor.getString(8));
            day.setMoneyPerHour(cursor.getString(9));
            day.setIncrHour(cursor.getString(10));
            day.setSalary(cursor.getString(11));
            File dbFile = context.getDatabasePath("\"shift.db\"");
            //Log.println(Log.ASSERT,"ssssssssssssssssssss",dbFile.getAbsolutePath());
        }
        else return null;
        db.close();
        cursor.close();
        return  day;
    }

    public List<Day> selectedDays(Calendar date){
        db = helper.getReadableDatabase();
        List<Day> list = new ArrayList<>();
        cursor = db.query(TABLE,new String[]{"*"},YEAR+" = "+date.get(Calendar.YEAR)+" AND "+MONTH+" = "+(date.get(Calendar.MONTH)),null,null,null,DAY+" ASC");
        if (cursor.moveToFirst()){
            do{
                Calendar value = Calendar.getInstance();
                Day day = new Day();
                value.set(cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
                day.setDate(value);
                day.setStartTime(cursor.getString(4));
                day.setEndTime(cursor.getString(5));
                day.setAmount(cursor.getString(6));
                day.setPercent(cursor.getString(7));
                day.setTips(cursor.getString(8));
                day.setMoneyPerHour(cursor.getString(9));
                day.setIncrHour(cursor.getString(10));
                day.setSalary(cursor.getString(11));
                list.add(day);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    public void deleteDay(Calendar date){
        db = helper.getWritableDatabase();
        db.delete(TABLE,YEAR+" = ?"+" AND "+MONTH+" = ?"+" AND "+
                DAY+" = ?", new String[]{Integer.toString(date.get(Calendar.YEAR)),Integer.toString(date.get(Calendar.MONTH)), Integer.toString(date.get(Calendar.DAY_OF_MONTH))});
        db.close();
    }

    public void addPrem(Premium p){
        Log.println(Log.ASSERT,"Data Base Editor", "-----   addPrem");
        db = helper.getWritableDatabase();
        ContentValues item = new ContentValues();
        Calendar c = p.getCalendar();
        item.put("year",c.get(Calendar.YEAR));
        item.put("month",c.get(Calendar.MONTH));
        item.put("many",p.getIntMany());
        item.put("des",p.getDescription());
        db.insert(TABLE1,null,item);
        db.close();
    }

    public List<Premium> getPremList(Calendar c){
        Log.println(Log.ASSERT,"Data Base Editor", "-----   getPremList");
        db = helper.getReadableDatabase();
        cursor = db.query(TABLE1, new String[]{"*"},"year = "+c.get(Calendar.YEAR)+" AND "+"month = "+(c.get(Calendar.MONTH)),null,null,null,null);
        if(cursor.moveToFirst()){
            Log.println(Log.ASSERT,"Data Base Editor", "-----   getPremList  Элемент есть");
            List<Premium> list = new ArrayList<>();
            do{
                Premium prem = new Premium(cursor.getInt(3),cursor.getString(4),c);
                prem.setIndex(cursor.getInt(0));
                list.add(prem);
            }while (cursor.moveToNext());
            db.close();
            cursor.close();
            Log.println(Log.ASSERT,"Data Base Editor", "-----   getPremList  - count "+list.size());
            return list;
        }else Log.println(Log.ASSERT,"Data Base Editor", "-----   getPremList  нет элементов");

        db.close();
        cursor.close();
        return new ArrayList<>();
    }

    public void deletePrem(int key){
        Log.println(Log.ASSERT,"Data Base Editor", "-----   Deletting item");
        db = helper.getWritableDatabase();
        db.delete(TABLE1,"_id"+" = ?", new String[]{Integer.toString(key)});
        db.close();
    }
}
