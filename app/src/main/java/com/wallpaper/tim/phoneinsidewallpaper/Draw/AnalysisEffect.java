package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

public class AnalysisEffect {

    private Paint paint, textPaint;
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

    //目前跑了幾格動畫，當作逐格動畫的順序標準
    private int frameCount = 1;

    private RectF smallCircleRect ,bigCircleRect;

    private final int DIRECTION_LEFT_TOP = 1;
    private final int DIRECTION_LEFT_DOWN = 2;
    private final int DIRECTION_RIGHT_TOP = 3;
    private final int DIRECTION_RIGHT_DOWN = 4;

    private TechEdge techEdge;

    private SmallWindow smallWindow1;
    private int SMALL_WINDOW_TEXT_SIZE = 13;

    public AnalysisEffect(String color){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(STROKE_W);
        paint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(paint);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(SMALL_WINDOW_TEXT_SIZE);

        float count = 360 - (CIRCLE_SPLIT * SEPARATE_DEGREE);
        singleRadianDegree = count / CIRCLE_SPLIT;
        techEdge = new TechEdge(color);

    }

    private void initSmallWindow(){

        if(smallWindow1 != null){
            return;
        }

        smallWindow1 = new SmallWindow() {
            @Override
            public void drawInRect(Canvas canvas, RectF self_edge) {
                String[] array = {"001000100001111","10110011110111","1000111111100","010101010111"};
                int start = frameCount % array.length;

                int count = 0;
                float textY = self_edge.top;
                float textX = self_edge.left;

                while (count < array.length){
                    canvas.drawText(array[start], textX, textY,textPaint);
                    textY += SMALL_WINDOW_TEXT_SIZE + 3;
                    start++;
                    if(start >= array.length){
                        start = 0;
                    }
                    count++;
                }

            }
        };
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
        canvas.save();

        initSmallWindow();
        smallWindow1.init(canvas, clickX, clickY, bigRect);
        smallWindow1.draw(canvas, techEdge);

        return after_a_frame();
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
        frameCount ++;

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
        resetPosition();
        time_passed = 0;
        bigCircleStartAngle = 0;
        smallCircleStartAngle = 360;
        frameCount = 1;
        smallWindow1 = null;
    }

    public void resetPosition(){
        bigCircleRect = smallCircleRect = null;
    }

    private abstract class SmallWindow{

        private RectF rectF;
        private float[] lineArray = new float[8];
        private int direction_info;


        public SmallWindow(){

        }

        public void init(Canvas canvas, float clickX, float clickY, RectF circleRect){
            float w = (float) (circleRect.width() * 0.6);
            float h = circleRect.height() / 7;
            float startX, startY;
            float margin = circleRect.width() / 9;

            float q_w = w / 4;

            calculate_small_window_direction(canvas, clickX, clickY);

            switch (direction_info){
                case DIRECTION_LEFT_TOP:
                    startX = circleRect.left - margin - w;
                    startY = circleRect.top - margin - h;
                    lineArray[0] = circleRect.left + q_w;
                    lineArray[1] = circleRect.top;
                    lineArray[3] = lineArray[5] = lineArray[7] = startY + (h / 2);
                    lineArray[2] = lineArray[0] - (lineArray[1] - lineArray[3]);
                    lineArray[4] = lineArray[2];
                    lineArray[6] = startX + w;

                    break;
                case DIRECTION_LEFT_DOWN:
                    startX = circleRect.left - (margin*4) - w;
                    startY = circleRect.bottom + margin + h;
                    lineArray[0] = circleRect.left + q_w;
                    lineArray[1] = circleRect.bottom;
                    lineArray[3] = lineArray[5] = lineArray[7] = startY + (h / 2);
                    lineArray[2] = lineArray[0] - (lineArray[3] - lineArray[1]);
                    lineArray[4] = lineArray[2];
                    lineArray[6] = startX + w;

                    break;
                case DIRECTION_RIGHT_TOP:
                    startX = circleRect.right + margin;
                    startY = circleRect.top - margin - h;
                    lineArray[0] = circleRect.right - q_w;
                    lineArray[1] = circleRect.top;
                    lineArray[3] = lineArray[5] = lineArray[7] = startY + (h / 2);
                    lineArray[2] = lineArray[0] + (lineArray[1] - lineArray[3]);
                    lineArray[4] = lineArray[2];
                    lineArray[6] = startX;
                    break;
                case DIRECTION_RIGHT_DOWN:
                    startX = circleRect.right + margin;
                    startY = circleRect.bottom + margin;
                    lineArray[0] = circleRect.right - q_w;
                    lineArray[1] = circleRect.bottom;
                    lineArray[3] = lineArray[5] = lineArray[7] = startY + (h / 2);
                    lineArray[2] = lineArray[0] + (lineArray[3] - lineArray[1]);
                    lineArray[4] = lineArray[2];
                    lineArray[6] = startX;
                    break;
                default:
                    startX = startY = 0;
            }

            rectF = new RectF(startX, startY, startX + w, startY + h);
        }


        public void draw(Canvas canvas, TechEdge techEdge){
            techEdge.normalEdge(canvas, rectF);
            canvas.drawLines(lineArray, techEdge.getPaint());
            canvas.clipRect(rectF);
            drawInRect(canvas, rectF);
            canvas.restore();
        }

        public abstract void drawInRect(Canvas canvas, RectF self_edge);

        private void calculate_small_window_direction(Canvas canvas, float clickX, float clickY){

            float toLeft, toRight, toTop, toBottom;
            toLeft = clickX;
            toRight = canvas.getWidth() - clickX;
            toTop = clickY;
            toBottom = canvas.getHeight() - clickY;

            boolean top = toTop >= toBottom;
            boolean left = toLeft >= toRight;

            if(top){
                direction_info = left ? DIRECTION_LEFT_TOP : DIRECTION_RIGHT_TOP;
            }else {
                direction_info = left ? DIRECTION_LEFT_DOWN : DIRECTION_RIGHT_DOWN;
            }
        }
    }
}
