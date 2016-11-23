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

    public static final int ANIMATION_TIME = 4000;

    public Setting(Context context){
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void setColor(String color){
        if(TextUtils.isEmpty(color)){
            return;
        }

        sp.edit().putString(KEY_THEME_COLOR, color).apply();
    }

    public String getColor(){
        return sp.getString(KEY_THEME_COLOR, Colors.BLUE);
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


}
