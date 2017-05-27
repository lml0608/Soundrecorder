package com.example.android.soundrecorder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.soundrecorder.CordData;
import com.example.android.soundrecorder.Cording;
import com.example.android.soundrecorder.R;

import java.util.List;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordingsViewHolder> {

    private static final String TAG = "FileViewerAdapter";

    private List<Cording> cordings;
    private Context mContext;

    public FileViewerAdapter(Context context) {
        this.mContext = context;
        cordings = new CordData().getNames();
    }

    @Override
    public RecordingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new RecordingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordingsViewHolder holder, int position) {

        Cording cording = cordings.get(position);

        holder.textView.setText(cording.getName());

    }

    @Override
    public int getItemCount() {
        return cordings.size();
    }

    public class RecordingsViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public RecordingsViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
