package com.mperfit.perfit.ui.imagechooser.imagechooser.album;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 单个图片信息实体类
 */
public class ImageInfo implements Parcelable {



    //state用于判断选中
    public int state;
    public String path;
    public long time;
    public String displayName;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state);
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.displayName);
    }

    public ImageInfo() {
    }

    protected ImageInfo(Parcel in) {
        this.state = in.readInt();
        this.path = in.readString();
        this.time = in.readLong();
        this.displayName = in.readString();
    }

    public static final Parcelable.Creator<ImageInfo> CREATOR = new Parcelable.Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
