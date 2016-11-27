package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.TechEdge;

/**
 * 模仿終端機的桌面小工具
 */

public class TerminalWidgetProvider extends AppWidgetProvider {

    private TechEdge techEdge;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (appWidgetIds != null && appWidgetIds.length > 0) {
            int appWidgetId = appWidgetIds[0];
            reDraw(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        reDraw(context, appWidgetManager, appWidgetId);
    }

    private void reDraw(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int h_dp = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        int w_dp = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int w = w_dp * TypedValue.COMPLEX_UNIT_DIP;
        int h = h_dp * TypedValue.COMPLEX_UNIT_DIP;


    }
}
