package com.example.android.soundrecorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soundrecorder.R;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class FileViewerFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    private RecyclerView mRecyclerView;

    public static FileViewerFragment newInstance(int position){
        
        Bundle bundle = new Bundle();
        
        bundle.putInt(ARG_POSITION, position);

        FileViewerFragment fragment = new FileViewerFragment();

        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_file_viewer, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recording_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
