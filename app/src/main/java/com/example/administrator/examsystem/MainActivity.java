package com.example.administrator.examsystem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.ui.MeFragment;
import com.example.administrator.examsystem.ui.discuss.DiscussFragment;
import com.example.administrator.examsystem.ui.note.NoteFragment;
import com.example.administrator.examsystem.ui.plan.ShowFragment;
import com.example.administrator.examsystem.utils.ActivityUtils;
import com.example.administrator.examsystem.utils.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";
    @BindView(R.id.content_main)
    FrameLayout contentMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView mBottomMenu;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        if (avUserFinal == null){
            toast("请先登录",0);
        }
        int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
            Log.e("permission", "动态申请");
            //参数分别是当前活动，权限字符串数组，requestcode
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    //    AVFile file = new AVFile("resume.txt","Working with LeanCloud is great!".getBytes());

        if (mSavedInstanceState == null) {
            ActivityUtils.replaceFragmentToActivity(mFragmentManager, ShowFragment.getInstance(), R.id.content_main);
        }
        BottomNavigationViewHelper.disableShiftMode(mBottomMenu);
        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.show_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, ShowFragment.getInstance(), R.id.content_main);
                        break;
                    case R.id.note_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, NoteFragment.getInstance(), R.id.content_main);
                        break;
                    case R.id.discuss_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, DiscussFragment.getInstance(), R.id.content_main);
                        break;
                    case R.id.me_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, MeFragment.getInstance(), R.id.content_main);
                        break;
                }

                return true;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
