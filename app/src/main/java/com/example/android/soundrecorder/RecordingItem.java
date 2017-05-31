package com.example.android.soundrecorder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liubin on 2017/5/27.
 */

public class RecordingItem implements Parcelable {


    private String mName;//文件名称
    private String mFilePath;//文件路径
    private int mId;//ID in 数据看
    private int mLength;// 文件长度，录音时间，秒
    private long mTime;// 录制时间 date／time

    public RecordingItem() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long title) {
        mTime = title;
    }

    protected RecordingItem(Parcel in) {
        mName = in.readString();
        mFilePath = in.readString();
        mId = in.readInt();
        mLength = in.readInt();
        mTime = in.readLong();
    }

    //写入接口函数，打包
    //将对象序列化为一个parcel对象
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mFilePath);
        dest.writeInt(mId);
        dest.writeInt(mLength);
        dest.writeLong(mTime);
    }

    //接口描述接口，
    @Override
    public int describeContents() {
        return 0;
    }

    //返回单个和多个继承类实例
    public static final Creator<RecordingItem> CREATOR = new Creator<RecordingItem>() {
        @Override
        public RecordingItem createFromParcel(Parcel in) {
            return new RecordingItem(in);
        }

        @Override
        public RecordingItem[] newArray(int size) {
            return new RecordingItem[size];
        }
    };
}
