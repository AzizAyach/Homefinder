package com.ayach.org.homefinder.Page;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ayach.org.homefinder.Fragments.Logement_Map;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by aziz on 31/12/2015.
 */
public class PageMap extends Page {
    public static final String Map_Logement = "Map";

    public PageMap(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }
    @Override
    public Fragment createFragment() {
        return Logement_Map.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }
    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(Map_Logement));
    }
}
