package com.wallpaper.tim.phoneinsidewallpaper.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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


public class SetFragment extends Fragment {

    private Setting setting;

    private SeekBar terminal_text_size_seek;
    private TextView terminal_text_size_value;

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

        root.findViewById(R.id.back).setOnClickListener(backInterface);
        return root;
    }


    private View.OnClickListener backInterface;

    public void setBackInterface(View.OnClickListener clickBack) {
        this.backInterface = clickBack;
    }

}
