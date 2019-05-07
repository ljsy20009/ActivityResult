package com.github.kk.activityresult;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class ActivityResultInfo implements Parcelable {

    private int resultCode;
    private Intent data;

    public ActivityResultInfo(int resultCode, Intent data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultCode);
        dest.writeParcelable(this.data, flags);
    }

    protected ActivityResultInfo(Parcel in) {
        this.resultCode = in.readInt();
        this.data = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityResultInfo> CREATOR = new Parcelable.Creator<ActivityResultInfo>() {
        @Override
        public ActivityResultInfo createFromParcel(Parcel source) {
            return new ActivityResultInfo(source);
        }

        @Override
        public ActivityResultInfo[] newArray(int size) {
            return new ActivityResultInfo[size];
        }
    };
}
