package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.FakeTerminal;
import com.wallpaper.tim.phoneinsidewallpaper.Draw.TechEdge;
import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

/**
 * 模仿終端機的桌面小工具
 */

public class TerminalWidgetProvider extends AppWidgetProvider {

    private FakeTerminal fakeTerminal;

    private void init(Context context){
        if(fakeTerminal != null){
            return;
        }
        Setting setting = new Setting(context);
        fakeTerminal = new FakeTerminal(context, setting.getTerminalTextColor(), setting.getTerminalTextSize());
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        fakeTerminal = null;
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (appWidgetIds != null && appWidgetIds.length > 0) {
            int appWidgetId = appWidgetIds[0];
            reDraw(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        fakeTerminal = null;
        reDraw(context, appWidgetManager, appWidgetId);
    }

    private void reDraw(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        init(context);

        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int h_dp = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        int w_dp = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int w = w_dp * TypedValue.COMPLEX_UNIT_DIP;
        int h = h_dp * TypedValue.COMPLEX_UNIT_DIP;

//        Setting setting = new Setting(context);
        TechEdge techEdge = new TechEdge(Colors.BLUE);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.terminal_layout);
        views.setImageViewBitmap(R.id.window_edge, techEdge.techEdge(w, h));
        views.setImageViewBitmap(R.id.terminal, drawTerminal(w, h));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private Bitmap drawTerminal(int w, int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Canvas chartCanvas = new Canvas(bitmap);
        RectF rectF = new RectF(0, 0 , w, h);
        fakeTerminal.draw(chartCanvas, rectF);
        return bitmap;
    }
}
