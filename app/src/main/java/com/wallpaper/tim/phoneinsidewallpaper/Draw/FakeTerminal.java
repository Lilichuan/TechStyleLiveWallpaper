package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by tim on 2016/11/8.
 */

public class FakeTerminal {

    private Paint paint;

    public FakeTerminal(Context context,String color){
        paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(false);

        //TODO set ttf file
        //Typeface type = Typeface.createFromAsset(context.getAssets(),"square_sans_serif_7.ttf");
    }


}
