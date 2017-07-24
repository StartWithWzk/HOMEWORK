package com.example.supplier.View.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.supplier.Control.Controler;
import com.example.supplier.Model.MyService;
import com.example.supplier.Model.Supplier;
import com.example.supplier.R;
import com.example.supplier.View.adapter.TabPageAdpater;
import com.example.supplier.View.frag.DishFrag;
import com.example.supplier.View.frag.MyOrderFrag;
import com.example.supplier.uitl.Tool;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Controler controler;
    TabLayout tabLayout;
    ViewPager viewPager;
    DishFrag dishFrag;
    MyOrderFrag myOrderFrag;
    List<Fragment> fragmentList = new ArrayList<>();
    FloatingActionButton plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        controler = Controler.getInstance();
        //启动订单提醒活动
        startService(new Intent(this, MyService.class));
    }
    private void initView() {
        getWindow().setStatusBarColor(0xffe54a19);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        plus = (FloatingActionButton) findViewById(R.id.plus);
        plus.setOnClickListener(this);
        dishFrag = new DishFrag();
        myOrderFrag = new MyOrderFrag();
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_rice);
        adpatePage();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    Tool.log("orderFrag.onRefresh");
                    plus.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.show));
                } else {
                    Tool.log("myOrderFrag.onRefresh");
                    plus.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.hide));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void adpatePage() {
        fragmentList.add(dishFrag);
        fragmentList.add(myOrderFrag);
        viewPager.setAdapter(new TabPageAdpater(getSupportFragmentManager(), fragmentList, new String[]{"我的菜式", "我的订单"}));
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                controler.logout(new Controler.OnUserStatusListener() {
                    @Override
                    public void onResult(int feedback, Supplier supplier) {
                        Tool.toast("已注销");
                        finish();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,MyService.class));
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        dishFrag.showAddDishDialog();
    }
}
