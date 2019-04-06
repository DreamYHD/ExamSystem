package com.example.administrator.examsystem.ui.note;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WrongQuestionActivity extends BaseActivity {

    @BindView(R.id.back_btn_question)
    ImageView backBtnQuestion;
    @BindView(R.id.tag_question)
    TextView tagQuestion;
    @BindView(R.id.delete_btn_question)
    ImageView deleteBtnQuestion;
    @BindView(R.id.descriptionShort_question)
    TextView descriptionShortQuestion;
    @BindView(R.id.descriptionLong_question)
    TextView descriptionLongQuestion;
    @BindView(R.id.img_question)
    ImageView imgQuestion;


    @OnClick(R.id.back_btn_question)
    public void onBackBtnQuestionClicked() {
        finish();
    }

    @OnClick(R.id.delete_btn_question)
    public void onDeleteBtnQuestionClicked() {
        toast("已经删除",1);
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wrong_question;
    }
}
