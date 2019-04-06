package com.example.administrator.examsystem.ui.note;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AddNoteActivity extends BaseActivity {

    @BindView(R.id.back_btn_note)
    ImageView backBtnNote;
    @BindView(R.id.post_btn_note)
    ImageView postBtnNote;
    @BindView(R.id.note_tag_edit)
    TextView noteTagEdit;
    @BindView(R.id.note_short_edit)
    EditText noteShortEdit;
    @BindView(R.id.note_long_edit)
    EditText noteLongEdit;
    @BindView(R.id.add_img_note)
    ImageView addImgNote;

    @OnClick(R.id.back_btn_note)
    public void onBackBtnNoteClicked() {
    }

    @OnClick(R.id.post_btn_note)
    public void onPostBtnNoteClicked() {
        toast("添加成功",1);
    }

    @OnClick(R.id.add_img_note)
    public void onAddImgNoteClicked() {
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_note;
    }
}
