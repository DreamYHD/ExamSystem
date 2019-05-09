package com.example.administrator.examsystem.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.utils.TableUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    private static final String TAG = "RegistActivity";
    @BindView(R.id.back_register_image)
    ImageView backRegisterImage;
    @BindView(R.id.username_register)
    EditText usernameRegister;
    @BindView(R.id.hint1)
    TextView hint1;
    @BindView(R.id.phoneNumber)
    EditText passwordRegister;
    @BindView(R.id.hint2)
    TextView hint2;
    @BindView(R.id.sms_code)
    EditText schoolRegister;
    @BindView(R.id.hint3)
    TextView hint3;
    @BindView(R.id.newPassEdit)
    EditText phoneRegister;
    @BindView(R.id.sendBtn)
    Button registerBtn;

    @OnClick(R.id.back_register_image)
    public void onBackRegisterImageClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.sendBtn)
    public void onRegisterBtnClicked() {
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        String userName = usernameRegister.getText().toString();
        String passWord = passwordRegister.getText().toString();
        String phoneNumber = phoneRegister.getText().toString();
        String schoolName = schoolRegister.getText().toString();
        Log.i(TAG, "onRegisterBtnClicked: " + userName);
        user.setUsername(userName);// 设置用户名
        user.setPassword(passWord);// 设置密码
        user.setMobilePhoneNumber(phoneNumber);
        user.put(TableUtil.USER_SCHOOL,schoolName);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    Log.i(TAG, "done: 注册成功");
                    toast("注册成功",0);
                    mActivity.finish();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    Log.e(TAG, "done: 注册失败"+e.getMessage());
                    toast("注册失败"+e.getMessage(), 0);
                }
            }
        });
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }
}
