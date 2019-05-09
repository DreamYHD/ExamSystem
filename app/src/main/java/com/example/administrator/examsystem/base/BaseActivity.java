package com.example.administrator.examsystem.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2019/4/5.
 */

public abstract class BaseActivity extends SwipeBackActivity {
    protected FragmentManager mFragmentManager;
    protected Activity mActivity;
    //protected AVUser mAVUserFinal;
    protected AVUser avUserFinal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        avUserFinal = AVUser.getCurrentUser();
        mFragmentManager = getSupportFragmentManager();
        mActivity = this;
        //mAVUserFinal = AVUser.getCurrentUser();
        logicActivity(savedInstanceState);
    }

    protected abstract void logicActivity(Bundle mSavedInstanceState);

    protected abstract int getLayoutId();

    public void toast(String toast,int time){
        if (time == 0){
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void snackBar(View v, String snackBar, int time){
        if (time == 0){
            Snackbar.make(v,snackBar,Snackbar.LENGTH_SHORT)
                    .show();
        }else {
            Snackbar.make(v,snackBar,Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}
