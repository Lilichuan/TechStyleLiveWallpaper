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


    public WallPaperCreator(Context context){
        Setting setting = new Setting(context);
        String color = setting.getColor();

        fakeTerminal = new FakeTerminal(context, "#00ff00");
        analysisEffect = new AnalysisEffect(Colors.YELLOW);

    }



    public void draw(Canvas canvas){

        if(visible){

            canvas.drawColor(Color.parseColor("#000000"));
            fakeTerminal.draw(canvas);

            if(isShowingClickAnimation()){
                boolean result = analysisEffect.draw(canvas, motionEvent.getX(), motionEvent.getY());
                if(!result){
                    motionEvent = null;
                }
            }
            canvas.save();
        }

    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void cleanCanvas(Canvas canvas){
        canvas.drawColor(Color.parseColor("#000000"));
        canvas.save();
    }

    public void setMotionEvent(MotionEvent me){
        if(me.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
            motionEvent = me;
        }
    }

    public boolean isShowingClickAnimation(){
        return motionEvent != null && analysisEffect.isDisplay();
    }
}
