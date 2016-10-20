package com.wallpaper.tim.phoneinsidewallpaper.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.WallPaperCreator;

/**
 * Created by tim on 2016/10/19.
 * 利用View之中的Canvas在app界面中呈現畫面
 *
 *
 */

public class MWallPaperView extends View {

    private WallPaperCreator wallPaperCreator;
    private boolean pause = false;


    public MWallPaperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MWallPaperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MWallPaperView(Context context) {
        super(context);
        init(context);
    }

//    public MWallPaperView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }

    public void init(Context context){
        wallPaperCreator = new WallPaperCreator(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(wallPaperCreator != null){
            if(pause){
                wallPaperCreator.cleanCanvas(canvas);
            }else {
                wallPaperCreator.draw(canvas);
            }
        }
        wallPaperCreator.draw(canvas);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
        wallPaperCreator.setVisible(!pause);
    }
}
