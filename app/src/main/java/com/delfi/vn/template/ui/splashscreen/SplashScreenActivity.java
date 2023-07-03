package com.delfi.vn.template.ui.splashscreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.delfi.core.base.BaseSplashScreenActivity;
import com.delfi.vn.template.ui.login.LoginActivity;

public class SplashScreenActivity extends BaseSplashScreenActivity {
    private String[] permissionList30 = new String[]{
            "android.permission.BLUETOOTH_SCAN",
            "android.permission.BLUETOOTH_CONNECT",
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE};
    private String[] permissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE};
    private int[] grantResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            requestPermissionsSafely(permissionList30, super.REQUEST_ACTIVATION_CODE);
        else
            requestPermissionsSafely(permissionList, super.REQUEST_ACTIVATION_CODE);
    }


    @Override
    public boolean hasPermission(String[] permissions) {
        return super.hasPermission(permissions);
    }

    @Override
    public void onStartNextActivity() {
        Intent intent = LoginActivity.initActivity(this);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allow = true;
        for (int i : grantResults)
            if (i != PackageManager.PERMISSION_GRANTED)
                allow = false;
        if (allow)
            onRequireAppIDActivate();
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 500);
            } else {
                onRequireAppIDActivate();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            if (requestCode == 500) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager())
                        if (isOnlineActivation())
                            onRequireAppIDActivate();
                        else
                            onLicenseNotActivated();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Supports online/offline activation
     * Online mode is used AppID as activation key
     * Offline mode is used activation key by generating from device serialnumber
     *
     * @return
     */
    @Override
    public boolean isOnlineActivation() {
        return true;
    }
}