package com.example.administrator.examsystem.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.ui.adapter.DiscussAdapter;

import java.util.ArrayList;

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
        unbinder = ButterKnife.bind(this, view);
        discussAdapter = new DiscussAdapter(getContext(),new ArrayList<String>());
        discussRecyclerview.setAdapter(discussAdapter);
        discussRecyclerview.setLayoutManager(linearLayoutManager);
        xuanshangProgressbar.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.xuanshang_add_floatingActionButton)
    public void onViewClicked() {
    }
}
