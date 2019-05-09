package com.example.administrator.examsystem;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2019/5/5.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"LqWUn0vCK3Y38UuSBkHaARvX-gzGzoHsz","U4cx6LRM9v7oMumkf9evi1Xp");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
