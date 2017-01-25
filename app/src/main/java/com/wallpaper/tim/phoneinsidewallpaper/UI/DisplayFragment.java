package com.wallpaper.tim.phoneinsidewallpaper.UI;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallpaper.tim.phoneinsidewallpaper.R;

import java.util.Timer;
import java.util.TimerTask;


public class DisplayFragment extends Fragment {

    private Timer timer;
    private static MWallPaperView wallPaperView;


    public DisplayFragment() {
        // Required empty public constructor
    }


    public static DisplayFragment newInstance() {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display, container, false);
        wallPaperView = (MWallPaperView)root.findViewById(R.id.my_wallpaper);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        wallPaperView.setPause(false);

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }
        },200,1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        wallPaperView.setPause(true);
        if(timer != null){
            timer.purge();
            timer.cancel();
            timer = null;
        }
    }

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            wallPaperView.invalidate();
            super.handleMessage(msg);
        }
    };
}
