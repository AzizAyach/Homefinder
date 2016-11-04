package com.ayach.org.homefinder.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziz on 27/11/2015.
 */
public class Logement implements Parcelable{

    private String id ;
    private String iduser;
    private List<Double> latLng ;
    private Attr attr ;
    private Price price ;
    private List<Photo> photos;
    private Location location ;
  //  public Provider provider { get; set; }
  private List<Availability> availability ;
public Logement(String id ,List<Double> latLng,Attr attr, Price price , List<Photo> photos, Location location,List<Availability> availability,String iduser){
    this.setId(id);
    this.setIduser(iduser);
    this.setLatLng(latLng);
    this.setAttr(attr);
    this.setPrice(price);
    this.setPhotos(photos);
    this.setLocation(location);
    this.setAvailability(availability);
}
    public Logement (){}
    public Logement(Parcel in) {
        this.id = in.readString();
        this.iduser = in.readString();
        this.latLng = (ArrayList<Double>) in.readSerializable();
        this.photos=in.createTypedArrayList(Photo.CREATOR);
        this.attr= (Attr)in.readParcelable(Logement.class.getClassLoader());
        this.location= (Location)in.readParcelable(Logement.class.getClassLoader());
        this.price= (Price)in.readParcelable(Logement.class.getClassLoader());

    }
    public Logement(String test, Price prix, Attr att, Location loc, ArrayList<Double> po, ParseUser user, List<Photo> phu){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Double> getLatLng() {
        return latLng;
    }

    public void setLatLng(List<Double> latLng) {
        this.latLng = latLng;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }


    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iduser);
        dest.writeList(latLng);
        dest.writeParcelable(attr, flags);
        dest.writeParcelable(location, flags);
        dest.writeParcelable(price,flags);
        dest.writeList(photos);
    }
    public static final Parcelable.Creator<Logement> CREATOR = new Parcelable.Creator<Logement>()
    {
        @Override
        public Logement createFromParcel(Parcel source)
        {
            return new Logement(source);
        }

        @Override
        public Logement[] newArray(int size)
        {
            return new Logement[size];
        }
    };
}
