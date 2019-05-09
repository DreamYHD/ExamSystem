package com.example.administrator.examsystem.ui.plan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.BaseActivity;
import com.example.administrator.examsystem.utils.DayToDay;
import com.example.administrator.examsystem.utils.FlexTextUtil;
import com.example.administrator.examsystem.utils.TableUtil;
import com.example.administrator.examsystem.utils.TimeUtil;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class AddPlanActivity extends BaseActivity {

    private static final String TAG = "AddPlanActivity";
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.post_btn)
    ImageView postBtn;
    @BindView(R.id.plan_edit)
    EditText planEdit;
    @BindView(R.id.flex_text)
    FlexboxLayout flexText;
    @BindView(R.id.start_time_text)
    TextView startTimeText;
    @BindView(R.id.end_time_text)
    TextView endTimeText;
    private String[] types = new String[]{"早起", "锻炼身体", "学习英语", "背政治", "做专业课", "午休", "晚睡", "吃饭"};

    private TextView createNewFlexItemTextView(final String string) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(string);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView.setBackgroundResource(R.drawable.shape_back);
        textView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Log.e(TAG, string);
                String s = planEdit.getText().toString();
                s += string;
                planEdit.setText(s);
                planEdit.setSelection(s.length());
            }
        });
        int padding = FlexTextUtil.dpToPixel(this, 3);
        int paddingLeftAndRight = FlexTextUtil.dpToPixel(this, 4);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight + 4, padding, paddingLeftAndRight + 4, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = FlexTextUtil.dpToPixel(this, 4);
        int marginTop = FlexTextUtil.dpToPixel(this, 8);
        layoutParams.setMargins(margin + 10, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private void displayFlexSubject(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            flexText.addView(createNewFlexItemTextView(strings[i]));
        }
    }


    @OnClick(R.id.back_btn)
    public void onBackBtnClicked() {
        setResult(1000);
        this.finish();
    }

    @OnClick(R.id.post_btn)
    public void onPostBtnClicked() {
        String planContent = planEdit.getText().toString().trim();
        String planStartTime = startTimeText.getText().toString().trim();
        String planEndTime = endTimeText.getText().toString().trim();
        String planDate = TimeUtil.getDate();
        String planIsFinish = "no";
        Log.i(TAG, "onPostBtnClicked: "+planDate);
        if (avUserFinal != null){
            AVObject plan = new AVObject(TableUtil.PLAN_TABLE_NAME);
            plan.put(TableUtil.PLAN_CONTENT,planContent);
            plan.put(TableUtil.PLAN_START_TIME,planStartTime);
            plan.put(TableUtil.PLAN_END_TIME,planEndTime);
            plan.put(TableUtil.PLAN_DATE,planDate);
            plan.put(TableUtil.PLAN_USER,avUserFinal);
            plan.put(TableUtil.PLAN_IS_FINISH,planIsFinish);
            plan.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null){
                        snackBar(findViewById(R.id.post_btn), "提交成功", 1);
                        setResult(1000);
                        mActivity.finish();
                    }else {
                        snackBar(findViewById(R.id.post_btn), "提交失败", 1);
                        Log.e(TAG, "done: 提交失败"+e.getMessage());
                    }
                }
            });
        }else {
            snackBar(findViewById(R.id.post_btn), "请先登录", 1);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(1000);
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.start_time_text)
    public void onStartTimeTextClicked() {

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                startTimeText.setText(DayToDay.getTime(date).split(" ")[1]);
            }
        }).build();
        pvTime.show();

    }

    @OnClick(R.id.end_time_text)
    public void onEndTimeTextClicked() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Log.i(TAG, "onTimeSelect: "+DayToDay.getTime(date));
                endTimeText.setText(DayToDay.getTime(date).split(" ")[1]);
            }
        }).build();

        pvTime.show();
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        displayFlexSubject(types);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_plan;
    }
}
