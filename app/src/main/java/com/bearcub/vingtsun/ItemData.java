package com.bearcub.vingtsun;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Home on 7/16/2015.
 */
public class ItemData implements Parcelable {
    int imageId;
    String label;

    public ItemData(){}

    public ItemData(Parcel in) {
        imageId = in.readInt();
        label = in.readString();
    }

    public static final Parcelable.Creator<ItemData> CREATOR = new Parcelable.Creator<ItemData>() {
        public ItemData createFromParcel(Parcel in) {
            return new ItemData(in);
        }
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeString(label);
    }
}
