package com.ayach.org.homefinder.Page;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ayach.org.homefinder.Fragments.Fragment_Price;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by aziz on 31/12/2015.
 */
public class PagePrice extends Page {
    public static final String Mounth_DATA_KEY = "mounth";
    public static final String Nightly_DATA_KEY = "nightly";
    public static final String Max_DATA_KEY = "max";
    public static final String Min_DATA_KEY = "min";
    public static final String Week_DATA_KEY = "week";
    public static final String Weekend_DATA_KEY = "weekrnd";
    public PagePrice(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }
    @Override
    public Fragment createFragment() {
        return Fragment_Price.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Mounth", mData.getString(Mounth_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Nightly", mData.getString(Nightly_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Max Nightly", mData.getString(Max_DATA_KEY ), getKey(), -1));
        dest.add(new ReviewItem("Min Nightly", mData.getString(Min_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Weekly", mData.getString(Week_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Weekend", mData.getString(Weekend_DATA_KEY), getKey(), -1));
    }
    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(Min_DATA_KEY));
    }
}
