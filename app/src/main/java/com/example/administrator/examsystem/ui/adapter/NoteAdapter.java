package com.example.administrator.examsystem.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<AVObject> list = new ArrayList<>();
    private OnClickListener onClickListener;

    public NoteAdapter(Context context, List<AVObject> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (onClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.click(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
        holder.tagQuestionItem.setText("#"+list.get(position).get(TableUtil.NOTE_TITLE));
        holder.descriptionQuestionItem.setText(list.get(position).get(TableUtil.NOTE_NAME).toString());
        Glide.with(context)
                .load(list.get(position).get(TableUtil.NOTE_IMG))
                .into(holder.imgQuestionItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.description_question_item)
        TextView descriptionQuestionItem;
        @BindView(R.id.img_question_item)
        ImageView imgQuestionItem;
        @BindView(R.id.tag_question_item)
        TextView tagQuestionItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
