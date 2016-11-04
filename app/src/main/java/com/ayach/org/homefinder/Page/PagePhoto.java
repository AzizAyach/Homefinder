package com.ayach.org.homefinder.Page;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ayach.org.homefinder.Fragments.Fragment_Photo;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by aziz on 31/12/2015.
 */
public class PagePhoto extends Page {
    public static final String Photo_Logement = "photomake";
    public PagePhoto(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }
    @Override
    public Fragment createFragment() {
        return Fragment_Photo.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }
    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(Photo_Logement));
    }

}
