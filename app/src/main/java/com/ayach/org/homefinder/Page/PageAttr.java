package com.ayach.org.homefinder.Page;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ayach.org.homefinder.Fragments.Fragment_Attr;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by aziz on 31/12/2015.
 */
public class PageAttr extends Page {
    public static final String Bed_DATA_KEY = "bed";
    public static final String Bedroom_DATA_KEY = "bedroom";
    public static final String Bathroom_DATA_KEY = "bath";
    public static final String Size_DATA_KEY = "size";
    public static final String Descr_DATA_KEY = "descr";
    public PageAttr(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }
    @Override
    public Fragment createFragment() {
        return Fragment_Attr.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Beds", mData.getString(Bed_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Bedroom", mData.getString(Bedroom_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Bathroom", mData.getString(Bathroom_DATA_KEY ), getKey(), -1));
        dest.add(new ReviewItem("Size", mData.getString(Size_DATA_KEY ), getKey(), -1));
        dest.add(new ReviewItem("Description", mData.getString(Descr_DATA_KEY), getKey(), -1));
    }
    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(Descr_DATA_KEY));
    }
}
