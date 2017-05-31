package com.example.android.soundrecorder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.soundrecorder.RecordingItem;
import com.example.android.soundrecorder.data.RecordContract.RecordEntry;
import com.example.android.soundrecorder.listeners.OnDatabaseChangedListener;

/**
 * Created by liubin on 2017/5/28.
 */

public class RecordDbHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static OnDatabaseChangedListener mOnDatabaseChangedListener;

    public static final String DATABASE_NAME = "recordr.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RecordEntry.TABLE_NAME + " (" +
                    RecordEntry._ID + " INTEGER PRIMARY KEY," +
                    RecordEntry.COLUMN_RECORDING_NAME + " TEXT," +
                    RecordEntry.COLUMN_RECORDING_FILE_PATH + " TEXT," +
                    RecordEntry.COLUMN_RECORDING_LENGTH + " INTEGER," +
                    RecordEntry.COLUMN_TIME_ADDED + " INTEGER " + ")";

    public RecordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        mOnDatabaseChangedListener = listener;
    }

    public long addRecording(String recordingName, String filePath, long length) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RecordEntry.COLUMN_RECORDING_NAME, recordingName);
        cv.put(RecordEntry.COLUMN_RECORDING_FILE_PATH , filePath);
        cv.put(RecordEntry.COLUMN_RECORDING_LENGTH, length);
        cv.put(RecordEntry.COLUMN_TIME_ADDED, System.currentTimeMillis());
        long rowId = db.insert(RecordEntry.TABLE_NAME, null, cv);

        //数据看添加新数据的时候，调用接口方法onNewDatabaseEntryAdded
        if (mOnDatabaseChangedListener != null) {
            mOnDatabaseChangedListener.onNewDatabaseEntryAdded();
        }

        return rowId;
    }


    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { RecordEntry._ID };
        Cursor c = db.query(RecordEntry.TABLE_NAME, projection, null, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }


    public RecordingItem getItemAt(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                RecordEntry._ID,
                RecordEntry.COLUMN_RECORDING_NAME,
                RecordEntry.COLUMN_RECORDING_FILE_PATH,
                RecordEntry.COLUMN_RECORDING_LENGTH,
                RecordEntry.COLUMN_TIME_ADDED
        };
        Cursor c = db.query(RecordEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c.moveToPosition(position)) {
            RecordingItem item = new RecordingItem();
            item.setId(c.getInt(c.getColumnIndex(RecordEntry._ID)));
            item.setName(c.getString(c.getColumnIndex(RecordEntry.COLUMN_RECORDING_NAME)));
            item.setFilePath(c.getString(c.getColumnIndex(RecordEntry.COLUMN_RECORDING_FILE_PATH)));
            item.setLength(c.getInt(c.getColumnIndex(RecordEntry.COLUMN_RECORDING_LENGTH)));
            item.setTime(c.getLong(c.getColumnIndex(RecordEntry.COLUMN_TIME_ADDED)));
            c.close();
            return item;
        }
        return null;
    }
}
