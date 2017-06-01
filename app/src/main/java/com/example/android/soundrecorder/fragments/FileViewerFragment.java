package com.example.android.soundrecorder.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.soundrecorder.R;
import com.example.android.soundrecorder.adapters.FileViewerAdapter;
import com.example.android.soundrecorder.data.RecordContract;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class FileViewerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "FileViewerFragment";

    private static final String ARG_POSITION = "position";

    private static final int RECORDING_LOADER = 0;

    private int position;
    private RecyclerView mRecyclerView;

    FileViewerAdapter mViewerAdapter;

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
        mObserver.startWatching();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(RECORDING_LOADER, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_file_viewer, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recording_recycler_view);
        mRecyclerView.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mViewerAdapter = new FileViewerAdapter(getActivity(), linearLayoutManager);


        mRecyclerView.setAdapter(mViewerAdapter);

        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                RecordContract.RecordEntry._ID,
                RecordContract.RecordEntry.COLUMN_RECORDING_NAME,
                RecordContract.RecordEntry.COLUMN_RECORDING_FILE_PATH,
                RecordContract.RecordEntry.COLUMN_RECORDING_LENGTH,
                RecordContract.RecordEntry.COLUMN_TIME_ADDED};

        String sortOrder = RecordContract.RecordEntry.COLUMN_TIME_ADDED + " DESC";
        return new CursorLoader(getActivity(),
                RecordContract.RecordEntry.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mViewerAdapter.swapCursor(data);

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mViewerAdapter.swapCursor(null);
    }


    FileObserver mObserver  = new FileObserver(Environment.getExternalStorageDirectory().toString()
                    + "/SoundRecorder1") {
        @Override
        public void onEvent(int event, String path) {
            if (event == FileObserver.DELETE) {

                Log.i(TAG, "path=" + path);

                String filePath = Environment.getExternalStorageDirectory().toString()
                        + "/SoundRecorder1" + path;
                Log.i(TAG, "path=" + path);

                mViewerAdapter.removeFile(path);
            }
        }
    };
}
