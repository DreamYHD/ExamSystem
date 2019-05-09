package com.example.administrator.examsystem.ui.discuss;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.OnClickListener;
import com.example.administrator.examsystem.ui.adapter.DiscussAdapter;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DiscussFragment extends Fragment {

    @BindView(R.id.xuanshang_recyclerview)
    RecyclerView discussRecyclerview;
    @BindView(R.id.xuanshang_progressbar)
    ProgressBar xuanshangProgressbar;
    @BindView(R.id.xuanshang_add_floatingActionButton)
    FloatingActionButton discussAddFlsatingActionButton;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private DiscussAdapter discussAdapter;
    private List<AVObject>discussList = new ArrayList<>();

    private static final String TAG = "DiscussFragment";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initRecyclerView();
        }
    };

    private void initRecyclerView() {
        discussAdapter = new DiscussAdapter(getContext(),discussList);
        discussAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(View view, int position) {
                Intent intent = new Intent(getActivity(),ShowDiscussActivity.class);
                intent.putExtra("id",discussList.get(position).getObjectId());
                startActivity(intent);
            }
        });
        discussRecyclerview.setAdapter(discussAdapter);
        discussRecyclerview.setLayoutManager(linearLayoutManager);
    }
    public DiscussFragment() {
        // Required empty public constructor
    }

    public static DiscussFragment getInstance() {
        return new DiscussFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discuss, container, false);
        initData();
        unbinder = ButterKnife.bind(this, view);

        xuanshangProgressbar.setVisibility(View.GONE);
        return view;
    }

    private void initData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DISCUSS_TABLE_NAME);
        //query.whereEqualTo(TableUtil.NOTE_USER, AVUser.getCurrentUser());
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    discussList = list;
                    Log.i(TAG, "done: "+ list.size());
                    handler.sendEmptyMessage(0);
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.xuanshang_add_floatingActionButton)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(),AddDiscussActivity.class);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();

    }
}
