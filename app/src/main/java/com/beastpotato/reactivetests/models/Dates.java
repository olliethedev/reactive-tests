package com.beastpotato.reactivetests.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates implements Parcelable {

    @SerializedName("maximum")
    @Expose
    private String maximum;

    @SerializedName("minimum")
    @Expose
    private String minimum;

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMinimum() {
        return minimum;
    }

    @Override
    public String toString() {
        return
                "Dates{" +
                        "maximum = '" + maximum + '\'' +
                        ",minimum = '" + minimum + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maximum);
        dest.writeString(this.minimum);
    }

    public Dates() {
    }

    protected Dates(Parcel in) {
        this.maximum = in.readString();
        this.minimum = in.readString();
    }

    public static final Parcelable.Creator<Dates> CREATOR = new Parcelable.Creator<Dates>() {
        public Dates createFromParcel(Parcel source) {
            return new Dates(source);
        }

        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };
}