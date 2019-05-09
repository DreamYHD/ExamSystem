package com.example.administrator.examsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.examsystem.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/10.
 */

public class DiscussItemAdapter extends RecyclerView.Adapter<DiscussItemAdapter.ViewHolder> {
    private static final String TAG = "DiscussItemAdapter";
    private Context context;
    private List<String> list = new ArrayList<>();

    public DiscussItemAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discuss_item_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String [] strings = list.get(position).split("\\|");
        Log.i(TAG, "onBindViewHolder: "+strings[0]+strings[0]);
        holder.discussItemNameTv.setText(strings[0]+":");
        holder.discussItemContentTv.setText(strings[1]);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.discuss_item_name_tv)
        TextView discussItemNameTv;
        @BindView(R.id.discuss_item_content_tv)
        TextView discussItemContentTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
