package com.github.dilyar85.violetdroid.adapter;
/**
 * Created by Dilyar on 11/2/16.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.dilyar85.violetdroid.CanvasFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String LOG_TAG = ViewPagerAdapter.class.getSimpleName();

    private Context mContext;


    public ViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {


        Fragment fragment = new CanvasFragment();
        return fragment;
    }

    @Override
    public int getCount() {

        //Temporally return 3 pages
        return 3;
    }


    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }



}
