package com.example.administrator.examsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */

public class DiscussImageAdapter extends RecyclerView.Adapter<DiscussImageAdapter.ViewHolder> {
    private Context context;
    private List<String>list = new ArrayList<>();
    private OnClickListener onClickListener;

    public DiscussImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discuss_img_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (onClickListener!=null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.click(view,holder.getLayoutPosition());
                }
            });
        }
        Glide.with(context)
                .load(list.get(position))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.discuss_img_item);
        }
    }
}
