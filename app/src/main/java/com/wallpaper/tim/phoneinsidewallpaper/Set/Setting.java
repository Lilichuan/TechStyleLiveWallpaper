package com.wallpaper.tim.phoneinsidewallpaper.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by tim on 2016/10/17.
 * Set project value.
 */

public class Setting {

    private SharedPreferences sp;

    private static final String SP_NAME = "SettingValue" ;
    private static final String KEY_THEME_COLOR = "color";

    private static final String KEY_SHOW_CLOCK = "showClock";
    private static final String KEY_TERMINAL_TEXT_COLOR = "terminal_text_color";
    private static final String KEY_TERMINAL_TEXT_SIZE = "terminal_text_size";
    private static final String KEY_TERMINAL_BG_COLOR = "terminal_bg_color";

    public static final int TERMINAL_TEXT_SMALLEST = 10;
    public static final int TERMINAL_TEXT_BIGGEST = 60;

    public static final int ANIMATION_TIME = 600;

    public static final int LINE_CHART_STROKE = 30;

    public static final int CIRCLE_SPLIT_MAX = 180;
    public static final int CIRCLE_SPLIT_MINI = 3;
    private static final String KEY_2_LAYER_COUNT = "secondLayerCount";

    public Setting(Context context){
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void setThemeColor(String color){
        if(TextUtils.isEmpty(color)){
            return;
        }

        sp.edit().putString(KEY_THEME_COLOR, color).apply();
    }

    public String getThemeColor(){
        return sp.getString(KEY_THEME_COLOR, Colors.BLUE);
    }

    public String getFadeColor(){
        return getFadeColor(getThemeColor());
    }

    public void setTerminalTextColor(String color){
        sp.edit().putString(KEY_TERMINAL_TEXT_COLOR, color).apply();
    }

    public String getTerminalTextColor() {
        return sp.getString(KEY_TERMINAL_TEXT_COLOR, Colors.TERMINAL_GREEN);
    }

    public void set_terminal_BG_color(String color){
        sp.edit().putString(KEY_TERMINAL_BG_COLOR, color).apply();
    }

    public void setTerminalTextSize(int size){
        sp.edit().putInt(KEY_TERMINAL_TEXT_SIZE, size).apply();
    }

    public int getTerminalTextSize(){
        return sp.getInt(KEY_TERMINAL_TEXT_SIZE, TERMINAL_TEXT_SMALLEST);
    }

    public static String getFadeColor(String selectColor){
        String color;

        switch (selectColor){
            case Colors.GREEN:
                color = "#57863A";
                break;
            case Colors.ORANGE:
                color = "#6b2c07";
                break;
            case Colors.PURPLE:
                color = "#371F3C";
                break;
            case Colors.BLUE:
                color = "#264B76";
                break;
            case Colors.RAD :
                color = "#470000";
                break;
            case Colors.BARNEY:
                color = "#340013";
                break;
            case Colors.YELLOW:
                color = "#9C7A3A";
                break;
            default:
                color = "#ffffff";
        }

        return color;
    }

    /*
    *
    * 2nd circle split count setting
    * */
    public void set2ndLayerSplit(int count){
        if(count < 2){
            return;
        }
        sp.edit().putInt(KEY_2_LAYER_COUNT, count).apply();
    }

    public int get2ndLayerSplit(){
        return sp.getInt(KEY_2_LAYER_COUNT, CIRCLE_SPLIT_MINI);
    }


}
