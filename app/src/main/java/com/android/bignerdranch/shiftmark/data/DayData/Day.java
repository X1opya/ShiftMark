package com.android.bignerdranch.shiftmark.data.DayData;

import java.util.Calendar;

/**
 * Created by X1opya on 30.04.2018.
 */

public class Day extends DaySettings {

    private double amount;
    private double tips;
    private int salary;
    public static final int YEAR=1;
    public static final int MONTH=2;
    public static final int DAY=3;
    Calendar date;


    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getAmount() {
        if(amount==0) return "0";
        return String.valueOf(amount);
    }

    public void setAmount(String amount) {
        if(amount.length()!=0)
        this.amount = Double.parseDouble(amount);
        else  if(percent==0) this.amount=0;
    }

    public String getSalary() {
        if(salary==0) return "0";
        return String.valueOf(salary);
    }

    public void setSalary(String salary) {
        if(salary.length()!=0)
            this.salary = Integer.parseInt(salary);
    }

    public String getTips() {
        if(tips==0) return "0";
        return String.valueOf(tips);
    }

    public void setTips(String tips) {
        if(tips.length()!=0)
        this.tips = Double.parseDouble(tips);
    }

    public  String getSpecificDate(int mod){
        switch (mod){
            case 1: return String.valueOf(date.get(Calendar.YEAR));
            case 2: return String.valueOf(date.get(Calendar.MONTH));
            case 3: return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
            default: return "-10";
        }
    }


    public Day() {
        super();
        date = Calendar.getInstance();
    }
}
