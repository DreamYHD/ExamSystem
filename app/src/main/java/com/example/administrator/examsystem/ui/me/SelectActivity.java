package com.example.administrator.examsystem.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.ui.adapter.SelectAdapter;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectActivity extends BaseActivity {

    private static final String TAG = "SelectActivity";
    @BindView(R.id.select_recyclerView)
    RecyclerView selectRecyclerView;
    private SelectAdapter selectAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String>list = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initView();
        }
    };
    private void initView() {
        selectAdapter = new SelectAdapter(list,mActivity);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        selectRecyclerView.setAdapter(selectAdapter);
        selectRecyclerView.setLayoutManager(linearLayoutManager);
    }
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select;
    }

    public void getData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.USER_TABLE_NAME);
        avQuery.getInBackground(avUserFinal.getObjectId(), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
              list = avObject.getList(TableUtil.USER_QUESTION);
              handler.sendEmptyMessage(0);
            }
        });
    }

}
