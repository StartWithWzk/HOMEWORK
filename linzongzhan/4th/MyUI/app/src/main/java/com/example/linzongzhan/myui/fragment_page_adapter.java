package com.example.linzongzhan.myui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by linzongzhan on 2017/7/20.
 */

public class fragment_page_adapter extends FragmentPagerAdapter {

    private final int COUNT = 3;
    private String[] title = new String[] {"我的出借","我的出售","我的资料"};
    private Context context;

    public fragment_page_adapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new my_fragment();
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
