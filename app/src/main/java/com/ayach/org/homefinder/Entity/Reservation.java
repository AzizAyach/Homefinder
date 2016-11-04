package com.ayach.org.homefinder.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aziz on 04/01/2016.
 */
public class Reservation implements Parcelable{


    private String id;
    private String idlogement;
    private String message;
    private String debut_date;
    private String debut_fin ;
    private int type ;
    private String  propid;
    private String userid;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getDebut_date() {
        return debut_date;
    }

    public void setDebut_date(String debut_date) {
        this.debut_date = debut_date;
    }

    public String getDebut_fin() {
        return debut_fin;
    }

    public void setDebut_fin(String debut_fin) {
        this.debut_fin = debut_fin;
    }


public  Reservation(){}
    public Reservation(String id ,String logement, String message, String debut_date, String debut_fin, int type, String propid, String userid) {
        this.idlogement = logement;
        this.message = message;
        this.debut_date = debut_date;
        this.debut_fin = debut_fin;
        this.type = type;
        this.propid = propid;
        this.userid = userid;
        this.id =id;
    }
    public Reservation(Parcel in) {
        this.idlogement= in.readString();
        this.message = in.readString();
        this.debut_date = in.readString();
        this.debut_fin = in.readString();
        this.type = in.readInt();
        this.propid =in.readString();
        this.userid = in.readString();
        this.id = in.readString();
    }

    public String getIdlogement() {
        return idlogement;
    }

    public void setIdlogement(String idlogement) {
        this.idlogement = idlogement;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idlogement);
        dest.writeString(message);
       dest.writeString(debut_date);
        dest.writeString(debut_fin);
        dest.writeInt(type);
        dest.writeString(propid);
        dest.writeString(userid);
        dest.writeString(id);
    }

    public String getPropid() {
        return propid;
    }

    public void setPropid(String propid) {
        this.propid = propid;
    }
    public static final Parcelable.Creator<Reservation> CREATOR = new Parcelable.Creator<Reservation>()
    {
        @Override
        public Reservation createFromParcel(Parcel source)
        {
            return new Reservation(source);
        }

        @Override
        public Reservation[] newArray(int size)
        {
            return new Reservation[size];
        }
    };
}
