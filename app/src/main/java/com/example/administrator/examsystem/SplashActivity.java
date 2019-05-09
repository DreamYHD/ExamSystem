package com.example.administrator.examsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.administrator.examsystem.base.BaseActivity;

public class SplashActivity extends BaseActivity {


    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);
                    mActivity.overridePendingTransition(0, 0);
                    mActivity.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
            Log.e("permission", "动态申请");
            //参数分别是当前活动，权限字符串数组，requestcode
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
