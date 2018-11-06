package com.android.bignerdranch.shiftmark.data.Premium;

import java.util.Calendar;

/**
 * Created by X1opya on 26.06.2018.
 */

public class Premium {
    protected String description;
    protected int many;
    protected Calendar calendar;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    protected int index;

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Premium(int many, String d, Calendar c) {
        this.many = many;
        description = d;
        calendar = c;
    }

    public String getDescription() {
        if(description.length()==0) return "";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIntMany() {
        return many;
    }

    public String getMany() {
        return Integer.toString(many);
    }

    public void setMany(int many) {
        this.many = many;
    }
}
