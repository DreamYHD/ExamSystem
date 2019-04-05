package com.example.administrator.examsystem.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.examsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

}
