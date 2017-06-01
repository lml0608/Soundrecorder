package com.example.android.soundrecorder.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by liubin on 2017/5/28.
 */

public class RecordContract {
    

    public static final String CONTENT_AUTHORITY = "com.example.android.soundrecorder";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_PETS = "record";

    public static final class RecordEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String TABLE_NAME = "record";

        public static final String COLUMN_RECORDING_NAME = "name";
        public static final String COLUMN_RECORDING_FILE_PATH = "path";
        public static final String COLUMN_RECORDING_LENGTH = "length";
        public static final String COLUMN_TIME_ADDED = "time";
    }
}
