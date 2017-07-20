package com.example.qgui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.qgui.demo.PageFragment;

/**
 * Created by 黄伟烽 on 2017/7/19.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String[] tabTitles = new String[]{"我的出借","我的出售","我的资料"};
    private Context mContext;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new PageFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
