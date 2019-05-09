package com.example.administrator.examsystem;

import android.content.Intent;
import android.os.Bundle;

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
