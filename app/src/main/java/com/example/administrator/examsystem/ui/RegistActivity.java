package com.example.administrator.examsystem.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @BindView(R.id.back_register_image)
    ImageView backRegisterImage;
    @BindView(R.id.username_register)
    EditText usernameRegister;
    @BindView(R.id.hint1)
    TextView hint1;
    @BindView(R.id.password_register)
    EditText passwordRegister;
    @BindView(R.id.hint2)
    TextView hint2;
    @BindView(R.id.school_register)
    EditText schoolRegister;
    @BindView(R.id.hint3)
    TextView hint3;
    @BindView(R.id.phone_register)
    EditText phoneRegister;
    @BindView(R.id.register_btn)
    Button registerBtn;

    @OnClick(R.id.back_register_image)
    public void onBackRegisterImageClicked() {
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }
}
