package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class TechEdge {

    private Paint paint;
    private RectF rectF;
    private int stroke_w = 3;

    private Canvas m_canvas;
    private Bitmap bmp;

    public TechEdge(String color){
        paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);
    }

    public void normalEdge(Canvas canvas){
        initNormalPaint();
        initRect(canvas.getWidth(), canvas.getHeight());
        canvas.drawRect(rectF, paint);
    }

    public Bitmap normalEdge(int w, int h){
        initNormalPaint();
        initCanvas(w, h);
        initRect(w, h);
        m_canvas.drawRect(rectF, paint);
        m_canvas.save();
        rectF = null;
        return bmp;
    }

    private void initNormalPaint(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke_w);
    }

    private void initCanvas(int w, int h){
        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        m_canvas = new Canvas(bmp);
    }

    private void initRect(int w, int h){
        if(rectF == null){
            rectF = new RectF(0, 0, w, h);
        }
    }

    public void setStrokeWidth(int strokeWidth) {
        stroke_w = strokeWidth;
    }
}
