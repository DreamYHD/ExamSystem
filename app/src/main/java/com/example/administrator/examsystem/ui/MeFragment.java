package com.example.administrator.examsystem.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    @BindView(R.id.image_vactor)
    ImageView imageVactor;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.school_text)
    TextView schoolText;
    Unbinder unbinder;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment getInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.image_vactor)
    public void onImageVactorClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.img)
    public void onImgClicked() {
    }

    @OnClick(R.id.textView)
    public void onTextViewClicked() {
    }

    @OnClick(R.id.school_text)
    public void onSchoolTextClicked() {
    }
}
