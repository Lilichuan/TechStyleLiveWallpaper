package com.wallpaper.tim.phoneinsidewallpaper.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.wallpaper.tim.phoneinsidewallpaper.R;

public class SetActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SetFragment fragment = SetFragment.newInstance();
        fragment.setBackInterface(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_set_id, fragment)
                .commit();
    }
}
