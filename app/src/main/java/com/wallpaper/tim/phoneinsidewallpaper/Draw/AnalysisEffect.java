package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

public class AnalysisEffect {

    private Paint paint;
    private float singleRadianDegree;
    private float SEPARATE_DEGREE = 5;
    private float STROKE_W = 20;

    private int time_passed = 0;

    private final int ROTATE_DEGREE_PER_FRAME = 7;
    private final int CIRCLE_SPLIT = 3;

    private int bigCircleStartAngle, smallCircleStartAngle;

    //每隔多久就刷新，單位是毫秒
    public static final int SINGLE_FRAME_TIME = 25;

    private RectF smallCircleRect ,bigCircleRect;

    public AnalysisEffect(String color){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(STROKE_W);
        paint.setStyle(Paint.Style.STROKE);

        float count = 360 - (CIRCLE_SPLIT * SEPARATE_DEGREE);
        singleRadianDegree = count / CIRCLE_SPLIT;
    }

    /*
    *
    * return: Need to display continue.
    * */
    public boolean draw(Canvas canvas, float clickX, float clickY){
        float h = canvas.getHeight();
        float w = canvas.getWidth();
        float bigDiameter = h > w ? h : w;
        bigDiameter = bigDiameter / 5;

        RectF bigRect = getBigCircleRect(bigDiameter, clickX, clickY);
        RectF smallRect = getSmallCircleRect(bigDiameter,clickX, clickY);

        drawCircle(canvas, bigRect, bigCircleStartAngle);
        drawCircle(canvas, smallRect, smallCircleStartAngle);

        return after_a_frame();
    }

    private void drawCircle(Canvas canvas, RectF rectF, int startDegree){
        int temp_start = startDegree;

        for (int i = 0 ;i < CIRCLE_SPLIT; i++){
            canvas.drawArc(rectF, temp_start, singleRadianDegree, false, paint);
            temp_start += singleRadianDegree;
            temp_start += SEPARATE_DEGREE;
        }
    }

    private boolean after_a_frame(){
        time_passed += SINGLE_FRAME_TIME;

        bigCircleStartAngle += ROTATE_DEGREE_PER_FRAME;
        if(bigCircleStartAngle > 360){
            bigCircleStartAngle -= 360;
        }

        smallCircleStartAngle -= ROTATE_DEGREE_PER_FRAME;
        if(smallCircleStartAngle < 0){
            smallCircleStartAngle = 360;
        }

        boolean need_continue = time_passed < Setting.ANIMATION_TIME;
        if(!need_continue){
            reset();
        }

        return need_continue;
    }

    public boolean isDisplay(){
        return time_passed > 0;
    }

    private RectF getSmallCircleRect(float bigDiameter, float clickX, float clickY){
        if(smallCircleRect != null){
            return smallCircleRect;
        }else {
            float diameter = bigDiameter - (STROKE_W * 4);
            smallCircleRect = createCircleRect(diameter, clickX, clickY);
            return smallCircleRect;
        }
    }

    private RectF getBigCircleRect(float bigDiameter, float clickX, float clickY){
        if(bigCircleRect != null){
            return bigCircleRect;
        }else {
            bigCircleRect = createCircleRect(bigDiameter, clickX, clickY);
            return bigCircleRect;
        }
    }

    private RectF createCircleRect(float diameter, float clickX, float clickY){
        float radius = diameter / 2;
        return new RectF(clickX - radius ,clickY - radius ,clickX + radius , clickY + radius);
    }

    private void reset(){
        bigCircleRect = smallCircleRect = null;
        time_passed = 0;
        bigCircleStartAngle = 0;
        smallCircleStartAngle = 360;
    }
}
