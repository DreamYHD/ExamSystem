package com.example.administrator.examsystem.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
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
        setResult(1000);
        mActivity.finish();
    }
    @OnClick(R.id.login)
    public void onLoginClicked() {
        String phoneNumber = editUsernameLogin.getText().toString().trim();
        String passWord = editPasswordLogin.getText().toString().trim();
        AVUser.loginByMobilePhoneNumberInBackground(phoneNumber, passWord, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null){
                    Log.i(TAG, "done: 登陆成功"+AVUser.getCurrentUser().getUsername());
                    toast("登陆成功",0);
                    setResult(1000);
                    mActivity.finish();
                }else {
                    toast("登录失败"+e.getMessage(),1);
                }
            }
        });
    }
    @OnClick(R.id.forget_login_text)
    public void onForgetLoginTextClicked() {
//        AVUser.requestPasswordResetBySmsCodeInBackground("17610226972", new RequestMobileCodeCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    Log.i(TAG, "done: mobile ");
//                    AVUser.resetPasswordBySmsCodeInBackground("123456", "password", new UpdatePasswordCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            if (e == null) {
//
//                            } else {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//                } else {
//                    Log.e(TAG, "done: "+e.getMessage() );
//                }
//            }
//        });
        Intent intent = new Intent(mActivity,ForgetActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.register_btn_text)
    public void onRegisterBtnTextClicked() {
        Intent intent = new Intent(mActivity, RegistActivity.class);
        startActivity(intent);
    }
}
