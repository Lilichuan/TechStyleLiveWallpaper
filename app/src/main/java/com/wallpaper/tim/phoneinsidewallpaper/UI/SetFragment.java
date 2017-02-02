package com.wallpaper.tim.phoneinsidewallpaper.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.wallpaper.tim.phoneinsidewallpaper.R;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Colors;
import com.wallpaper.tim.phoneinsidewallpaper.Set.Setting;

import java.util.ArrayList;
import java.util.List;


public class SetFragment extends Fragment {

    private Setting setting;

    private SeekBar terminal_text_size_seek;
    private TextView terminal_text_size_value;
    private ArrayAdapter circleColorAdapter;
    private Spinner circleColorSpinner;
    private List<ColorItem> colorList;

    public SetFragment() {
        // Required empty public constructor
    }


    public static SetFragment newInstance() {
        SetFragment fragment = new SetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set, container, false);

        setting = new Setting(getContext());

        terminal_text_size_seek = (SeekBar)root.findViewById(R.id.terminal_text_seek_bar);
        terminal_text_size_seek.setMax(Setting.TERMINAL_TEXT_BIGGEST);
        terminal_text_size_seek.setProgress(setting.getTerminalTextSize());
        terminal_text_size_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if(fromUser){
                    if(i < Setting.TERMINAL_TEXT_SMALLEST){
                        i = Setting.TERMINAL_TEXT_SMALLEST;
                        seekBar.setProgress(Setting.TERMINAL_TEXT_SMALLEST);
                    }
                    terminal_text_size_value.setText("" + i);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setting.setTerminalTextSize(seekBar.getProgress());
            }
        });

        terminal_text_size_value = (TextView)root.findViewById(R.id.terminal_text_size_value);
        terminal_text_size_value.setText(""+ setting.getTerminalTextSize());
        initSecondCircleSettingView(root);

        circleColorSpinner = (Spinner)root.findViewById(R.id.circle_color_spinner);
        initCircleColorList(getContext());


        root.findViewById(R.id.back).setOnClickListener(backInterface);
        return root;
    }


    private View.OnClickListener backInterface;

    public void setBackInterface(View.OnClickListener clickBack) {
        this.backInterface = clickBack;
    }

    private TextView splitCountText;

    private void initSecondCircleSettingView(View root){
        splitCountText = (TextView)root.findViewById(R.id.splitCountText);
        SeekBar circleSplitSeekBar = (SeekBar)root.findViewById(R.id.splitCountSeekBar);
        circleSplitSeekBar.setMax(Setting.CIRCLE_SPLIT_MAX);
        circleSplitSeekBar.setProgress(setting.get2ndLayerSplit());
        circleSplitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < Setting.CIRCLE_SPLIT_MINI && fromUser){
                    progress = Setting.CIRCLE_SPLIT_MINI;
                }
                setSplitText(progress);
                setting.set2ndLayerSplit(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setSplitText(setting.get2ndLayerSplit());

    }

    private void setSplitText(int count){
        splitCountText.setText(getString(R.string.secondLayerSplitCount, count));
    }

    private List<ColorItem> circleColorItemList = new ArrayList<>();

    private void initCircleColorList(Context context){

        colorList = new ArrayList<>();

        colorList.add(new ColorItem(Colors.ORANGE, getStr(R.string.color_orange)));
        colorList.add(new ColorItem(Colors.BLUE , getStr(R.string.color_blue)));
        colorList.add(new ColorItem(Colors.GREEN , getStr(R.string.color_green)));
        colorList.add(new ColorItem(Colors.PURPLE , getStr(R.string.color_purple)));
        colorList.add(new ColorItem(Colors.RAD , getStr(R.string.color_rad)));
        colorList.add(new ColorItem(Colors.BLUE , getStr(R.string.color_blue)));

        List<String> strList = new ArrayList<>();
        for (ColorItem item : colorList){
            strList.add(item.getStr());
        }

        circleColorAdapter = new ArrayAdapter(getContext(), R.layout.selection_textview, strList);
        circleColorSpinner.setAdapter(circleColorAdapter);
        circleColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setting.setCircleColor(colorList.get(i).getColorCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String nowColorCode = setting.getCircleColor();

        for (int i = 0; i < colorList.size() ;i++){
            if(nowColorCode.contentEquals(colorList.get(i).colorCode)){
                circleColorSpinner.setSelection(i);
            }
        }
    }

    private class ColorItem{

        public String colorCode;
        public String str;

        public ColorItem(String code, String str){
            colorCode = code;
            this.str = str;
        }

        public String getStr() {
            return str;
        }

        public String getColorCode() {
            return colorCode;
        }
    }

//    private class MAdapter extends ArrayAdapter<ColorItem>{
//
//        private LayoutInflater inflater;
//
//        public MAdapter(Context context){
//            super(context, R.layout.selection_textview);
//            inflater = LayoutInflater.from(context);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if(convertView == null){
//                convertView = inflater.inflate(R.layout.selection_textview, null);
//            }
//
//            ColorItem item = getItem(position);
//            TextView textView = (TextView)convertView;
//            textView.setText(item.getStr());
//            textView.setTag(item);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ColorItem item1 = (ColorItem)view.getTag();
//                    setting.setCircleColor(item1.colorCode);
//                }
//            });
//            return convertView;
//        }
//    }

    private String getStr(int id){
        return getContext().getString(id);
    }

}
