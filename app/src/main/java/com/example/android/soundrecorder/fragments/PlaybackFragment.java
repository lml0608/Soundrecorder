package com.example.android.soundrecorder.fragments;

import android.app.Dialog;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.soundrecorder.R;

import com.example.android.soundrecorder.data.RecordContract;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;

import static android.R.attr.path;


/**
 * Created by liubin on 2017/6/1.
 */

public class PlaybackFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "PlaybackFragment";
    private static final String ARG_ITEM_ID = "arg_item_id";

    private MediaPlayer mMediaPlayer = null;
    private static final int RECORDING_ITEM_LOADER = 1;

    private SeekBar mSeekBar = null;
    private FloatingActionButton mPlayButton = null;
    private TextView mCurrentProgressTextView = null;
    private TextView mFileNameTextView = null;
    private TextView mFileLengthTextView = null;
    private int mRecordId;
    private Uri mUriForDateClicked;
    private String mRecordName;

    private boolean isPlaying = false;
    private String mPath;

    public static PlaybackFragment newInstance(int id) {
        PlaybackFragment f = new PlaybackFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_ITEM_ID, id);
        f.setArguments(b);

        return f;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(RECORDING_ITEM_LOADER, null, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mRecordId = getArguments().getInt(ARG_ITEM_ID);

        mUriForDateClicked = RecordContract.RecordEntry.CONTENT_URI.buildUpon()
                .appendPath(Long.toString(mRecordId))
                .build();
        ;

        Log.i(TAG, String.valueOf(mRecordId));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_media_playback, null);

        mFileNameTextView = (TextView) v.findViewById(R.id.file_name_text_view);
        mFileLengthTextView = (TextView) v.findViewById(R.id.file_length_text_view);
        mCurrentProgressTextView = (TextView) v.findViewById(R.id.current_progress_text_view);

        mSeekBar = (SeekBar) v.findViewById(R.id.seekbar);
        mPlayButton = (FloatingActionButton) v.findViewById(R.id.fab_play);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(isPlaying);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("nihao")
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    private void onPlay(boolean isPlaying){
        if (!isPlaying) {
            //currently MediaPlayer is not playing audio
            if(mMediaPlayer == null) {
                startPlaying(); //start from beginning
            } else {
                //resumePlaying(); //resume the currently paused MediaPlayer
            }

        } else {
            //pause the MediaPlayer
            //pausePlaying();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                RecordContract.RecordEntry.COLUMN_RECORDING_NAME,
                RecordContract.RecordEntry.COLUMN_RECORDING_FILE_PATH,
                RecordContract.RecordEntry.COLUMN_RECORDING_LENGTH,
                RecordContract.RecordEntry.COLUMN_TIME_ADDED};

        return new CursorLoader(getActivity(),
                mUriForDateClicked,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();
        mRecordName = data.getString(data.getColumnIndex(RecordContract.RecordEntry.COLUMN_RECORDING_NAME));
        long itemDuration = data.getLong(data.getColumnIndex(RecordContract.RecordEntry.COLUMN_RECORDING_LENGTH));
        mPath = data.getString(data.getColumnIndex(RecordContract.RecordEntry.COLUMN_RECORDING_FILE_PATH));
        long recordTime = data.getLong(data.getColumnIndex(RecordContract.RecordEntry.COLUMN_TIME_ADDED));

        Log.i(TAG, mRecordName + itemDuration + "\n" + mPath + recordTime);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void startPlaying() {


        mPlayButton.setImageResource(R.drawable.ic_media_pause);
        mMediaPlayer = new MediaPlayer();


        try {
            mMediaPlayer.setDataSource(mPath);
            mMediaPlayer.prepare();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "nihao");
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {

            Log.e(TAG, "prepare() failed");
        }
    }





}
