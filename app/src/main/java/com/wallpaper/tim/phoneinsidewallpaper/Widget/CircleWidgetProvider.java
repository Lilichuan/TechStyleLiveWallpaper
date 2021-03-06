package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 呈現星期幾的圓圈桌面小工具
 */
public class CircleWidgetProvider extends AppWidgetProvider {

    private WidgetTool widgetTool;

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

        int h =(int) context.getResources().getDimension(R.dimen.widget_h);

        widgetTool = new WidgetTool(context, Colors.BLUE , h, 7);

        Locale locale = Locale.getDefault();
        Calendar calendar = Calendar.getInstance(locale);

        SimpleDateFormat format = new SimpleDateFormat("EEE", locale);
        String text = format.format(calendar.getTime());

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int adjustDayOfWeek = 0;

        switch (dayOfWeek){
            case Calendar.SUNDAY:
                adjustDayOfWeek = 7;
                break;
            case Calendar.MONDAY:
                adjustDayOfWeek = 1;
                break;
            case Calendar.TUESDAY:
                adjustDayOfWeek = 2;
                break;
            case Calendar.WEDNESDAY:
                adjustDayOfWeek = 3;
                break;
            case Calendar.THURSDAY:
                adjustDayOfWeek = 4;
                break;
            case Calendar.FRIDAY:
                adjustDayOfWeek = 5;
                break;
            case Calendar.SATURDAY:
                adjustDayOfWeek = 6;
                break;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_2);
        views.setTextViewText(R.id.text, text);
        views.setTextColor(R.id.text, Color.parseColor(Colors.BLUE));
        views.setImageViewBitmap(R.id.circle, widgetTool.draw(adjustDayOfWeek, (float) adjustDayOfWeek / 7f));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
