package com.example.administrator.examsystem.ui.note;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.utils.BigImageDialog;
import com.example.administrator.examsystem.utils.TableUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class WrongQuestionActivity extends BaseActivity {

    private static final String TAG = "WrongQuestionActivity";
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

    private String imgUrl;
    private String id;


    @OnClick(R.id.back_btn_question)
    public void onBackBtnQuestionClicked() {
        finish();
    }

    @OnClick(R.id.delete_btn_question)
    public void onDeleteBtnQuestionClicked() {

        // 执行 CQL 语句实现删除一个 Todo 对象
        AVQuery.doCloudQueryInBackground("delete from Note where objectId="+"'"+id+"'", new CloudQueryCallback<AVCloudQueryResult>() {

            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null){
                    setResult(1000);
                    mActivity.finish();
                    toast("已经删除", 1);
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        id = getIntent().getStringExtra("id");
        final AVObject avObject = AVObject.createWithoutData(TableUtil.NOTE_TABLE_NAME, id);
        avObject.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                Log.i(TAG, "logicActivity: " + avObject.getObjectId());
                tagQuestion.setText(avObject.get(TableUtil.NOTE_TITLE).toString());
                descriptionShortQuestion.setText(avObject.get(TableUtil.NOTE_NAME).toString());
                descriptionLongQuestion.setText(avObject.get(TableUtil.NOTE_CONTENT).toString());
                imgUrl = (String) avObject.get(TableUtil.NOTE_IMG);
                Glide.with(mActivity)
                        .load(avObject.get(TableUtil.NOTE_IMG))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imgQuestion);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wrong_question;
    }

    private void showBigImage( String s) {
        BigImageDialog bigImageDialog = new BigImageDialog(this, R.style.DialogBigImage, s);
        bigImageDialog.show();
    }

    @OnClick(R.id.img_question)
    public void onViewClicked() {
        showBigImage(imgUrl);
    }
}
