package com.example.administrator.examsystem.ui.discuss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.base.OnClickListener;
import com.example.administrator.examsystem.ui.adapter.DiscussImageAdapter;
import com.example.administrator.examsystem.utils.BigImageDialog;
import com.example.administrator.examsystem.utils.FileUtils;
import com.example.administrator.examsystem.utils.Glide4Engine;
import com.example.administrator.examsystem.utils.TableUtil;
import com.example.administrator.examsystem.utils.TimeUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddDiscussActivity extends BaseActivity {

    @BindView(R.id.discuss_back_img)
    ImageView discussBackImg;
    @BindView(R.id.discuss_send_btn)
    ImageView discussSendBtn;
    @BindView(R.id.discuss_edit_show)
    EditText discussEditShow;
    @BindView(R.id.discuss_send_tags)
    EditText discussSendTags;
    @BindView(R.id.discuss_image_recycler)
    RecyclerView discussImageRecycler;
    @BindView(R.id.discuss_send_add_image)
    ImageView discussSendAddImage;
    @BindView(R.id.discuss_send_progressbar)
    ProgressBar discussSendProgressbar;

    private static final String TAG = "AddDiscussActivity";
    private DiscussImageAdapter discussImageAdapter;
    private GridLayoutManager mGridLayoutManager;
    public static final int SELECT_CODE = 100;
    private static List<String> mStringList = new ArrayList<>();//图片的url
    private static List<String> mFileList = new ArrayList<>();//存到图片路径
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initRecyclerView();
        }
    };

    private void initRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(this, 4);
        discussImageRecycler.setLayoutManager(mGridLayoutManager);
        discussImageAdapter = new DiscussImageAdapter(this, mFileList);
        discussImageAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(View view, int position) {
                BigImageDialog m = new BigImageDialog(mActivity, 0, mFileList.get(position));
                m.show();
            }
        });
        discussImageRecycler.setAdapter(discussImageAdapter);
    }


    @OnClick(R.id.discuss_back_img)
    public void onDiscussBackImgClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.discuss_send_btn)
    public void onDiscussSendBtnClicked() {
        discussSendProgressbar.setVisibility(View.VISIBLE);
        if (avUserFinal != null) {
            for (int i = 0; i < mFileList.size(); i++) {
                final AVFile file;
                String path = FileUtils.getFilePathFromUri(this, Uri.parse(mFileList.get(i)));
                try {
                    file = AVFile.withAbsoluteLocalPath(FileUtils.getFileName(path), path);
                    final int finalI = i;
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                mStringList.add(file.getUrl());
                                if (finalI == (mFileList.size() - 1)) {
                                    AVObject avObject = new AVObject(TableUtil.DISCUSS_TABLE_NAME);
                                    avObject.put(TableUtil.DISCUSS_CONTENT, discussEditShow.getText().toString());
                                    avObject.put(TableUtil.DISCUSS_TIME, TimeUtil.getDate());
                                    avObject.put(TableUtil.DISCUSS_TAG, discussSendTags.getText().toString());
                                    avObject.put(TableUtil.DISCUSS_USER, avUserFinal);
                                    avObject.put(TableUtil.DISCUSS_IMG, mStringList);
                                    avObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                setResult(1000);
                                                mActivity.finish();
                                                Log.i(TAG, "done: 发送成功");
                                            } else {
                                                Log.e(TAG, "done: " + e.getMessage());
                                            }
                                        }
                                    });
                                }
                                Log.i(TAG, "done: " + file.getUrl());
                            } else {
                                Log.e(TAG, "done: " + e.getMessage());
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            toast("请先登录", 0);
            Log.i(TAG, "onDiscussSendBtnClicked: ");
        }
    }

    @OnClick(R.id.discuss_send_add_image)
    public void onDiscussSendAddImageClicked() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .maxSelectable(8)
                .imageEngine(new Glide4Engine())
                .forResult(SELECT_CODE);
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){

            List<Uri> mList = Matisse.obtainResult(data);
            mFileList.clear();
            for (Uri m :
                    mList) {
                mFileList.add(m.toString());
                Log.i(TAG, "onActivityResult: " + m.toString());

            }
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_discuss;
    }
}
