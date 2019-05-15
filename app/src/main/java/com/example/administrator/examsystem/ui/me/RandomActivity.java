package com.example.administrator.examsystem.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.utils.BottomNavigationViewHelper;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class RandomActivity extends BaseActivity {

    private static final String TAG = "RandomActivity";
    @BindView(R.id.back_answer_btn)
    ImageView backAnswerBtn;
    @BindView(R.id.index_answer_tv)
    TextView indexAnswerTv;
    @BindView(R.id.answer_type_tv)
    TextView answerTypeTv;
    @BindView(R.id.answer_title_tv)
    TextView answerTitleTv;
    @BindView(R.id.answer_radio_index1)
    CheckBox answerRadioIndex1;
    @BindView(R.id.answer_radio_index2)
    CheckBox answerRadioIndex2;
    @BindView(R.id.answer_radio_index3)
    CheckBox answerRadioIndex3;
    @BindView(R.id.answer_radio_index4)
    CheckBox answerRadioIndex4;
    @BindView(R.id.answer_radio_group)
    RadioGroup answerRadioGroup;
    @BindView(R.id.answer_cankaodaan_title)
    TextView answerCankaodaanTitle;
    @BindView(R.id.answer_answer_tv)
    TextView answerAnswerTv;
    @BindView(R.id.content_main)
    LinearLayout contentMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;
    private List<AVObject>avObjectList = new ArrayList<>();
    private final Set<String> strings = new HashSet<>();
    private int index = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initView();
        }
    };
    private void initView() {
        answerRadioIndex1.setChecked(false);
        answerRadioIndex2.setChecked(false);
        answerRadioIndex3.setChecked(false);
        answerRadioIndex4.setChecked(false);
        indexAnswerTv.setText((index+1)+"/10");
        final AVObject avObject = avObjectList.get(index);
        answerTitleTv.setText(avObject.get(TableUtil.QUESTION_TITLE).toString());
        //answerAnswerTv.setText(avObject.get(TableUtil.QUESTION_TEACH).toString());
        List<String>list = (List<String>) avObject.get(TableUtil.QUESTION_ANSWER);
        answerRadioIndex1.setText(list.get(0));
        answerRadioIndex2.setText(list.get(1));
        answerRadioIndex3.setText(list.get(2));
        answerRadioIndex4.setText(list.get(3));

        answerRadioIndex1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strings.add(answerRadioIndex1.getText().toString());
                }else {
                    if (strings.contains(answerRadioIndex1.getText().toString())){
                        strings.remove(answerRadioIndex1.getText().toString());
                    }
                }
            }
        });
        answerRadioIndex2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strings.add(answerRadioIndex2.getText().toString());
                }else {
                    if (strings.contains(answerRadioIndex2.getText().toString())){
                        strings.remove(answerRadioIndex2.getText().toString());
                    }
                }
            }
        });
        answerRadioIndex3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strings.add(answerRadioIndex3.getText().toString());
                }else {
                    if (strings.contains(answerRadioIndex3.getText().toString())){
                        strings.remove(answerRadioIndex3.getText().toString());
                    }
                }
            }
        });
        answerRadioIndex4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strings.add(answerRadioIndex4.getText().toString());
                }else {
                    if (strings.contains(answerRadioIndex4.getText().toString())){
                        strings.remove(answerRadioIndex4.getText().toString());
                    }
                }
            }
        });
        //科目 0：政治 1：英语
        // 00 政治单选  01政治多选     10英语1单选  11英语1阅读      12英语2单选   13英语2阅读
        switch (avObject.get(TableUtil.QUESTION_TYPE).toString()){
            case "00":answerTypeTv.setText("政治单选");
                break;
            case "01":answerTypeTv.setText("政治多选");
                break;
            default:
                break;
        }
    }
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        answerAnswerTv.setVisibility(View.GONE);
        answerCankaodaanTitle.setVisibility(View.GONE);
        getData();
        BottomNavigationViewHelper.disableShiftMode(bottomMenu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pre_question_item:
                        index = index -1;
                        if (index > -1){
                            initView();
                            cancelAnswer();
                        }else {
                            index = 0;
                            toast("已经是第一个了",0);
                        }
                        break;
                    case R.id.answer_item:
                        showAnswer();
                        break;
                    case R.id.select_item:
                        addToSelect();
                        break;
                    case R.id.next_question_item:
                        index = index + 1;
                        if (index < 10){
                            initView();
                            cancelAnswer();
                        }else {
                            index = 9;
                            toast("已经是最后一个了",0);
                        }
                        break;
                }
                return true;
            }
        });
    }
    private void addToSelect() {
        AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.USER_TABLE_NAME);
        if (getIntent().getStringExtra("subject").equals("政治单选")){
            avQuery.whereEqualTo(TableUtil.QUESTION_TYPE,"00");
        }else {
            avQuery.whereEqualTo(TableUtil.QUESTION_TYPE,"01");
        }
        avQuery.getInBackground(avUserFinal.getObjectId(), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                List<String>list = avObject.getList(TableUtil.USER_QUESTION);
                if (list == null){
                    list = new ArrayList<>();
                }
                if (list.contains(avObjectList.get(index).getObjectId())){
                    toast("您已经收藏过了",0);
                }else {
                    list.add(avObjectList.get(index).getObjectId());
                    avObject.put(TableUtil.USER_QUESTION,list);
                    avObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                toast("收藏成功",1);
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_random;
    }
    @OnClick(R.id.back_answer_btn)
    public void onViewClicked() {
        mActivity.finish();
    }
    public void getData(){
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.QUESTION_TABLE_NAME);
        if (getIntent().getStringExtra("subject").equals("政治单选")){
            query.whereEqualTo(TableUtil.QUESTION_TYPE,"00");
        }else {
            query.whereEqualTo(TableUtil.QUESTION_TYPE,"01");
        }
        //query.whereEqualTo(TableUtil.NOTE_USER, AVUser.getCurrentUser());
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    avObjectList = list;
                    handler.sendEmptyMessage(0);
                    Log.i(TAG, "done: " + list.size());
                }else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }
    public void showAnswer() {
        String[] yourSelect = avObjectList.get(index).get(TableUtil.QUESTION_SELECT).toString().split("\\|");
        Log.i(TAG, "showAnswer: " + yourSelect.length);
        if (yourSelect.length == 1) {
            if (strings.contains(yourSelect[0])) {
                toast("回答正确", 0);

            } else {
                toast("回答错误", 0);
            }
        } else {
            boolean isTrue = true;
            for (int i = 0; i < yourSelect.length; i++) {
                if (!strings.contains(yourSelect[i])) {
                    Log.i(TAG, "showAnswer: " + yourSelect[i]);
                    isTrue = false;
                }
            }
            if (isTrue) {
                toast("回答正确", 0);
            } else {
                toast("回答错误", 0);

            }
            answerAnswerTv.setVisibility(View.VISIBLE);
            answerCankaodaanTitle.setVisibility(View.VISIBLE);
            answerAnswerTv.setText(avObjectList.get(index).get(TableUtil.QUESTION_TEACH).toString());
        }
    }
    public void cancelAnswer(){
        answerAnswerTv.setVisibility(View.GONE);
        answerCankaodaanTitle.setVisibility(View.GONE);
        //answerAnswerTv.setText(avObjectList.get(index).get(TableUtil.QUESTION_TEACH).toString());
    }
}
