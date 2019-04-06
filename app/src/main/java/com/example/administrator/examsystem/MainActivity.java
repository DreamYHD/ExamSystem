package com.example.administrator.examsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.ui.DiscussFragment;
import com.example.administrator.examsystem.ui.MeFragment;
import com.example.administrator.examsystem.ui.note.NoteFragment;
import com.example.administrator.examsystem.ui.show.ShowFragment;
import com.example.administrator.examsystem.utils.ActivityUtils;
import com.example.administrator.examsystem.utils.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.content_main)
    FrameLayout contentMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView mBottomMenu;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
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
