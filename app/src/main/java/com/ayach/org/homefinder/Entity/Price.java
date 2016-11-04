package com.ayach.org.homefinder.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aziz on 27/11/2015.
 */
public class Price implements Parcelable {

    private int monthly;
    private int nightly;
    private int maxNight;
    private int weekend;
    private int minNight;
    private int weekly;


    public Price(int monthly, int nightly, int weekend, int weekly, int minNight,int maxNight) {

        this.monthly=monthly;
        this.nightly=nightly;
        this.maxNight=maxNight;
        this.weekend=weekend;
        this.minNight=minNight;
        this.weekly=weekly;


    }

    public Price(Parcel in) {
        this.monthly = in.readInt();
        this.nightly = in.readInt();
        this.weekly = in.readInt();
        this.weekend = in.readInt();
        this.maxNight = in.readInt();
        this.minNight = in.readInt();
    }

    public Price() {
    }

    public int getMonthly() {
        return monthly;
    }

    public void setMonthly(int monthly) {
        this.monthly = monthly;
    }

    public int getNightly() {
        return nightly;
    }

    public void setNightly(int nightly) {
        this.nightly = nightly;
    }

    public int getMaxNight() {
        return maxNight;
    }

    public void setMaxNight(int maxNight) {
        this.maxNight = maxNight;
    }

    public int getWeekend() {
        return weekend;
    }

    public void setWeekend(int weekend) {
        this.weekend = weekend;
    }

    public int getMinNight() {
        return minNight;
    }

    public void setMinNight(int minNight) {
        this.minNight = minNight;
    }

    public int getWeekly() {
        return weekly;
    }

    public void setWeekly(int weekly) {
        this.weekly = weekly;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(monthly);
        dest.writeInt(nightly);
        dest.writeInt(maxNight);
        dest.writeInt(weekend);
        dest.writeInt(minNight);
        dest.writeInt(weekly);
    }
    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>()
    {
        @Override
        public Price createFromParcel(Parcel source)
        {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size)
        {
            return new Price[size];
        }
    };
}

