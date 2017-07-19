package com.example.ui;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyDetail extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);
        initView();
    }










    private void initView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        toolbar = (Toolbar) findViewById(R.id.person_tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.person_coll_tool_bar);
        collapsingToolbarLayout.setTitle("林志玲");
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        list.add(new BookListFrag());
        list.add(new BookListFrag());
        list.add(new BookListFrag());

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),list,new String[]{"我的出借","我的出售","我的资料"}));
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        menu.findItem(R.id.option).setIcon(R.drawable.ic_text_white);
        return true;
    }
    @Override
    public void onClick(View v) {

    }
}
