package com.android.bignerdranch.shiftmark.Spotlite;

import android.app.Activity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.android.bignerdranch.shiftmark.R;
import com.takusemba.spotlight.OnSpotlightStateChangedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.Spotlight;
import com.takusemba.spotlight.target.SimpleTarget;

public class SpotliteMarks {
    public static void globalMark(Activity context, View view, String title, String desc){
        SimpleTarget simpleTarget = new SimpleTarget.Builder(context)
                .setPoint(0f, 0f)
                .setShape(new SpotRectangle(view.getHeight(),view.getWidth()))
                .setTitle(title)
                .setDescription(desc)
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        // do something
                    }
                    @Override
                    public void onEnded(SimpleTarget target) {
                        // do something
                    }
                })
                .build();
        Spotlight.with(context)
                .setOverlayColor(R.color.background)
                .setDuration(1000L)
                .setAnimation(new DecelerateInterpolator(2f))
                .setTargets(simpleTarget)
                .setClosedOnTouchedOutside(true)
                .setOnSpotlightStateListener(new OnSpotlightStateChangedListener() {
                    @Override
                    public void onStarted() {
                    }

                    @Override
                    public void onEnded() {
                    }
                })
                .start();
    }
}
