package com.example.android.soundrecorder.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.android.soundrecorder.R;
import com.example.android.soundrecorder.RecordingService;
import com.example.android.soundrecorder.adapters.FileViewerAdapter;
import com.example.android.soundrecorder.data.RecordContract;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class RecordFragment extends Fragment {

    private static final String DUG_TAG = "RecordFragment";

    private static final String ARG_POSITION = "position";

    //private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    private int position;
    private FloatingActionButton mRecordButton;
    private TextView mRecordingPrompt;
    private Chronometer mChronometer;
    private Button mPauseButton;

    //是否录音
    private Boolean mStartRecording = true;
    //是否暂停
    private Boolean mPauseRecording = true;
    long timeWhenPaused = 0;//暂停时记住时间



    public static RecordFragment newInstance(int position){
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        RecordFragment fragment = new RecordFragment();
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

        View view = inflater.inflate(R.layout.fragment_record, container, false);

        mRecordButton = (FloatingActionButton) view.findViewById(R.id.btnRecord);
        mRecordingPrompt = (TextView) view.findViewById(R.id.recording_status_text);
        mChronometer = (Chronometer) view.findViewById(R.id.chronometer);
        //ProgressBar viewById = (ProgressBar) view.findViewById(R.id.recordProgressBar);
        mPauseButton = (Button) view.findViewById(R.id.btnPause);

        //录音按钮
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecordButton.setColorNormal(getResources().getColor(R.color.colorPrimary, null));
            mRecordButton.setColorPressed(getResources().getColor(R.color.colorPrimaryDark, null));
        }

        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

                Log.i(DUG_TAG, String.valueOf(mStartRecording));
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;

            }
        });

        //默认隐藏。开始录音才出现，录完又隐藏
        mPauseButton.setVisibility(View.GONE);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPauseRecord(mPauseRecording);
                //按暂停之后，状态取反
                mPauseRecording = !mPauseRecording;
            }
        });



        return  view;
    }

    private void onPauseRecord(Boolean pause) {
        if (pause) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mPauseButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_media_play, 0, 0, 0);
            }

            mRecordingPrompt.setText((String)getString(R.string.resume_recording_button).toUpperCase());
        }

    }

    //Recording Start/Stop
    private void onRecord(Boolean start) {

        //录制服务
        Intent intent = new Intent(getActivity(), RecordingService.class);

        if (start) {

            mRecordButton.setImageResource(R.drawable.ic_media_stop);
            mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder1");

            Log.i(DUG_TAG, String.valueOf(folder));
            if (!folder.exists()) {

                Log.d(DUG_TAG, String.valueOf(folder.exists()));
                folder.mkdir();
            }

            getActivity().startService(intent);
        } else {

            mRecordButton.setImageResource(R.drawable.ic_mic_white_36dp);
            mRecordingPrompt.setText(getString(R.string.record_prompt));
            //停止服务，停止录音
            getActivity().stopService(intent);
        }


    }



}
