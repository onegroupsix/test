package com.example.asus.burnnews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Asus on 2016/11/13.
 */

public class FPAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> arrayList;
    ArrayList<String> titles;

    public FPAdapter(FragmentManager fm, ArrayList<Fragment> arrayList,ArrayList<String> titles) {
        super(fm);
        this.arrayList = arrayList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
