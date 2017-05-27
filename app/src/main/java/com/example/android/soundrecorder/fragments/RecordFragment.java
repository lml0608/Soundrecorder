package com.example.android.soundrecorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soundrecorder.R;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class RecordFragment extends Fragment {

    public static RecordFragment newInstance(int position){


        return new RecordFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_record, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
