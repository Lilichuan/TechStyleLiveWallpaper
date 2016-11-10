package com.wallpaper.tim.phoneinsidewallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.WallPaperCreator;

/**
 * Created by tim on 2016/11/10.
 */

public class TechWallpaperService extends WallpaperService {



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public Engine onCreateEngine() {
        return new TechEngine(this);
    }

    private class TechEngine extends Engine{

        private Handler handler = new Handler();
        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        private WallPaperCreator wallPaperCreator;
        private SurfaceHolder surfaceHolder;

        TechEngine(Context context){
            wallPaperCreator = new WallPaperCreator(context);
        }

        private void init(){

        }


        private void draw(){
            if(surfaceHolder != null && isVisible()){
                Canvas canvas = surfaceHolder.lockCanvas();
                wallPaperCreator.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 1000);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            surfaceHolder = holder;
            Canvas canvas = holder.lockCanvas();
            wallPaperCreator.setVisible(true);
            wallPaperCreator.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            wallPaperCreator.setVisible(false);
            handler.removeCallbacks(runnable);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                handler.post(runnable);
            } else {
                handler.removeCallbacks(runnable);
            }
        }


    }
}
