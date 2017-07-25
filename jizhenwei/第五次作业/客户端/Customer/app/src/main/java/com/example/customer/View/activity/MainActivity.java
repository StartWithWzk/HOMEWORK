package com.example.customer.View.activity;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.customer.Control.Controler;
import com.example.customer.Model.Customer;
import com.example.customer.MyApplication;
import com.example.customer.R;
import com.example.customer.Util.Tool;
import com.example.customer.View.adpter.TabPageAdpater;
import com.example.customer.View.frag.MyOrderFrag;
import com.example.customer.View.frag.OrderFrag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Controler controler;
    TabLayout tabLayout;
    ViewPager viewPager;
    OrderFrag orderFrag;
    MyOrderFrag myOrderFrag;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        controler = Controler.getInstance();
    }

    private void initView() {
        getWindow().setStatusBarColor(0xffe54a19);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        orderFrag = new OrderFrag();
        myOrderFrag = new MyOrderFrag();
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_rice);
        adpatePage();
    }

    private void adpatePage() {
        fragmentList.add(orderFrag);
        fragmentList.add(myOrderFrag);
        viewPager.setAdapter(new TabPageAdpater(getSupportFragmentManager(), fragmentList, new String[]{"下单", "我的订单"}));
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
                    public void onResult(int feedback, Customer customer) {
                        Tool.toast("已注销");
                        finish();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    long start = 0;
    @Override
    public void onBackPressed() {
        switch (tabLayout.getSelectedTabPosition()) {
            //在viewpager左边
            case 0:
                switch (orderFrag.mode) {
                    case OrderFrag.MODE_SUPPLIER:
                        if (System.currentTimeMillis() - start > 2000) {
                            Tool.toast("再按一次退出");
                            start = System.currentTimeMillis();
                        } else {
                            controler.logoutInMe();
                            for (Activity activity : MyApplication.activityStack) {
                                activity.finish();
                            }
                        }
                        return;
                    case OrderFrag.MODE_DISH:
                        orderFrag.mode = OrderFrag.MODE_SUPPLIER;
                        orderFrag.onRefresh();
                        return;
                    default:
                        super.onBackPressed();
                        return;
                }
            case 1:
                if (System.currentTimeMillis() - start > 2000) {
                    Tool.toast("再按一次退出");
                    start = System.currentTimeMillis();
                } else {
                    controler.logoutInMe();
                    for (Activity activity : MyApplication.activityStack) {
                        activity.finish();
                    }
                }
                break;
            default:
                super.onBackPressed();
        }

    }

}
