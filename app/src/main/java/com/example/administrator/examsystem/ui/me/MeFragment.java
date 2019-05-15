package com.example.administrator.examsystem.ui.me;


import android.annotation.SuppressLint;
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
import android.widget.Toast;

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
    @BindView(R.id.me_subject_tv)
    TextView meSubjectTv;
    @BindView(R.id.me_subject_btn)
    TextView meSubjectBtn;
    @BindView(R.id.do_question_btn)
    ImageView doQuestionBtn;
    @BindView(R.id.do_select_btn)
    ImageView doSelectBtn;
    @BindView(R.id.do_about_btn)
    ImageView doAboutBtn;

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
        if (avUser != null) {
            textView.setText(avUser.getUsername());
            schoolText.setText(avUser.get(TableUtil.USER_SCHOOL).toString());
        } else {
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
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();
    }
    @OnClick(R.id.img)
    public void onImgClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
    @OnClick(R.id.me_subject_btn)
    public void onMeSubjectBtnClicked() {

        final String[] sex = {"政治单选", "政治多选"};
        @SuppressLint("ResourceType") final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), 3);
        dialog.setTitle("选择科目");
        dialog.setSingleChoiceItems(sex, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                meSubjectTv.setText(sex[i]);
            }
        });
        dialog.create().show();

    }
    @OnClick(R.id.do_question_btn)
    public void onDoQuestionBtnClicked() {
        if (AVUser.getCurrentUser()!=null){
            Intent intent = new Intent(getContext(),RandomActivity.class);
            intent.putExtra("subject",meSubjectTv.getText());
            startActivity(intent);
        }else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.do_select_btn)
    public void onDoSelectBtnClicked() {
        if (AVUser.getCurrentUser()!=null){
            Intent intent = new Intent(getContext(),SelectActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.do_about_btn)
    public void onDoAboutBtnClicked() {
        Intent intent = new Intent(getContext(),AboutActivity.class);
        startActivity(intent);

    }
}
