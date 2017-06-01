package com.example.android.soundrecorder;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.android.soundrecorder.data.RecordContract;
import com.example.android.soundrecorder.data.RecordContract.RecordEntry;
import com.example.android.soundrecorder.data.RecordDbHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liubin on 2017/5/27.
 */

public class RecordingService extends Service {

    private static final String TAG = "RecordingService";

    private String mFileName;//文件名
    private String mFilePath;//文件路径
    private MediaRecorder mRecorder;




    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;
    private int mElapsedSeconds = 0;
    //private OnTimerChangedListener onTimerChangedListener = null;
    private static final SimpleDateFormat mTimerFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());

    private Timer mTimer = null;
    private TimerTask mIncrementTimerTask = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {

        //服务销毁的时候停止录音
        if (mRecorder != null) {
            stopRecording();
        }
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }


    public void startRecording() {

        setFileNameAndPath();
        mRecorder = new MediaRecorder();

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);

        try {

            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

    }

    public void stopRecording() {

        mRecorder.stop();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        try {

            ContentValues values = new ContentValues();

            values.put(RecordEntry.COLUMN_RECORDING_NAME, mFileName);
            values.put(RecordEntry.COLUMN_RECORDING_FILE_PATH, mFilePath);
            values.put(RecordEntry.COLUMN_TIME_ADDED, System.currentTimeMillis());
            values.put(RecordEntry.COLUMN_RECORDING_LENGTH, mElapsedMillis);
            Uri newUri = getContentResolver().insert(RecordEntry.CONTENT_URI, values);
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.insert_recording_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_recording_successful),
                        Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e) {
            Log.e(TAG, "exception", e);
        }
        Toast.makeText(this, getString(R.string.toast_recording_finish), Toast.LENGTH_SHORT).show();


    }

    public void setFileNameAndPath() {

        int count = 0;
        File f;

        do{
            count++;

            Cursor query = getContentResolver().query(RecordEntry.CONTENT_URI, new String[]{"count(*)"}, null, null, null);
            query.moveToFirst();
            int recordingCount = query.getInt(0);

            Log.i(TAG, "recordingCount" + recordingCount);
            mFileName = getString(R.string.default_file_name)
                    + "_" + (recordingCount + count) + ".mp4";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/SoundRecorder1/" + mFileName;

            f = new File(mFilePath);
        }while (f.exists() && !f.isDirectory());
    }
}

