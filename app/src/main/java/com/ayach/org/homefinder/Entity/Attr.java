package com.ayach.org.homefinder.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aziz on 27/11/2015.
 */
public class Attr implements Parcelable {

    private int beds;
    private int bathrooms ;
    private int bedrooms;
    private int size ;
    private String description;
public Attr ( int beds,int bathrooms, int bedrooms, int size,String description){
    this.setBeds(beds);
    this.setBathrooms(bathrooms);
    this.setBedrooms(bedrooms);
    this.setSize(size);
    this.setDescription(description);

}
    public Attr(Parcel in) {
        this.beds = in.readInt();
        this.bathrooms = in.readInt();
        this.bedrooms = in.readInt();
        this.size = in.readInt();
        this.description = in.readString();
    }
    public Attr(){}
    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(beds);
        dest.writeInt(bathrooms);
        dest.writeInt(bedrooms);
        dest.writeInt(size);
        dest.writeString(description);
    }
    public static final Parcelable.Creator<Attr> CREATOR = new Parcelable.Creator<Attr>()
    {
        @Override
        public Attr createFromParcel(Parcel source)
        {
            return new Attr(source);
        }

        @Override
        public Attr[] newArray(int size)
        {
            return new Attr[size];
        }
    };
}
