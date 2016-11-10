package com.wallpaper.tim.phoneinsidewallpaper;

import android.content.Context;
import android.graphics.Canvas;
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

        private WallPaperCreator wallPaperCreator;

        TechEngine(Context context){
            wallPaperCreator = new WallPaperCreator(context);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
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
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            wallPaperCreator.setVisible(false);
        }
    }
}
