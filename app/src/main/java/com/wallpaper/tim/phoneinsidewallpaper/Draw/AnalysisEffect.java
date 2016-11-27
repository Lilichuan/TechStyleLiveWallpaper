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

    //每一個影格要讓圈圈轉多少角度
    private final int ROTATE_DEGREE_PER_FRAME = 2;
    //圈圈分裂成幾段
    private final int CIRCLE_SPLIT = 3;

    //紀錄當下圈圈旋轉的角度，值0~360
    private int bigCircleStartAngle, smallCircleStartAngle;

    //每隔多久就刷新，單位是毫秒
    public static final int SINGLE_FRAME_TIME = 25;

    private RectF smallCircleRect ,bigCircleRect;

    private final int DIRECTION_LEFT_TOP = 1;
    private final int DIRECTION_LEFT_DOWN = 2;
    private final int DIRECTION_RIGHT_TOP = 3;
    private final int DIRECTION_RIGHT_DOWN = 4;

    private int direction_info_window = -1;

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
    * 回傳值：是否繼續呈現動畫
    * */
    public boolean draw(Canvas canvas, float clickX, float clickY){
        float h = canvas.getHeight();
        float w = canvas.getWidth();
        float bigDiameter = h > w ? h : w;
        bigDiameter = bigDiameter / 5;

        RectF bigRect = getBigCircleRect(bigDiameter, clickX, clickY);
        RectF smallRect = getSmallCircleRect(bigDiameter,clickX, clickY);

        drawCircle(canvas, bigRect, bigCircleStartAngle, STROKE_W);
        drawCircle(canvas, smallRect, smallCircleStartAngle, STROKE_W / 2);

        calculate_small_window_direction(canvas, clickX, clickY);

        return after_a_frame();
    }

    private void calculate_small_window_direction(Canvas canvas, float clickX, float clickY){
        float toLeft, toRight, toTop, toBottom;
        toLeft = clickX;
        toRight = canvas.getWidth() - clickX;
        toTop = clickY;
        toBottom = canvas.getHeight() - clickY;

        boolean top = toTop >= toBottom;
        boolean left = toLeft >= toRight;

        if(top){
            direction_info_window = left ? DIRECTION_LEFT_TOP : DIRECTION_RIGHT_TOP;
        }else {
            direction_info_window = left ? DIRECTION_LEFT_DOWN : DIRECTION_RIGHT_DOWN;
        }
    }

    private void drawCircle(Canvas canvas, RectF rectF, int startDegree, float stroke_w){
        int temp_start = startDegree;

        paint.setStrokeWidth(stroke_w);

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

    public void reset(){
        bigCircleRect = smallCircleRect = null;
        time_passed = 0;
        bigCircleStartAngle = 0;
        smallCircleStartAngle = 360;
        direction_info_window = -1;
    }
}
