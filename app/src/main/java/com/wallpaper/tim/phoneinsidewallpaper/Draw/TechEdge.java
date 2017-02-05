package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class TechEdge {

    private Paint paint;
    private RectF rectF;
    public static final int stroke_w = 6;

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

    public void normalEdge(Canvas canvas, RectF rectF){
        initNormalPaint();
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

    public Bitmap techEdge(int w, int h){
        paint.setStrokeWidth(stroke_w);
        initCanvas(w, h);
        m_canvas.drawLines(createArray(w, h), paint);
        m_canvas.save();
        rectF = null;
        return bmp;
    }

    //繪製科技感邊框所需的陣列
    private float[] createArray(int w, int h){
        float x = 0, y = 0, depth = w / 30;
        float flat = h / 8, flat2 = w / 3;

        float[] array = new float[112];
        array[0] = x;
        array[1] = y;
        array[2] = x;
        array[3] = flat;

        array[6] = array[2] + depth;
        array[7] = array[3] + depth;

        array[14] = x;
        array[15] = ((h - flat * 3) / 2) + flat;

        array[10] = array[6];
        array[11] = array[15] - depth;

        array[18] = x;
        array[19] = array[15] + flat;

        array[22] = array[6];
        array[23] = array[19] + depth;

        array[30] = x;
        array[31] = h - flat;

        array[26] = array[6];
        array[27] = array[31] - depth;

        array[34] = x;
        array[35] = h + y;

        array[38] = x + flat2;
        array[39] = array[35];

        array[42] = array[38] + depth;
        array[43] = array[39] - depth;

        array[50] = x + w - flat2;
        array[51] = array[35];

        array[46] = array[50] - depth;
        array[47] = array[43];

        array[54] = x + w;
        array[55] = array[35];

        array[58] = array[54];
        array[59] = array[31];

        array[62] = x + w - depth;
        array[63] = array[27];

        array[66] = array[62];
        array[67] = array[23];

        array[70] = array[54];
        array[71] = array[19];

        array[74] = array[54];
        array[75] = array[15];

        array[78] = array[62];
        array[79] = array[11];

        array[82] = array[62];
        array[83] = array[7];

        array[86] = array[54];
        array[87] = array[3];

        array[90] = array[54];
        array[91] = y;

        array[94] = array[50];
        array[95] = y;

        array[98] = array[46];
        array[99] = y + depth;

        array[102] = array[42];
        array[103] = array[99];

        array[106] = array[38];
        array[107] = y;

        array[110] = x;
        array[111] = y;

        for (int i = 4 ; i < array.length ; i+=4){
            array[i] = array[i-2];
            array[i+1] = array[i-1];
        }

        return array;
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

    public static int getStrokeWidth() {
        return stroke_w;
    }

    public Paint getPaint(){
        return paint;
    }
}
