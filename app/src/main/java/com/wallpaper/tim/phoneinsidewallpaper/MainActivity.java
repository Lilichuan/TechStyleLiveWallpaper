package com.wallpaper.tim.phoneinsidewallpaper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.output);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //finish();
        boolean havePermission = PermissionUtil.havePermission(this, Manifest.permission.BIND_WALLPAPER);
        textView.setText("Bind wallpaper permission is :"+havePermission);
        PermissionUtilListener permissionUtilListener = new PermissionUtilListener() {
            @Override
            public int getPermissionExplainRes(String permission) {
                Log.d("permissionUtilListener", "Start getPermissionExplainRes()");
                return R.string.wallpaper_permission_explain;
            }

            @Override
            public void startFunction() {
                Log.d("permissionUtilListener", "startFunction()");
            }
        };
        PermissionUtil permissionUtil = new PermissionUtil();
        Log.d("MainActivity", "Start check permission");
        permissionUtil.checkPermissionAndStart(this, new String[]{Manifest.permission.BIND_WALLPAPER}
                , 1 ,2,permissionUtilListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
