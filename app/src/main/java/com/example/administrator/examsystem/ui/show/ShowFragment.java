package com.example.administrator.examsystem.ui.show;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.ui.adapter.PlanAdapter;
import com.example.administrator.examsystem.utils.DayToDay;
import com.example.administrator.examsystem.utils.TableUtil;
import com.example.administrator.examsystem.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zhouzhuo.zzhorizontalcalenderview.ZzHorizontalCalenderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment {


    private static final String TAG = "ShowFragment";
    private static final int RESULT_CODE = 100;
    @BindView(R.id.show_day_text)
    TextView showDayText;
    @BindView(R.id.show_min_text)
    TextView showMinText;
    @BindView(R.id.zz_horizontal_calender_view)
    ZzHorizontalCalenderView zzHorizontalCalenderView;
    Unbinder unbinder;
    @BindView(R.id.plan_add_btn)
    FloatingActionButton planAddBtn;
    private List<AVObject> planList = new ArrayList<>();
    private PlanAdapter planAdapter;


    private static long mDay = DayToDay.getDay();// 天
    private static long mHour = 11;//小时,
    private static long mMin = 56;//分钟,
    private static long mSecond = 32;//秒
    @BindView(R.id.recyclerView_paln)
    RecyclerView recyclerViewPaln;

    private Timer mTimer = new Timer();

    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initRecyclerView();
        }
    };
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                showDayText.setText(mDay + "");//天数不用补位
                showMinText.setText(DayToDay.TimeDiff() + getTv(mSecond));
                if (mSecond == 0 && mDay == 0 && mHour == 0 && mMin == 0) {
                    mTimer.cancel();
                }
            }
        }
    };
    private void initRecyclerView() {
        planAdapter = new PlanAdapter(getContext(), planList);
        recyclerViewPaln.setAdapter(planAdapter);
        recyclerViewPaln.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapter.notifyDataSetChanged();

    }

    private void initData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.PLAN_TABLE_NAME);
        query.whereEqualTo(TableUtil.PLAN_DATE, TimeUtil.getDate());
        Log.i(TAG, "initData: "+TimeUtil.getDate() );
        query.whereEqualTo(TableUtil.PLAN_USER, AVUser.getCurrentUser());
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    planList = list;
                    mainHandler.sendEmptyMessage(0);
                    Log.i(TAG, "done: "+ list.size());
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    public ShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
        initView();
    }

    public static ShowFragment getInstance() {
        return new ShowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        startRun();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        initData();
        initView();
        startRun();
    }

    private void initView() {
        initRecyclerView();
        zzHorizontalCalenderView.setOnDaySelectedListener(new ZzHorizontalCalenderView.OnDaySelectedListener() {
            @Override
            public void onSelected(boolean hasChanged, int year, int month, int day, int week) {
                Log.i(TAG, "onSelected: " + "日期是否有变化:" + hasChanged + ",\n\n日期:" + year + "-" + month + "-" + day + ",\n\n星期:" + week);
                AVQuery<AVObject> query = new AVQuery<>(TableUtil.PLAN_TABLE_NAME);
                String monthS = "";
                String dayS = "";
                if (month>=1 && month <= 9){
                    monthS = "0"+ month;
                }
                if (day>=1 && day <= 9){
                    dayS = "0" + day;
                }
                query.whereEqualTo(TableUtil.PLAN_DATE, year + "-" + monthS + "-" + dayS);
                query.whereEqualTo(TableUtil.PLAN_USER, AVUser.getCurrentUser());
                // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e == null){
                            planList = list;
                            mainHandler.sendEmptyMessage(0);
                            Log.i(TAG, "done: "+ list.size());
                        }else {
                            Log.e(TAG, "done: "+e.getMessage() );
                        }
                    }
                });
            }
        });
        //动态设置各种属性值：
        zzHorizontalCalenderView.setShowPickDialog(true);
        zzHorizontalCalenderView.setUnitColorResId(android.R.color.holo_green_dark);
        zzHorizontalCalenderView.setDayTextColorSelectedResId(android.R.color.holo_blue_bright);
        zzHorizontalCalenderView.setDayTextColorNormalResId(android.R.color.holo_red_dark);
        zzHorizontalCalenderView.setDaySelectionColorResId(android.R.color.holo_orange_dark);
        zzHorizontalCalenderView.setTodayPointColor(Color.YELLOW);
        zzHorizontalCalenderView.setMonthTextColor(Color.CYAN);
        zzHorizontalCalenderView.setYearTextColor(Color.CYAN);
    }

    /**
     * 倒计时计算
     */
    private static void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if (mDay < 0) {
                        // 倒计时结束
                        mDay = 0;
                        mHour = 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }

    private static String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }

    /**
     * 开启倒计时
     * //time为Date类型：在指定时间执行一次。
     * timer.schedule(task, time);
     * //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     * timer.schedule(task, firstTime,period);
     * //delay 为long类型：从现在起过delay毫秒执行一次。
     * timer.schedule(task, delay);
     * //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     * timer.schedule(task, delay,period);
     */
    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTimer.cancel();

        unbinder.unbind();
    }

    @OnClick(R.id.plan_add_btn)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(),AddPlanActivity.class);
        startActivityForResult(intent,RESULT_CODE);
    }
}
