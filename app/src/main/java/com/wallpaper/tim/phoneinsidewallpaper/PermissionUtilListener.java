package com.wallpaper.tim.phoneinsidewallpaper;

import androidx.annotation.StringRes;

public interface PermissionUtilListener {

    @StringRes
    int getPermissionExplainRes(String permission);

    void startFunction();
}
