package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.FakeTerminal;
import com.wallpaper.tim.phoneinsidewallpaper.Draw.TechEdge;
import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

/**
 * 模仿終端機的桌面小工具
 */

public class TerminalWidgetProvider extends AppWidgetProvider {

    private FakeTerminal fakeTerminal;
    private Handler handler;
    private Setting setting;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reDraw();
        }
    };

    private static final int REPEAT_INTERVAL = 1000;

    private Context context;
    private AppWidgetManager mAppWidgetManager;
    private int id;

    private void init(Context context){

        if(handler == null){
            handler = new Handler();
        }

        if(setting == null){
            setting = new Setting(context);
        }

        if(fakeTerminal == null){
            fakeTerminal = new FakeTerminal(context, setting.getTerminalTextColor(), setting.getTerminalTextSize());
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        fakeTerminal = null;
        unregister();
        setting = null;
    }

    private void register(){
        handler.postDelayed(runnable,REPEAT_INTERVAL);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        log("onUpdate");
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            log("onUpdate and draw");
            id = appWidgetIds[0];
            mAppWidgetManager = appWidgetManager;
            this.context = context;
            init(context);
            fakeTerminal.reset();
            register();
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        log("WidgetOptionsChanged");
        mAppWidgetManager = appWidgetManager;
        this.context = context;
        id = appWidgetId;
        init(context);
        fakeTerminal.reset();

        int h_dp = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, 0);
        log("get new Height? value="+h_dp);

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    private void reDraw(){

        if(mAppWidgetManager == null){
            return;
        }
        Bundle bundle = mAppWidgetManager.getAppWidgetOptions(id);
        int h_dp = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        int w_dp = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);

        if(h_dp <= 0 || w_dp <= 0){
            return;
        }

        int w = w_dp * TypedValue.COMPLEX_UNIT_DIP;
        int h = h_dp * TypedValue.COMPLEX_UNIT_DIP;

        log("terminal height = "+ h_dp);
        TechEdge techEdge = new TechEdge(setting.getThemeColor());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.terminal_layout);
        views.setImageViewBitmap(R.id.window_edge, techEdge.normalEdge(w, h));
        views.setImageViewBitmap(R.id.terminal, drawTerminal(w, h));

        mAppWidgetManager.updateAppWidget(id, views);
        unregister();
        register();
    }

    private Bitmap drawTerminal(int w, int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Canvas chartCanvas = new Canvas(bitmap);
        fakeTerminal.draw(chartCanvas);
        return bitmap;
    }

    @Override
    public void onDisabled(Context context) {
        unregister();
        super.onDisabled(context);
    }

    private void unregister(){
        if(handler != null){
            handler.removeCallbacks(runnable);
        }
    }

    private void log(String msg){
        Log.d("TerminalWidget", msg);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        log("onRestored");
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
