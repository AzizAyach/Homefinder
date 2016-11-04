package com.ayach.org.homefinder.Page;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ayach.org.homefinder.Fragments.TitileFragment;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by aziz on 31/12/2015.
 */
public class PageName extends Page {

    public static final String Name_Logement = "logement";


    public PageName(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }


    @Override
    public Fragment createFragment() {
        return TitileFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Name of Your House", mData.getString(Name_Logement ), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(Name_Logement));
    }
}
