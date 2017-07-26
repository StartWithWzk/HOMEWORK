package com.example.linzongzhan.buyerclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class InterfaceActivity extends BaseActivity {

    private static final String TAG = "InterfaceActivity";

    private CustomApplication app;

    public MyService.ServiceWay mServiceWay;

    private IntentFilter intentFilter;

//    private InterfaceActivity.MyBroadcastReceiver myBroadcastReceiver;

    private TabLayout tabLayout;

    private ViewPager viewPager;


    private ListView foodView;

    private List<OrderM> orderMList = new ArrayList<>();

    private List<foodStyle> foodStyleList;


    listview_food_adapter foodAdapter;


    private Fragment_order fragment_order;

    private Fragment_seller fragment_seller;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceWay = (MyService.ServiceWay) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.interface_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:

                Command command1 = new Command();
                command1.setOrder("getOrderList");
                command1.setName(CustomApplication.getName());
                command1.setGoal(CustomApplication.getName());
                mServiceWay.sendMessage(command1);

                Command command = new Command();
                command.setOrder("getSell");
                command.setName(app.getName());
                command.setGoal(app.getName());
                mServiceWay.sendMessage(command);

                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfaceactivity_layout);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        Intent serviceIntent = new Intent(this,MyService.class);
        startService (serviceIntent);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);

        app = (CustomApplication) getApplication();

        initViewPager();


    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private void initViewPager () {
        List<String> titleList = new ArrayList<>();
        titleList.add("我的订单");
        titleList.add("商家");

        for (int i = 0;i < titleList.size();i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titleList.get(i)));
        }

        List<Fragment> fragmentList = new ArrayList<>();
        //添加Fragment

        fragment_order = new Fragment_order();
        fragment_seller = new Fragment_seller();
        fragmentList.add(fragment_order);
        fragmentList.add(fragment_seller);

        FragmentAdater fragmentAdater = new FragmentAdater(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(fragmentAdater);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        unbindService(connection);

    //    unregisterReceiver(myBroadcastReceiver);
    }

}
