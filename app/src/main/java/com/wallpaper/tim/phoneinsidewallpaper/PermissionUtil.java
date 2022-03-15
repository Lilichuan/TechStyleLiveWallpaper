package com.wallpaper.tim.phoneinsidewallpaper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {

    /**
     *
     * @param mActivity
     * @param permissionList
     * list of Manifest.permission.
     *
     * @param requestPermissionCode
     *
     * @param requestActivityCode
     * @param listener
     */
    public void checkPermissionAndStart(Activity mActivity,String[] permissionList,
                                        int requestPermissionCode,
                                        int requestActivityCode ,
                                        PermissionUtilListener listener){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            listener.startFunction();
            return;
        }

        for ( String permission : permissionList){
            if (!havePermission(mActivity, permission)) {
                Log.d("checkPermissionAndStart()", "No permission");
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                        permission)){

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    int explainText = listener.getPermissionExplainRes(permission);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    MyAskDialogListener dialogListener = new MyAskDialogListener(mActivity, permissionList,requestPermissionCode);
                    builder.setMessage(explainText)
                            .setNeutralButton(R.string.got_it, dialogListener).show();
                } else {
                    // No explanation needed, we can request the permission.
                    Log.d("checkPermissionAndStart()","No need explain");
                    ActivityCompat.requestPermissions(mActivity,
                            permissionList,
                            requestActivityCode);
                }
                return;
            }
        }

        listener.startFunction();

    }


    public static void checkHavePermission(Activity context ,String[] permission, int requestCode){
        for (String s : permission){
            if(!havePermission(context, s)){
                ActivityCompat.requestPermissions(context,
                        permission,
                        requestCode);
                return ;
            }
        }

        return;
    }

    /*
     *
     * 判斷是否有權限
     *
     * @param permission
     * 以Manifest.permission帶入參數
     *
     * @return boolean
     * 是否有權限
     * */
    public static boolean havePermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 是否顯示提醒訊息
     * @return
     */
    public static boolean needToShowRationale(Activity activity, String[] permissions){
        for (String permissionName : permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)){
                return true;
            }
        }

        return false;
    }

    /**
     * Start show dialog
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void queryPermissionByDialog(@NonNull Activity activity,
                                               @NonNull String[] permissions, int requestCode){
        ActivityCompat.requestPermissions(activity,
                permissions,
                requestCode);
    }

    public void showExplainDialog(@NonNull Context context, @NonNull Activity activity,
                                  @NonNull String[] permissions, int requestCode, @StringRes int permissionExplainRes){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        MyAskDialogListener listener = new MyAskDialogListener(activity, permissions,requestCode);
        builder.setMessage(permissionExplainRes)
                .setNeutralButton(R.string.got_it, listener).show();
    }

    private class MyAskDialogListener implements DialogInterface.OnClickListener{

        private Activity activity;
        String[] permissions;
        int code;

        public MyAskDialogListener(@NonNull Activity activity,
                                   @NonNull String[] permissions, int requestCode){
            this.activity = activity;
            this.permissions = permissions;
            code = requestCode;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            queryPermissionByDialog(activity, permissions, code);
        }
    }
}
