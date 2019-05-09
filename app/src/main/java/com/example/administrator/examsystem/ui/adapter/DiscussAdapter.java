package com.example.administrator.examsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.OnClickListener;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/5.
 */

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.ViewHolder> {
    private static final String TAG = "DiscussAdapter";
    private Context context;
    private List<AVObject> list = new ArrayList<>();

    private OnClickListener onClickListener;
    public DiscussAdapter(Context context, List<AVObject> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discuss_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (onClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.click(view,holder.getLayoutPosition());
                }
            });
        }
        final AVObject avUser = (AVObject) list.get(position).get(TableUtil.DISCUSS_USER);
        if (avUser!=null){
            Log.i(TAG, "onBindViewHolder:++"+avUser.getObjectId());
            AVQuery<AVObject> avQuery = new AVQuery<>(TableUtil.USER_TABLE_NAME);
            avQuery.getInBackground(avUser.getObjectId(), new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    holder.fragmentDiscussItemSchool.setText(avObject.get(TableUtil.USER_SCHOOL).toString());
                    holder.fragmentDiscussItemName.setText(avObject .get(TableUtil.USER_NAME).toString());
                }
            });
            holder.fragmentDiscussItemTime.setText(list.get(position).get(TableUtil.DISCUSS_TIME).toString());
            holder.fragmentDiscussItemDescription.setText(list.get(position).get(TableUtil.DISCUSS_CONTENT).toString());
            List<String>strings = (List<String>) list.get(position).get(TableUtil.DISCUSS_IMG);
            Glide.with(context)
                    .load(strings.get(0))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.fragmentDiscussItemImg);
        }else {
            Log.e(TAG, "onBindViewHolder: " );
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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
