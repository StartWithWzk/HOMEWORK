package com.example.supplier.View.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class TabPageAdpater extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    String[] titles;

    public TabPageAdpater(FragmentManager fm) {
        super(fm);
    }

    public TabPageAdpater(FragmentManager fm, List<Fragment> list, String[] titles) {
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
