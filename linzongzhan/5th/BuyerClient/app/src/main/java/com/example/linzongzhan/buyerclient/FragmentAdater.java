package com.example.linzongzhan.buyerclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class FragmentAdater extends FragmentStatePagerAdapter {

    private List<String> titleList = new ArrayList<>();

    private List<Fragment> fragmentList = new ArrayList<>();

    public FragmentAdater (FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
