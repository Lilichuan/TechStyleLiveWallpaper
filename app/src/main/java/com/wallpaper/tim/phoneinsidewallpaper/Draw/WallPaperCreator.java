package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class WallPaperCreator {

    private int secondSplit;

    private boolean visible, showText, showSecondCircle, showBattery;
    private Paint textPaint, bigCirclePaint, bigCircleBatteryPaint;
    private DrawSecondTool drawSecondTool;
    private BatteryTool batteryTool;
    private FakeTerminal fakeTerminal;
    private float textH, splitCircleH;


    public WallPaperCreator(Context context){
        Setting setting = new Setting(context);
        String color = setting.getColor();
        showText = setting.isShowClock();
        showSecondCircle = setting.getShow2Layer();
        secondSplit = setting.get2ndLayerSplit();
        showBattery = setting.isShowBattery();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor(color));
        Typeface type = Typeface.createFromAsset(context.getAssets(),"square_sans_serif_7.ttf");
        textPaint.setTypeface(type);

        bigCirclePaint = new Paint();
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setColor(Color.parseColor(color));
        bigCirclePaint.setStyle(Paint.Style.STROKE);

        bigCircleBatteryPaint = new Paint(bigCirclePaint);
        bigCircleBatteryPaint.setColor(Color.parseColor(setting.getFadeColor()));

        drawSecondTool = new DrawSecondTool(secondSplit, color, setting.getFadeColor());
        batteryTool = new BatteryTool(context);
        fakeTerminal = new FakeTerminal(context, "#00ff00");
    }



    public void draw(Canvas canvas){

        if(visible){

            canvas.drawColor(Color.parseColor("#000000"));
            bigCirclePaint.setStrokeWidth(getCircleStrokeW(canvas));
            float x = canvas.getWidth()/ 2;
            float y = canvas.getHeight() / 2;

            if(showBattery){
                bigCircleBatteryPaint.setStrokeWidth(getCircleStrokeW(canvas));
                canvas.drawCircle(x, y, getBigCircleRadius(canvas), bigCircleBatteryPaint);
                float r = getBigCircleRadius(canvas);
                float left = (canvas.getWidth()/2) - r;
                float top = (canvas.getHeight() / 2) - r;
                float right = left + (2*r);
                float btn = top + (2*r);
                RectF rectF = new RectF(left, top, right, btn);
                canvas.drawArc(rectF, -90 , (360*batteryTool.getBatteryPct()) ,false, bigCirclePaint );
            }else {
                canvas.drawCircle(x, y, getBigCircleRadius(canvas), bigCirclePaint);
            }

            splitCircleH = getSecondCircleH(canvas);

            if(showText){
                textH = countTextSize(splitCircleH);
                textPaint.setTextSize(textH);
                String s = createNowTimeText();
                float textLen = textPaint.measureText(s);
                float x2 = (canvas.getWidth() - textLen)/ 2;
                float y2 = (canvas.getHeight())/ 2 + (textH / 4);
                canvas.drawText(s,x2,y2,textPaint);
            }

            if(showSecondCircle){
                float leftMargin = (canvas.getWidth() - splitCircleH)/2;
                float topMargin = (canvas.getHeight() - splitCircleH)/2;
                RectF rectF = new RectF(leftMargin, topMargin, leftMargin + splitCircleH , topMargin + splitCircleH);
                drawSecondTool.setRectF(rectF);
                drawSecondTool.drawCanvas(canvas);
            }

            fakeTerminal.draw(canvas);
            canvas.save();
        }

    }

    private float getBigCircleRadius(Canvas canvas){
        int shortSite = getShortSide(canvas);
        return (float) (shortSite * 0.4);
    }

    private float getSecondCircleH(Canvas canvas){
        int shortSite = getShortSide(canvas);
        return (float) (shortSite * 0.7);
    }

    private float getCircleStrokeW(Canvas canvas){
        int shortSite = getShortSide(canvas);
        return (float) (shortSite * 0.05);
    }

    private float countTextSize(float secondCircleH){
        return (float)(secondCircleH * 0.15);
    }

    private int getShortSide(Canvas canvas){
        int h = canvas.getHeight();
        int w = canvas.getWidth();
        return (h < w) ? h : w;
    }

    private static final String FORMAT = "HH:mm:ss";

    public static String createNowTimeText(){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT , Locale.US);
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void cleanCanvas(Canvas canvas){
        canvas.drawColor(Color.parseColor("#000000"));
        canvas.save();
    }
}
