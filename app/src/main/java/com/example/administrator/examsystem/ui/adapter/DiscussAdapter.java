package com.example.administrator.examsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.examsystem.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/5.
 */

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.ViewHolder> {
    private Context context;
    private List<String> list = new ArrayList<>();

    public DiscussAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discuss_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }




    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fragment_discuss_item_description)
        TextView fragmentDiscussItemDescription;
        @BindView(R.id.fragment_discuss_item_img)
        ImageView fragmentDiscussItemImg;
        @BindView(R.id.fragment_discuss_item_name)
        TextView fragmentDiscussItemName;
        @BindView(R.id.fragment_discuss_item_time)
        TextView fragmentDiscussItemTime;
        @BindView(R.id.fragment_discuss_item_school)
        TextView fragmentDiscussItemSchool;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
