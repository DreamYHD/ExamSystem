package com.example.administrator.examsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/15.
 */

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    private List<String> list = new ArrayList<>();
    private Context context;

    private static final String TAG = "SelectAdapter";
    public SelectAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.QUESTION_TABLE_NAME);
        avQuery.getInBackground(list.get(position), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                List<String>list = (List<String>) avObject.get(TableUtil.QUESTION_ANSWER);
                holder.answerRadioIndex1Item.setText(list.get(0));
                holder.answerRadioIndex2Item.setText(list.get(1));
                holder.answerRadioIndex3Item.setText(list.get(2));
                holder.answerRadioIndex4Item.setText(list.get(3));

                String [] strings = avObject.get(TableUtil.QUESTION_SELECT).toString().split("\\|");
                Set<String> stringSet = new HashSet<>();
                for (int i = 0; i < strings.length ; i++) {
                    stringSet.add(strings[i]);
                    Log.i(TAG, "done: ");
                }
                if (stringSet.contains(holder.answerRadioIndex1Item.getText())){
                    holder.answerRadioIndex1Item.setChecked(true);
                }else {
                    holder.answerRadioIndex1Item.setChecked(false);
                }
                if (stringSet.contains(holder.answerRadioIndex2Item.getText())){
                    holder.answerRadioIndex2Item.setChecked(true);
                }else {
                    holder.answerRadioIndex2Item.setChecked(false);
                }
                if (stringSet.contains(holder.answerRadioIndex3Item.getText())){
                    holder.answerRadioIndex3Item.setChecked(true);
                }else {
                    holder.answerRadioIndex3Item.setChecked(false);
                }
                if (stringSet.contains(holder.answerRadioIndex4Item.getText())){
                    holder.answerRadioIndex4Item.setChecked(true);
                }else {
                    holder.answerRadioIndex4Item.setChecked(false);
                }
                holder.answerRadioIndex1Item.setClickable(false);
                holder.answerRadioIndex2Item.setClickable(false);;
                holder.answerRadioIndex3Item.setClickable(false);
                holder.answerRadioIndex4Item.setClickable(false);
                holder.answerTitleTvItem.setText(avObject.get(TableUtil.QUESTION_TITLE).toString());
                holder.answerTypeTvItem.setVisibility(View.VISIBLE);
                holder.answerCankaodaanTitleItem.setVisibility(View.VISIBLE);
                //科目 0：政治 1：英语
                // 00 政治单选  01政治多选     10英语1单选  11英语1阅读      12英语2单选   13英语2阅读
                switch (avObject.get(TableUtil.QUESTION_TYPE).toString()) {
                    case "00":
                        holder.answerTypeTvItem.setText("政治单选");
                        break;
                    case "01":
                        holder.answerTypeTvItem.setText("政治多选");
                        break;
                    default:
                        break;
                }
                holder.answerAnswerTvItem.setText(avObject.get(TableUtil.QUESTION_TEACH).toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.answer_type_tv_item)
        TextView answerTypeTvItem;
        @BindView(R.id.answer_title_tv_item)
        TextView answerTitleTvItem;
        @BindView(R.id.answer_radio_index1_item)
        CheckBox answerRadioIndex1Item;
        @BindView(R.id.answer_radio_index2_item)
        CheckBox answerRadioIndex2Item;
        @BindView(R.id.answer_radio_index3_item)
        CheckBox answerRadioIndex3Item;
        @BindView(R.id.answer_radio_index4_item)
        CheckBox answerRadioIndex4Item;
        @BindView(R.id.answer_radio_group_item)
        RadioGroup answerRadioGroupItem;
        @BindView(R.id.answer_cankaodaan_title_item)
        TextView answerCankaodaanTitleItem;
        @BindView(R.id.answer_answer_tv_item)
        TextView answerAnswerTvItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
