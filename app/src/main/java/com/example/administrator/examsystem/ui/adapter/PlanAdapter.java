package com.example.administrator.examsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/6.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private Context context;
    private static final String TAG = "PlanAdapter";
    private List<AVObject> list = new ArrayList<>();
    public PlanAdapter(Context context, List<AVObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String planTimeItem = list.get(position).get(TableUtil.PLAN_START_TIME)+"-"+list.get(position).get(TableUtil.PLAN_END_TIME);
        String planContentItem = list.get(position).get(TableUtil.PLAN_CONTENT).toString();
        String planIsFinishItem = list.get(position).get(TableUtil.PLAN_IS_FINISH).toString();
        holder.planTimeItem.setText(planTimeItem);
        holder.planDescriptionItem.setText(planContentItem);
        holder.planSwitchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    list.get(position).put(TableUtil.PLAN_IS_FINISH,"yes");
                    list.get(position).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                //Toast.makeText(context, "你已经完成", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e(TAG, "done: something wrong ");
                            }
                        }
                    });
                }else {
                    list.get(position).put(TableUtil.PLAN_IS_FINISH,"no");
                    list.get(position).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                Toast.makeText(context, "你已经取消完成", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e(TAG, "done: something wrong ");
                            }
                        }
                    });
                }
            }
        });
        if (planIsFinishItem.equals("no")){
            holder.planSwitchItem.setChecked(false);
        }else {
            holder.planSwitchItem.setChecked(true);
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.plan_time_item)
        TextView planTimeItem;
        @BindView(R.id.plan_description_item)
        TextView planDescriptionItem;
        @BindView(R.id.plan_switch_item)
        Switch planSwitchItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
