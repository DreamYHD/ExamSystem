package com.example.administrator.examsystem.ui.discuss;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.base.OnClickListener;
import com.example.administrator.examsystem.ui.adapter.DiscussImageAdapter;
import com.example.administrator.examsystem.ui.adapter.DiscussItemAdapter;
import com.example.administrator.examsystem.utils.BigImageDialog;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowDiscussActivity extends BaseActivity {

    private static final String TAG = "ShowDiscussActivity";
    @BindView(R.id.discuss_get_bank_image)
    ImageView discussGetBankImage;
    @BindView(R.id.discuss_get_circler_avator_image)
    ImageView discussGetCirclerAvatorImage;


    @BindView(R.id.discuss_get_name_text)
    TextView discussGetNameTv;
    @BindView(R.id.discuss_school_tv)
    TextView discussSchoolTv;
    @BindView(R.id.discuss_time_tv)
    TextView discussTimeTv;
    @BindView(R.id.discuss_get_content)
    TextView discussContentTv;


    @BindView(R.id.discuss_get_images_recycler)
    RecyclerView showImagesRecycler;
    @BindView(R.id.discuss_get_pinnglun_recycler)
    RecyclerView showItemRecycler;

    @BindView(R.id.discuss_get_pinglun_image)
    ImageView showItemImage;
    @BindView(R.id.discuss_add_description_edit)
    EditText showAddItemEdit;
    @BindView(R.id.discuss_send_description_image)
    ImageView showSendItemImage;
    private String id;
    private List<String>imgList = new ArrayList<>();
    private List<String>itemList = new ArrayList<>();
    private DiscussImageAdapter imgAdapter;
    private DiscussItemAdapter itemAdapter;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;


    private Handler handlerImg = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initImgRecyclerView();
        }
    };
    private Handler handlerItem = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initItemRecyclerView();
        }
    };

    @OnClick(R.id.discuss_get_bank_image)
    public void onDiscussGetBankImageClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.discuss_get_pinglun_image)
    public void onItemClicked() {
        showAddItemEdit.setVisibility(View.VISIBLE);
        showSendItemImage.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.discuss_send_description_image)
    public void onSendItemClicked() {
        if (avUserFinal == null){
            toast("请先登录",0);
        }else {
            AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.DISCUSS_TABLE_NAME);
            avQuery.getInBackground(id, new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null){
                        //获取人
                        itemList = (List<String>) avObject.get(TableUtil.DISCUSS_ITEM);
                        String userId = AVUser.getCurrentUser().getUsername();
                        String content = showAddItemEdit.getText().toString();
                        if (itemList == null){
                            itemList = new ArrayList<>();
                        }
                        itemList.add(userId+"|"+content);
                        avObject.put(TableUtil.DISCUSS_ITEM,itemList);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null){
                                    handlerItem.sendEmptyMessage(0);
                                    Toast.makeText(ShowDiscussActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                    showAddItemEdit.setVisibility(View.GONE);
                                    showSendItemImage.setVisibility(View.GONE);

                                }
                            }
                        });
                    }

                }
            });
        }

    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        initDate();
    }

    private void initDate() {
        id = getIntent().getStringExtra("id");
        AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.DISCUSS_TABLE_NAME);
        avQuery.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null){
                    //获取人
                    final AVObject avUser = (AVObject) avObject.get(TableUtil.DISCUSS_USER);
                    if (avUser!=null){
                        Log.i(TAG, "onBindViewHolder:"+avUser.getObjectId());
                        AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.USER_TABLE_NAME);
                        avQuery.getInBackground(avUser.getObjectId(), new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                discussSchoolTv.setText(avObject.get(TableUtil.USER_SCHOOL).toString());
                                discussGetNameTv.setText(avObject .get(TableUtil.USER_NAME).toString());
                            }
                        });
                    }else {
                        Log.e(TAG, "onBindViewHolder: " +e.getMessage());
                    }
                    discussContentTv.setText(avObject.getString(TableUtil.DISCUSS_CONTENT));
                    discussTimeTv.setText(avObject.getString(TableUtil.DISCUSS_TIME));
                    imgList = (List<String>) avObject.get(TableUtil.DISCUSS_IMG);
                    if (imgList != null && imgList.size() != 0){

                        handlerImg.sendEmptyMessage(0);
                    }
                    itemList = (List<String>) avObject.get(TableUtil.DISCUSS_ITEM);
                    if (itemList != null && itemList.size() != 0){

                        handlerItem.sendEmptyMessage(0);
                    }

                }

            }
        });

    }
    public void initImgRecyclerView(){
        mGridLayoutManager = new GridLayoutManager(mActivity, 4);
        showImagesRecycler.setLayoutManager(mGridLayoutManager);
        imgAdapter = new DiscussImageAdapter(this, imgList);
        imgAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(View view, int position) {
                BigImageDialog m = new BigImageDialog(mActivity, 0, imgList.get(position));
                m.show();
            }
        });
        showImagesRecycler.setAdapter(imgAdapter);
    }

    public void initItemRecyclerView(){
        linearLayoutManager = new LinearLayoutManager(mActivity);
        showItemRecycler.setLayoutManager(linearLayoutManager);
        itemAdapter = new DiscussItemAdapter(this,itemList);
        showItemRecycler.setAdapter(itemAdapter);

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_discuss;
    }
}
