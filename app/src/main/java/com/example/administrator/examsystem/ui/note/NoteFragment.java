package com.example.administrator.examsystem.ui.note;


import android.content.Intent;
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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.OnClickListener;
import com.example.administrator.examsystem.ui.adapter.NoteAdapter;
import com.example.administrator.examsystem.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {


    @BindView(R.id.note_recyclerView)
    RecyclerView noteRecyclerView;
    @BindView(R.id.note_add_btn)
    FloatingActionButton noteAddBtn;
    Unbinder unbinder;
    private NoteAdapter noteAdapter;
    private List<AVObject>noteList = new ArrayList<>();
    private static final String TAG = "NoteFragment";

    public NoteFragment() {
        // Required empty public constructor
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initRecyclerView();
        }
    };

    public static NoteFragment getInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.NOTE_TABLE_NAME);
        query.whereEqualTo(TableUtil.NOTE_USER, AVUser.getCurrentUser());
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    noteList = list;
                    handler.sendEmptyMessage(0);
                    Log.i(TAG, "done: "+ list.size());
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    private void initRecyclerView() {
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteAdapter = new NoteAdapter(getContext(),noteList);
        noteAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(View view, int position) {
                Intent intent = new Intent(getActivity(),WrongQuestionActivity.class);
                intent.putExtra("id",noteList.get(position).getObjectId());
                startActivityForResult(intent,100);
            }
        });
        noteRecyclerView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.note_add_btn)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(),AddNoteActivity.class);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}
