package com.example.kolot.myweatherapp.activity.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by kolot on 05.04.2018.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private ArrayList<String> fragmentsTitlesList = new ArrayList<>();

    public void addFragment(Fragment fragment, String s){
        fragmentsList.add(fragment);
        fragmentsTitlesList.add(s);
    }

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CharSequence getPageTitle(int position){
        return fragmentsTitlesList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
