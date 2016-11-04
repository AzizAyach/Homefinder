package com.ayach.org.homefinder.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aziz on 27/11/2015.
 */
public class Location implements Parcelable {

    private String city ;
    private String all;
    private String country ;
    private String state ;
    private String streetName ;
    private String postalCode ;
    private String neighbourhood ;
public Location (String city, String all, String country,String state ,String streetName , String postalCode ,String neighbourhood) {

    this.city=city;
    this.all=all;
    this.country=country;
    this.state =state;
    this.streetName=streetName;
    this.postalCode=postalCode;
    this.neighbourhood=neighbourhood;}
    public Location(){}
    public Location(Parcel in) {
        this.city = in.readString();
        this.all = in.readString();
        this.country = in.readString();
        this.state = in.readString();
        this.streetName = in.readString();
        this.postalCode = in.readString();
        this.neighbourhood = in.readString();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(all);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(streetName);
        dest.writeString(postalCode);
        dest.writeString(neighbourhood);
    }
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>()
    {
        @Override
        public Location createFromParcel(Parcel source)
        {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size)
        {
            return new Location[size];
        }
    };
}
