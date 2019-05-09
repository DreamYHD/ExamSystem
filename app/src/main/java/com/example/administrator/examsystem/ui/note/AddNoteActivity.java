package com.example.administrator.examsystem.ui.note;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.utils.FileUtils;
import com.example.administrator.examsystem.utils.Glide4Engine;
import com.example.administrator.examsystem.utils.TableUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddNoteActivity extends BaseActivity {

    private static final String TAG = "AddNoteActivity";
    private static final int REQUEST_CODE_CHOOSE = 23;
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
    private Uri noteUrl;

    @OnClick(R.id.back_btn_note)
    public void onBackBtnNoteClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.post_btn_note)
    public void onPostBtnNoteClicked() {
        Log.i(TAG, "onPostBtnNoteClicked: ");
        if (avUserFinal!=null){

            Log.i(TAG, "onPostBtnNoteClicked: post");
            final String noteTag = noteTagEdit.getText().toString();
            final String noteName = noteShortEdit.getText().toString();
            final String noteContent = noteLongEdit.getText().toString();
            String path = FileUtils.getFilePathFromUri(this,noteUrl);
            Log.i(TAG, "onPostBtnNoteClicked: "+path);
            try {
                final AVFile file = AVFile.withAbsoluteLocalPath(FileUtils.getFileName(path),path);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException mE) {
                        if (mE == null) {
                            AVObject avObject = new AVObject(TableUtil.NOTE_TABLE_NAME);
                            avObject.put(TableUtil.NOTE_NAME,noteName);
                            avObject.put(TableUtil.NOTE_TITLE,noteTag);
                            avObject.put(TableUtil.NOTE_CONTENT,noteContent);
                            avObject.put(TableUtil.NOTE_USER,avUserFinal);
                            avObject.put(TableUtil.NOTE_IMG,file.getUrl());
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null){
                                        toast("添加成功",1);
                                        setResult(1000);
                                        mActivity.finish();
                                    }
                                }
                            });
                            Log.i(TAG, "done: "+file.getUrl());
                        } else {
                            Log.e(TAG, "done: " + mE.getMessage());
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            toast("请先登录",1);
            this.finish();
        }
    }

    @OnClick(R.id.add_img_note)
    public void onAddImgNoteClicked() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .maxSelectable(1)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            Log.i(TAG, "onActivityResult: "+result.get(0).toString());
            noteUrl = result.get(0);
            Glide.with(mActivity)
                    .load(noteUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(addImgNote);
        }
    }
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_note;
    }
}
