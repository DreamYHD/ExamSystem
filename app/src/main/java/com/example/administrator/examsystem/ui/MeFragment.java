package com.example.administrator.examsystem.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.ui.login.LoginActivity;
import com.example.administrator.examsystem.utils.TableUtil;

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
        initView();
        return view;
    }

    private void initView() {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null){
            textView.setText(avUser.getUsername());
            schoolText.setText(avUser.get(TableUtil.USER_SCHOOL).toString());
        }else {
            textView.setText("请先登录");
            schoolText.setText("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.image_vactor)
    public void onImageVactorClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();

    }

    @OnClick(R.id.img)
    public void onImgClicked() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("提示：");
        builder.setMessage("是否退出登录");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AVUser.logOut();
                initView();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    @OnClick(R.id.textView)
    public void onTextViewClicked() {
    }

    @OnClick(R.id.school_text)
    public void onSchoolTextClicked() {
    }
}
