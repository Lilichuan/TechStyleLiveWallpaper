package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by tim on 2016/11/13.
 */

public class AnalysisEffect {

    private Paint paint;
    private float singleRadianDegree;
    private float SEPARATE_DEGREE = 5;
    private float STROKE_W = 20;

    //change 45 degree per second
    private float ANGLE_CHANGE_PER_UNIT = 45;

    private RectF smallCircleRect ,bigCircleRect;

    public AnalysisEffect(String color){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(STROKE_W);
        paint.setStyle(Paint.Style.STROKE);

        singleRadianDegree = 120 - SEPARATE_DEGREE;
    }

    public void draw(Canvas canvas, int clickX, int clickY){
        float h = canvas.getHeight();
        float w = canvas.getWidth();
        float bigDiameter = h > w ? h : w;
        bigDiameter = bigDiameter / 5;

//        canvas.drawArc(getBigCircleRect(bigDiameter, clickX, clickY),
//                );

    }

    private RectF getSmallCircleRect(float bigDiameter, int clickX, int clickY){
        if(smallCircleRect != null){
            return smallCircleRect;
        }else {
            float diameter = bigDiameter - (STROKE_W * 4);
            smallCircleRect = createCircleRect(diameter, clickX, clickY);
            return smallCircleRect;
        }
    }

    private RectF getBigCircleRect(float bigDiameter, int clickX, int clickY){
        if(bigCircleRect != null){
            return bigCircleRect;
        }else {
            bigCircleRect = createCircleRect(bigDiameter, clickX, clickY);
            return bigCircleRect;
        }
    }

    private RectF createCircleRect(float diameter, int clickX, int clickY){
        float radius = diameter / 2;
        return new RectF(clickX - radius ,clickY - radius ,clickX + radius , clickY + radius);
    }

    public void destroy(){
        bigCircleRect = smallCircleRect = null;
    }
}
