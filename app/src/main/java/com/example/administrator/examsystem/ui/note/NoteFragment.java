package com.example.administrator.examsystem.ui.note;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.examsystem.R;
import com.example.administrator.examsystem.base.OnClickListener;
import com.example.administrator.examsystem.ui.adapter.NoteAdapter;

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
    private List<String>list = new ArrayList<>();

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment getInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteAdapter = new NoteAdapter(getContext(),list);
        noteAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(View view, int position) {
                Intent intent = new Intent(getActivity(),WrongQuestionActivity.class);
                startActivity(intent);
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
        startActivity(intent);
    }
}
