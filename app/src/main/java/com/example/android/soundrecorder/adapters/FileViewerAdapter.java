package com.example.android.soundrecorder.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android.soundrecorder.R;
import com.example.android.soundrecorder.RecordingItem;
import com.example.android.soundrecorder.data.RecordContract;
import com.example.android.soundrecorder.data.RecordDbHelper;
import com.example.android.soundrecorder.listeners.OnDatabaseChangedListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordingsViewHolder>
        implements OnDatabaseChangedListener{

    private static final String TAG = "FileViewerAdapter";

    private Cursor mCursor;
    private Context mContext;

    LinearLayoutManager mLayoutManager;

    public FileViewerAdapter(Context context, LinearLayoutManager layoutManager) {
        this.mContext = context;
        mLayoutManager = layoutManager;

    }



    @Override
    public RecordingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_recording, parent, false);

        return new RecordingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordingsViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        String recordName = mCursor.getString(mCursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_RECORDING_NAME));
        int itemDuration = mCursor.getInt(mCursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_RECORDING_LENGTH));

        int recordTime = mCursor.getInt(mCursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_TIME_ADDED));

        Log.i(TAG, recordName);


        long minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                - TimeUnit.MINUTES.toSeconds(minutes);

        holder.mRecordName.setText(recordName);
        holder.mRecordLength.setText(String.format("%02d:%02d", minutes, seconds));
        holder.mRecordDateAdded.setText(
                DateUtils.formatDateTime(
                        mContext,
                        recordTime,
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));

    }



    @Override
    public int getItemCount() {

        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    @Override
    public void onNewDatabaseEntryAdded() {

        notifyItemInserted(getItemCount() - 1);
        mLayoutManager.scrollToPosition(getItemCount() - 1);

    }

    @Override
    public void onDatabaseEntryRenamed() {

    }

    public class RecordingsViewHolder extends RecyclerView.ViewHolder {

        protected TextView mRecordName;
        protected TextView mRecordLength;
        protected TextView mRecordDateAdded;
        protected View mCardView;
        public RecordingsViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view);
            mRecordName = (TextView) v.findViewById(R.id.file_name_text);
            mRecordLength = (TextView) v.findViewById(R.id.file_length_text);
            mRecordDateAdded = (TextView) v.findViewById(R.id.file_date_added_text);

        }
    }

    public void removeFile(String filePath) {
        //user deletes a saved recording out of the application through another application
//        Log.i(TAG, "filePath=" + filePath);
//        int id = -1;
//        Uri currentPetUri = null;
//        while (mCursor.moveToNext()) {
//
//            if (filePath == mCursor.getString(mCursor.getColumnIndex(RecordContract.RecordEntry.COLUMN_RECORDING_NAME)));
//            id = mCursor.getInt(mCursor.getColumnIndex(RecordContract.RecordEntry._ID));
//            currentPetUri = ContentUris.withAppendedId(RecordContract.RecordEntry.CONTENT_URI, id);
//
//        }
//        mContext.getContentResolver().delete(currentPetUri, null, null);

    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
