package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;


public class WallPaperCreator {

    private FakeTerminal fakeTerminal;
    private AnalysisEffect analysisEffect;
    private MotionEvent motionEvent;
//    private static final String TAG = "WallPaperCreator";
    private Paint bigCirclePaint;
    private DrawSecondTool drawSecondTool;
    private RectF smallCircleRectF;

    public WallPaperCreator(Context context){
        init(context);
    }

    public void init(Context context){

        Setting setting = new Setting(context);
        if(fakeTerminal == null){
            fakeTerminal = new FakeTerminal(context, Colors.BLUE, setting.getTerminalTextSize());
        }

        if(analysisEffect == null){
            analysisEffect = new AnalysisEffect(Colors.BLUE);
        }

        if(bigCirclePaint == null){
            bigCirclePaint = new Paint();
            bigCirclePaint.setAntiAlias(true);
            bigCirclePaint.setColor(Color.parseColor(setting.getCircleColor()));
            bigCirclePaint.setStyle(Paint.Style.STROKE);
        }


        if(drawSecondTool == null){
            drawSecondTool = new DrawSecondTool(setting.get2ndLayerSplit()
                    ,setting.getCircleColor()
                    ,setting.getFadeColor());
        }


    }

    public void draw(Context context, Canvas canvas){
        init(context);
        canvas.drawColor(Color.parseColor("#000000"));
        drawCircles(canvas);
        drawTerminal(canvas);
        if(isShowingClickAnimation()){
            boolean result = analysisEffect.draw(canvas, motionEvent.getX(), motionEvent.getY());
            if(!result){
                motionEvent = null;
            }
        }
        canvas.save();
    }

    private void drawCircles(Canvas canvas){
        bigCirclePaint.setStrokeWidth(getCircleStrokeW(canvas));
        canvas.drawCircle(canvas.getWidth()/ 2,
                canvas.getHeight() / 2,
                getBigCircleRadius(canvas),
                bigCirclePaint);

        if(smallCircleRectF == null){
            float splitCircleH = getSecondCircleH(canvas);
            float leftMargin = (canvas.getWidth() - splitCircleH)/2;
            float topMargin = (canvas.getHeight() - splitCircleH)/2;
            smallCircleRectF = new RectF(leftMargin, topMargin, leftMargin + splitCircleH , topMargin + splitCircleH);
        }

        drawSecondTool.drawCanvas(smallCircleRectF, canvas);
    }

    private void drawTerminal(Canvas canvas){

//        if(terminalRectF == null){
//            terminalRectF = new RectF(0,0,canvas.getWidth() / 3, canvas.getHeight());
//        }
//        techEdge.normalEdge(canvas, terminalRectF);
//        fakeTerminal.draw(canvas, terminalRectF);
        fakeTerminal.draw(canvas);
    }


//    public void setVisible(boolean visible) {
//        this.visible = visible;
//        if(!visible){
//            clear();
//        }
//    }

    public void clear(){
        analysisEffect.reset();
        bigCirclePaint = null;
        motionEvent = null;
        smallCircleRectF = null;
        drawSecondTool = null;
        fakeTerminal = null;
    }

    public void cleanCanvas(Canvas canvas){
        canvas.drawColor(Color.parseColor("#000000"));
        canvas.save();
    }

    public void setMotionEvent(MotionEvent me){
        motionEvent = me;
        analysisEffect.resetPosition();
//        if(me.getAction() == MotionEvent.ACTION_DOWN){
//
//        }
    }

    public boolean isShowingClickAnimation(){
        return motionEvent != null;
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

    private int getShortSide(Canvas canvas){
        int h = canvas.getHeight();
        int w = canvas.getWidth();
        return (h < w) ? h : w;
    }
}
