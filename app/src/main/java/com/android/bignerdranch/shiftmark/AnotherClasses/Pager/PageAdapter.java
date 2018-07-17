package com.android.bignerdranch.shiftmark.AnotherClasses.Pager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.bignerdranch.shiftmark.AnotherClasses.Pager.Fragment;

/**
 * Created by X1opya on 28.02.2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        return Fragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 12;
    }
}

