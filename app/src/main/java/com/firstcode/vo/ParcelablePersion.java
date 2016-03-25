package com.firstcode.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangjinliang on 2016/3/25.
 */
public class ParcelablePersion implements Parcelable {
    private String name;
    private int age;

    public ParcelablePersion(){}

    public ParcelablePersion(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ParcelablePersion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
    }

    public static final Creator<ParcelablePersion> CREATOR = new Creator<ParcelablePersion>() {
        @Override
        public ParcelablePersion createFromParcel(Parcel parcel) {
            ParcelablePersion persion = new ParcelablePersion();
            persion.name = parcel.readString();
            persion.age = parcel.readInt();
            return persion;
        }

        @Override
        public ParcelablePersion[] newArray(int i) {
            return new ParcelablePersion[i];
        }
    };
}
