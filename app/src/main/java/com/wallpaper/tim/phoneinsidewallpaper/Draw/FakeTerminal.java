package com.wallpaper.tim.phoneinsidewallpaper.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.wallpaper.tim.phoneinsidewallpaper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tim on 2016/11/8.
 */

public class FakeTerminal {

    private Paint paint;
    private String[] texts;
    private int lastShowTextPosition = 0;
    private static final float TEXT_SIZE = 10;
    private static final float SINGLE_LINE_MARGIN = 2;
    private List<SingleLine> lines;

    public FakeTerminal(Context context,String color){
        paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(false);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(TEXT_SIZE);
        texts = context.getResources().getStringArray(R.array.fake_terminal_str);

        //TODO set ttf file
        //Typeface type = Typeface.createFromAsset(context.getAssets(),"square_sans_serif_7.ttf");
    }

    /*
    *
    * 用畫布的高度來初始化所需繪製物件與前置計算
    *
    * */
    private void initLines(float height){
        lines = new ArrayList<>();
        float lineH_unit = TEXT_SIZE + SINGLE_LINE_MARGIN;
        int count = (int)(height / lineH_unit);
        lastShowTextPosition = 0;

        for (int i = 0 ; i < count ;i++){
            SingleLine singleLine = new SingleLine();

            int po = i;
            while (po >= texts.length){
                po -= texts.length;
                if(po < 0){
                    po = 0;
                }
            }

            singleLine.setStr(texts[po]);
            lines.add(singleLine);
        }

        //Count position
        //Basic on bottom line
        int b = 1;
        for (int i = lines.size() - 1; i >= 0 ;i--){
            SingleLine line = lines.get(i);
            line.setPosition((int)SINGLE_LINE_MARGIN , (int)(height - (lineH_unit * b)));
            b++;
        }
    }

    //底部顯示新的一行，舊資料往上推。
    //為此所需的前置計算
    private void initForNewLine(){
        lastShowTextPosition++;
        if(lastShowTextPosition >= texts.length){
            lastShowTextPosition = 0;
        }
        int po = lastShowTextPosition;

        for(SingleLine line : lines){
            line.setStr(texts[po]);
            po++;
            if(po >= texts.length){
                po = 0;
            }
        }
    }

    private void draw(Canvas canvas){
        int h = canvas.getHeight();
        int w = canvas.getWidth();

    }

    private class SingleLine{

        private String str;

        //此值被動畫使用
        //已經播放到第幾個字
        private int displayPosition = 0;

        //該行有多少字元長度
        private int textLength;

        private int p_x, p_y;

        SingleLine(){

        }

        public String getStr() {
            return str;
        }

        void setStr(String str) {
            this.str = str;
            textLength = str.length();
        }

        void setPosition(int x, int y){
            p_x = x;
            p_y = y;
        }

        public int getX() {
            return p_x;
        }

        public int getY() {
            return p_y;
        }
    }


}
