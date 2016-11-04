package com.ayach.org.homefinder.Entity;

/**
 * Created by aziz on 27/11/2015.
 */
public class Availability {

    private int start;
    private int end;
public Availability(int start,int end){
    this.setStart(start);
    this.setEnd(end);
}
    public Availability(){}
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
