package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;


public class WallPaperCreator {

    private FakeTerminal fakeTerminal;
    private AnalysisEffect analysisEffect;
    private MotionEvent motionEvent;
    private boolean visible;
    private static final String TAG = "WallPaperCreator";

    public WallPaperCreator(Context context){
        Setting setting = new Setting(context);
        fakeTerminal = new FakeTerminal(context, Colors.TERMINAL_GREEN, setting.getTerminalTextSize());
        analysisEffect = new AnalysisEffect(Colors.YELLOW);
    }

    public void draw(Canvas canvas){

        if(visible){
            drawTerminal(canvas);
            if(isShowingClickAnimation()){
                boolean result = analysisEffect.draw(canvas, motionEvent.getX(), motionEvent.getY());
                if(!result){
                    motionEvent = null;
                    drawTerminal(canvas);
                }
            }
            canvas.save();
        }

    }

    private void drawTerminal(Canvas canvas){
        canvas.drawColor(Color.parseColor("#000000"));
        fakeTerminal.draw(canvas);
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
        if(!visible){
            motionEvent = null;
        }
    }

    public void cleanCanvas(Canvas canvas){
        canvas.drawColor(Color.parseColor("#000000"));
        canvas.save();
    }

    public void setMotionEvent(MotionEvent me){
        if(me.getAction() == MotionEvent.ACTION_DOWN
                && motionEvent == null){
            motionEvent = me;
        }
    }

    public boolean isShowingClickAnimation(){
        return motionEvent != null;
    }
}
