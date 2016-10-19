package com.wallpaper.tim.phoneinsidewallpaper;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wallpaper.tim.phoneinsidewallpaper.UI.DisplayFragment;
import com.wallpaper.tim.phoneinsidewallpaper.UI.SetFragment;

public class MainActivity extends AppCompatActivity {

    public static final int PAGE_DISPLAY = 1;
    public static final int PAGE_SET = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    private void changePage(int page){
        switch (page){
            case PAGE_DISPLAY:
                changeFragment(DisplayFragment.newInstance());
                break;
            case PAGE_SET:
                SetFragment fragment = SetFragment.newInstance();
                fragment.setBackInterface(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                changeFragment(fragment);
                break;
        }
    }

    private void changeFragment(Fragment f){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, f)
                .commit();
    }
}
