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
import com.example.administrator.examsystem.base.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/5.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<String> list = new ArrayList<>();
    private OnClickListener onClickListener;

    public NoteAdapter(Context context, List<String> list) {
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

    }

    @Override
    public int getItemCount() {
        return 2;
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
