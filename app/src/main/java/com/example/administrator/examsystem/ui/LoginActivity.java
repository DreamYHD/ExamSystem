package com.example.administrator.examsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.back_login_image)
    ImageView backLoginImage;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.edit_username_login)
    EditText editUsernameLogin;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.edit_password_login)
    EditText editPasswordLogin;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forget_login_text)
    TextView forgetLoginText;
    @BindView(R.id.register_btn_text)
    TextView registerBtnText;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.back_login_image)
    public void onBackLoginImageClicked() {
    }

    @OnClick(R.id.edit_username_login)
    public void onEditUsernameLoginClicked() {
    }

    @OnClick(R.id.edit_password_login)
    public void onEditPasswordLoginClicked() {
    }

    @OnClick(R.id.login)
    public void onLoginClicked() {
    }

    @OnClick(R.id.forget_login_text)
    public void onForgetLoginTextClicked() {
    }

    @OnClick(R.id.register_btn_text)
    public void onRegisterBtnTextClicked() {
        Intent intent = new Intent(mActivity, RegistActivity.class);
        startActivity(intent);
    }
}
