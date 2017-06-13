package com.wallpaper.tim.phoneinsidewallpaper.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.wallpaper.tim.phoneinsidewallpaper.Draw.BatteryTool;
import com.wallpaper.tim.phoneinsidewallpaper.Draw.TechEdge;
import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;


public class BatteryWidgetProvider extends AppWidgetProvider {
    private BatteryTool batteryTool;
    private Setting setting;
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
        //int h_dp = 40;
        int w_dp = (int) context.getResources().getDimension(R.dimen.widget_max_h);

        int w = w_dp * TypedValue.COMPLEX_UNIT_DIP;
        //int h = h_dp * TypedValue.COMPLEX_UNIT_DIP;

        if(batteryTool == null){
            batteryTool = new BatteryTool(context);
        }

        if(setting == null){
            setting = new Setting(context);
        }

        if(techEdge == null){
            techEdge = new TechEdge(Colors.BLUE);
        }

        float select = batteryTool.getBatteryAgain();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextColor(R.id.text, Color.parseColor(Colors.BLUE));
        views.setImageViewBitmap(R.id.window_edge, techEdge.techEdge(w*2, w));
        views.setImageViewBitmap(R.id.line_chart, drawChart(w, Setting.LINE_CHART_STROKE, select));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    private final int SPLIT_COUNT = 10;
    private final int margin = 20;
    private Bitmap drawChart(int w, int h, float percent){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Canvas chartCanvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor(Colors.BLUE));
        paint.setStrokeWidth(h);

        w -= margin*2;
        float unit_w = w / SPLIT_COUNT;
        float column_w = unit_w * 4 / 5;
        int normal_column = (int)(SPLIT_COUNT * percent);

        int a = margin;
        for (int i = 1; i <= SPLIT_COUNT; i++){
            if(i > normal_column){
                paint.setColor(Color.parseColor(setting.getFadeColor()));
            }
            chartCanvas.drawLine(a, 0, a + column_w, 0, paint);
            a += unit_w;
        }
        chartCanvas.save();
        return bitmap;
    }


}
