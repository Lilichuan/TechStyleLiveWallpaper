package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;

import java.util.Calendar;


public class WorkWidgetProvider extends AppWidgetProvider {

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

        WidgetTool widgetTool = new WidgetTool(context, Colors.GREEN, h);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        int now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        float a = 0;

        if(now > 21 ){
            a = ((float) (now - 21)) / (float)9;
        }else if(now < 6){
            a = ((float) (now + 3)) / (float) 9;
        }else if(now >= 6 || now <= 21){
            a = ((float) (21-now)) / (float) 15;
        }

        int pers = (int)(a*100);
        views.setTextViewText(R.id.text, pers + "%");
        views.setTextViewText(R.id.text2, context.getString(R.string.strength));
        views.setImageViewBitmap(R.id.circle, widgetTool.draw((int)(a * 10), a));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
