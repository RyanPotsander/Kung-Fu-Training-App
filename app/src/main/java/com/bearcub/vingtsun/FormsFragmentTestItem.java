package com.bearcub.vingtsun;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Home on 7/16/2015.
 */
public class FormsFragmentTestItem implements Parcelable {
    int imageId;
    String label;

    public FormsFragmentTestItem(){}

    public FormsFragmentTestItem(Parcel in) {
        imageId = in.readInt();
        label = in.readString();
    }

    public static final Parcelable.Creator<FormsFragmentTestItem> CREATOR = new Parcelable.Creator<FormsFragmentTestItem>() {
        public FormsFragmentTestItem createFromParcel(Parcel in) {
            return new FormsFragmentTestItem(in);
        }
        public FormsFragmentTestItem[] newArray(int size) {
            return new FormsFragmentTestItem[size];
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