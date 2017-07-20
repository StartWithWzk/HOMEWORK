package com.example.qgui.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qgui.R;
import com.example.qgui.base.TopbarBaseActivity;
import com.example.qgui.view.BottomTabView;

import java.util.ArrayList;
import java.util.List;


public class MyInfoActivity extends TopbarBaseActivity {

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitles(R.string.homepage);

        setTopLeftButton(R.drawable.ic_green_arrow, new OnClickListener() {
            @Override
            public void onClick() {

                Intent intent = new Intent(MyInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setTopRightButton("button", R.drawable.ic_green_chat, new OnClickListener() {
            @Override
            public void onClick() {
                    Toast.makeText(MyInfoActivity.this, "点击了菜单", Toast.LENGTH_SHORT).show();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bottomTabView = (BottomTabView) findViewById(R.id.bottomTabView);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }

            @Override
            public int getCount() {
                return getFragments().size();
            }
        };

        viewPager.setAdapter(adapter);

        if (getCenterView() == null){
            bottomTabView.setTabItemViews(getTabViews());
        }else {
            bottomTabView.setTabItemViews(getTabViews(), getCenterView());
        }

        bottomTabView.setUpWithViewPager(viewPager);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_base_bottom_tab;
    }

    ViewPager viewPager;
    BottomTabView bottomTabView;
    FragmentPagerAdapter adapter;


    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this, "书城", R.color.colorDefault,
                R.color.colorBule, R.drawable.ic_book, R.drawable.ic_bule_book));
        tabItemViews.add(new BottomTabView.TabItemView(this, "发现", R.color.colorDefault,
                R.color.colorBule, R.drawable.ic_find, R.drawable.ic_bule_find));
        tabItemViews.add(new BottomTabView.TabItemView(this, "购物车", R.color.colorDefault,
                R.color.colorBule, R.drawable.ic_shop, R.drawable.ic_bule_shop));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的", R.color.colorDefault,
                R.color.colorBule, R.drawable.ic_my, R.drawable.ic_bule_me));
        return tabItemViews;
    }


    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyInfoFragment());
        fragments.add(new MyInfoFragment());
        fragments.add(new MyInfoFragment());
        fragments.add(new MyInfoFragment());
        return fragments;
    }


    protected View getCenterView() {
        ImageView centerView = new ImageView(this);
        centerView.setImageResource(R.drawable.ic_add);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(180, 180);
        layoutParams.leftMargin = 60;
        layoutParams.rightMargin = 60;
        layoutParams.bottomMargin = 50;
        centerView.setLayoutParams(layoutParams);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyInfoActivity.this, "centerView 点击了", Toast.LENGTH_SHORT).show();
            }
        });
        return centerView;
    }
}
