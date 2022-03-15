package com.wallpaper.tim.phoneinsidewallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.AnalysisEffect;
import com.wallpaper.tim.phoneinsidewallpaper.Draw.WallPaperCreator;

import java.util.Timer;
import java.util.TimerTask;

public class TechWallpaperService extends WallpaperService {



    @Override
    public Engine onCreateEngine() {
        Log.d("TechWallpaperService", "start onCreateEngine");
        TechEngine engine = new TechEngine(getApplicationContext());
        return engine;
    }

    private class TechEngine extends Engine{

        private static final String TAG = "TechWallpaperService";

        boolean isRunning = true;

        /*
        *
        * Use Handler and Runnable to draw animation
        * 利用 Handler 和 Runnable定時繪製動畫
        *
        * */
        //private Handler handler;

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

//        private Handler.Callback callback = new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message message) {
//
//                if(isRunning){
//                    draw();
//                    handler.sendEmptyMessageDelayed(0, 1000);
//                }
//                return false;
//            }
//        };

        private TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        };

        /*
        *
        * 所有繪製有關的物件與計算
        * 都被包裝在 WallPaperCreator 裡面
        *
        * */
        private WallPaperCreator wallPaperCreator;

        private SurfaceHolder surfaceHolder;

        private Timer timer;

        TechEngine(Context context){
            init(context);
        }

        private void init(Context context){
            Log.d(TAG, "Start init()");
            timer = new Timer();
            wallPaperCreator = new WallPaperCreator(context);
            timer.schedule(timerTask, 1000);
            //handler = new Handler(Looper.getMainLooper(), callback);
            //handler.sendEmptyMessageDelayed(0, 50);
        }


        private void draw(){
            if(surfaceHolder != null && isVisible() && isRunning){
                Canvas canvas = surfaceHolder.lockCanvas();
                wallPaperCreator.draw(getBaseContext(), canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            //int delay = wallPaperCreator.isShowingClickAnimation() ? AnalysisEffect.SINGLE_FRAME_TIME : 1000;
            //handler.sendEmptyMessageDelayed(0, delay);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            surfaceHolder = holder;
            Canvas canvas = holder.lockCanvas();
            getWallPaperCreator().clear();
            getWallPaperCreator().draw(getBaseContext(), canvas);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            wallPaperCreator.setMotionEvent(event);
            //handler.sendEmptyMessageDelayed(0, 1000);

            super.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            //handler.removeCallbacks(runnable);
            isRunning = false;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                //handler.post(runnable);
            } else {
                //handler.removeCallbacks(runnable);
                wallPaperCreator.clear();
            }
        }

        private WallPaperCreator getWallPaperCreator(){
            if(wallPaperCreator == null){
                wallPaperCreator = new WallPaperCreator(getApplicationContext());
            }

            return wallPaperCreator;
        }

        //測試用
        //For test
//        private void log(String s){
//            Log.d(TAG,s);
//        }


    }
}
