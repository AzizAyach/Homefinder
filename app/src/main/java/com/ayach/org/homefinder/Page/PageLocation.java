package com.ayach.org.homefinder.Page;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ayach.org.homefinder.Fragments.Fragment_Location;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by aziz on 31/12/2015.
 */
public class PageLocation extends Page {
    public static final String Country_DATA_KEY = "pays";
    public static final String State_DATA_KEY = "gouvernorat";
    public static final String StreetName_DATA_KEY = "cite";
    public static final String City_DATA_KEY = "City";
    public static final String All_DATA_KEY = "adresse";
    public static final String Postal_DATA_KEY = "postal";
    public static final String Neighbourhood_DATA_KEY = "neighbor";

    public PageLocation(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return Fragment_Location.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Country", mData.getString(Country_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("State", mData.getString(State_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("City", mData.getString(City_DATA_KEY ), getKey(), -1));
        dest.add(new ReviewItem("Street", mData.getString(StreetName_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Neighbourhood", mData.getString(Neighbourhood_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("More", mData.getString(All_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Postal Code", mData.getString(Postal_DATA_KEY), getKey(), -1));


    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(All_DATA_KEY));
    }
}
