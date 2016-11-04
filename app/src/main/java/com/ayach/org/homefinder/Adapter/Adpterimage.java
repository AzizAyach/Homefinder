package com.ayach.org.homefinder.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ayach.org.homefinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aziz on 17/12/2015.
 */
public class Adpterimage extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> image =new ArrayList<>();
    public Adpterimage(Context mContext,ArrayList<String>image){
        this.mContext=mContext;
        this.image=image;

    }
    @Override
    public int getCount() {
        return image.size();
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((LinearLayout)object);
    }


    @Override
    public Parcelable saveState() {
        return null;
    }

@Override
public Object instantiateItem(View collection, int position) {
    LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.imagescrollview,null);
    ((ViewPager) collection).addView(view);
    final ImageView img = (ImageView) view.findViewById(R.id.img);
    Picasso.with(mContext).load(image.get(position)).placeholder(R.drawable.noimage).centerCrop().fit().into(img);
    return view;
}

}

