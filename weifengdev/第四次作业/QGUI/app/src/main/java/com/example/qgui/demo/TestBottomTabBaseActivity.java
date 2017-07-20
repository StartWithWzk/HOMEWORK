package com.example.qgui.demo;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qgui.R;
import com.example.qgui.base.BottomTabBaseActivity;
import com.example.qgui.view.BottomTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈序员 on 2017/4/27.
 * Email: Matthew_Chen_1994@163.com
 * Blog: https://blog.ifmvo.cn
 */

public class TestBottomTabBaseActivity extends BottomTabBaseActivity {

    @Override
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

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        return fragments;
    }

    @Override
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
                Toast.makeText(TestBottomTabBaseActivity.this, "centerView 点击了", Toast.LENGTH_SHORT).show();
            }
        });
        return centerView;
    }
}
