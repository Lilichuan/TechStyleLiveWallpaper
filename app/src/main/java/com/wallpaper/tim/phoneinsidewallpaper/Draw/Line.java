package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

/**
 * Created by tim on 2016/10/18.
 */

public class Line {

    private float[] path;
    private Paint paint;
    private Setting setting;
    private float strokeW;

    public Line(Context context){
        setting = new Setting(context);
        strokeW = (float) 20.0;
    }

    public void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(Colors.BLUE));
        paint.setStrokeWidth(strokeW);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setPath(float[] array){
        path = array;
    }

    public void drawSelf(Canvas canvas){
        canvas.drawLines(path, paint);
    }
}
