package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.BatteryTool;
import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;


public class BatteryWidgetProvider extends AppWidgetProvider {
    private BatteryTool batteryTool;

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
        int h = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        WidgetTool widgetTool = new WidgetTool(context, Colors.YELLOW, h);

        if(batteryTool == null){
            batteryTool = new BatteryTool(context);
        }

        float select = batteryTool.getBatteryAgain();
        int percent = ((int)(select*100));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.text, percent + "%");
        views.setTextViewText(R.id.text2, context.getString(R.string.battery));

        views.setImageViewBitmap(R.id.circle, widgetTool.draw((int)(select*10), select));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


}
