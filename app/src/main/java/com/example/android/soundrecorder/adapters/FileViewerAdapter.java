package com.example.android.soundrecorder.adapters;

import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordingsViewHolder> {

    private static final String TAG = "FileViewerAdapter";

    @Override
    public RecordingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecordingsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecordingsViewHolder extends RecyclerView.ViewHolder {

        public RecordingsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
