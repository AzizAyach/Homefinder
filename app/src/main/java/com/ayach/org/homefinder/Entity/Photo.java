package com.ayach.org.homefinder.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aziz on 27/11/2015.
 */
public class Photo implements Parcelable{

    private String large ;
    private String caption ;
    private String small ;
    private String medium ;
    private String xlarge ;


    public Photo(String large,String caption, String small ,String medium , String xlarge){
        this.setLarge(large);
        this.setCaption(caption);
        this.setSmall(small);
        this.setMedium(medium);
        this.setXlarge(xlarge);
    }
    public Photo () {}
    public Photo(Parcel in) {
        this.large = in.readString();
        this.caption = in.readString();
        this.small = in.readString();

    }
    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getXlarge() {
        return xlarge;
    }

    public void setXlarge(String xlarge) {
        this.xlarge = xlarge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(large);
        dest.writeString(caption);
        dest.writeString(small);
    }
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>()
    {
        @Override
        public Photo createFromParcel(Parcel source)
        {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size)
        {
            return new Photo[size];
        }
    };
}
