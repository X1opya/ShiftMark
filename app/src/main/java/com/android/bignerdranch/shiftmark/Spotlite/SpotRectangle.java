package com.android.bignerdranch.shiftmark.Spotlite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.takusemba.spotlight.shape.Shape;

public class SpotRectangle implements Shape {

    private float x;
    private float y;

    public SpotRectangle(float width, float height) {
        this.x = width;
        this.y = height;
    }

    @Override
    public void draw(Canvas canvas, PointF point, float value, Paint paint) {
        canvas.drawRect(point.x,point.y, point.x+x,point.y+y,paint);
    }

    @Override
    public int getHeight() {
        return (int)y;
    }

    @Override
    public int getWidth() {
        return (int)x;
    }
}
