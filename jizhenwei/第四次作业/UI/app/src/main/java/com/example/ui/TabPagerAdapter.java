package com.example.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/19.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    String[] titles;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabPagerAdapter(FragmentManager fm, List<Fragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
