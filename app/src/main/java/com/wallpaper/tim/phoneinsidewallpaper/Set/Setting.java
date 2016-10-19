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
    private static final String KEY_DISPLAY_2_LAYER = "secondLayer";
    private static final String KEY_2_LAYER_COUNT = "secondLayerCount";
    private static final String KEY_COLOR = "color";
    private static final String KEY_ERROR = "error";
    private static final String KEY_MAIN_SHADOW = "mainShadow";
    private static final String KEY_SHOW_BATTERY = "showBattery";

    public static final int CIRCLE_SPLIT_MAX = 180;
    public static final int CIRCLE_SPLIT_MINI = 3;

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


    /*
    *
    * Display clock setting
    * */
    public void setShowClock(boolean show){
        sp.edit().putBoolean(KEY_SHOW_CLOCK, show).apply();
    }

    public boolean isShowClock(){
        return sp.getBoolean(KEY_SHOW_CLOCK, true);
    }


    /*
    *
    * Display two circles setting
    * */
    public void setShow2Layer(boolean show){
        sp.edit().putBoolean(KEY_DISPLAY_2_LAYER, show).apply();
    }

    public boolean getShow2Layer(){
        return sp.getBoolean(KEY_DISPLAY_2_LAYER, false);
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

    public String getFadeColor(){
        return getFadeColor(getColor());
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
    * */
    public void setShowBattery(boolean showBattery){
        sp.edit().putBoolean(KEY_SHOW_BATTERY, showBattery).apply();
    }

    public boolean isShowBattery(){
        return sp.getBoolean(KEY_SHOW_BATTERY, false);
    }

}
