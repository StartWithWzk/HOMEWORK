package com.example.linzongzhan.sellerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

//    private ListView orderView ;

    private ListView foodView;

    private List<OrderM> orderMList = new ArrayList<>();

    private List<foodStyle> foodStyleList;

 //   listview_order_adapter orderAdapter;

    listview_food_adapter foodAdapter;

    private View MoreView1;

    private View MoreView2;

    private Fragment_order fragment_order;

    private Fragment_food fragment_food;


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
                command1.setName(app.getName());
                command1.setGoal(app.getName());
                mServiceWay.sendMessage(command1);

                Command command = new Command();
                command.setOrder("getFoodStyle");
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

      //  intentFilter = new IntentFilter();
     //   intentFilter.addAction("com.example.broadcasttext.get");
     //   myBroadcastReceiver = new InterfaceActivity.MyBroadcastReceiver();
     //   registerReceiver(myBroadcastReceiver,intentFilter);

        app = (CustomApplication) getApplication();

        initViewPager();

    /*    Command command = new Command();
        command.setOrder("getFoodStyle");
        command.setName(app.getName());
        command.setGoal(app.getName());
        mServiceWay.sendMessage(command);

        Command command1 = new Command();
        command1.setOrder("getOrderList");
        command1.setName(app.getName());
        command1.setGoal(app.getName());
        mServiceWay.sendMessage(command1);*/

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                Command command = new Command();
                command.setOrder("getFoodStyle");
                command.setName(app.getName());
                command.setGoal(app.getName());
                mServiceWay.sendMessage(command);

                Command command1 = new Command();
                command1.setOrder("getOrderList");
                command1.setName(app.getName());
                command1.setGoal(app.getName());
                mServiceWay.sendMessage(command1);

                Log.d(TAG, "onReceive: 8888888888888888888888888888888888888888888888888888888888");
            }
        });*/

        MoreView1 = LayoutInflater.from(this).inflate(R.layout.fragment_order,null);
        MoreView2 = LayoutInflater.from(this).inflate(R.layout.fragment_food,null);//View.inflate(this,R.layout.fragment_food,null);

        //orderView = (ListView) MoreView1.findViewById(R.id.fragment_listview_order1);
        foodView = (ListView) MoreView2.findViewById(R.id.fragment_listview_food1);
     //   orderAdapter = new listview_order_adapter(InterfaceActivity.this,R.layout.listview_order,orderMList);
        foodAdapter = new listview_food_adapter(InterfaceActivity.this,R.layout.listview_foodmessage,foodStyleList);

        //fragment_order.orderView.setAdapter(orderAdapter);
        //foodView.setAdapter(foodAdapter);
        

/*        fragment_order.orderView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrderM orderM = orderMList.get(i);
                String time = orderM.getTime();
                //更改订单状态
                Command command2 = new Command();
                command2.setName(app.getName());
                command2.setTime(time);
                command2.setOrder("receiveOrder");
                mServiceWay.sendMessage(command2);
            }
        });  */

   /*   foodView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foodStyle foodStyle = foodStyleList.get(i);
                final String foodname = foodStyle.getName();
                String[] items = {"增加","删除","修改"};
                AlertDialog.Builder listDialog = new AlertDialog.Builder(InterfaceActivity.this);
                listDialog.setTitle(foodname);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                //增加
                                final EditText add_foodname = (EditText) findViewById(R.id.add_foodname);
                                final EditText add_cost = (EditText) findViewById(R.id.add_cost);
                                final EditText add_price = (EditText) findViewById(R.id.add_price);
                                AlertDialog.Builder customizeDialog = new AlertDialog.Builder(InterfaceActivity.this);
                                View dialogView_add = LayoutInflater.from(InterfaceActivity.this).inflate(R.layout.dialogview_add,null);
                                customizeDialog.setTitle("增加");
                                customizeDialog.setView(dialogView_add);
                                customizeDialog.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //发送
                                                Command command2 = new Command();
                                                command2.setOrder("addFood");
                                                command2.setName(app.getName());
                                                command2.setFoodStytle(add_foodname.getText().toString());
                                                command2.setCost(Integer.parseInt(add_cost.getText().toString()));
                                                command2.setPrice(Integer.parseInt(add_price.getText().toString()));
                                                mServiceWay.sendMessage(command2);

                                            }
                                        }).show();

                                break;
                            case 1:
                                //删除
                                Command command2 = new Command();
                                command2.setOrder("deleteFood");
                                command2.setName(app.getName());
                                command2.setName(foodname);
                                mServiceWay.sendMessage(command2);
                                break;
                            case 2:
                                //修改
                                String[] item = {"菜式名","成本","价格"};
                                AlertDialog.Builder listDialog1 = new AlertDialog.Builder(InterfaceActivity.this);
                                listDialog1.setTitle("要修改的项");
                                listDialog1.setItems(item, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                //菜式名
                                                final EditText editText = new EditText(InterfaceActivity.this);
                                                AlertDialog.Builder alertdialog = new AlertDialog.Builder(InterfaceActivity.this);
                                                alertdialog.setTitle("请输入新菜式名").setView(editText);
                                                alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Command command3 = new Command();
                                                        command3.setName(app.getName());
                                                        command3.setFoodProject("foodname");
                                                        command3.setResultString(editText.getText().toString());
                                                        mServiceWay.sendMessage(command3);

                                                    }
                                                }).show();
                                                break;
                                            case 1:
                                                //成本
                                                final EditText editText1 = new EditText(InterfaceActivity.this);
                                                AlertDialog.Builder alertdialog1 = new AlertDialog.Builder(InterfaceActivity.this);
                                                alertdialog1.setTitle("请输入新成本").setView(editText1);
                                                alertdialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Command command3 = new Command();
                                                        command3.setName(app.getName());
                                                        command3.setFoodProject("cost");
                                                        command3.setResultInt(Integer.parseInt(editText1.getText().toString()));
                                                        mServiceWay.sendMessage(command3);

                                                    }
                                                }).show();
                                                break;
                                            case 2:
                                                //价格
                                                final EditText editText2 = new EditText(InterfaceActivity.this);
                                                AlertDialog.Builder alertdialog2 = new AlertDialog.Builder(InterfaceActivity.this);
                                                alertdialog2.setTitle("请输入新价格").setView(editText2);
                                                alertdialog2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Command command3 = new Command();
                                                        command3.setName(app.getName());
                                                        command3.setFoodProject("price");
                                                        command3.setResultInt(Integer.parseInt(editText2.getText().toString()));
                                                        mServiceWay.sendMessage(command3);

                                                    }
                                                }).show();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }).show();

                                break;
                            default:
                                break;
                        }
                    }
                }).show();
            }
        }); */






    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private void initViewPager () {
        List<String> titleList = new ArrayList<>();
        titleList.add("订单列表");
        titleList.add("我的菜式");

        for (int i = 0;i < titleList.size();i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titleList.get(i)));
        }

        List<Fragment> fragmentList = new ArrayList<>();
        //添加Fragment

        fragment_order = new Fragment_order();
        fragment_food = new Fragment_food();
        fragmentList.add(fragment_order);
        fragmentList.add(fragment_food);

        FragmentAdater fragmentAdater = new FragmentAdater(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(fragmentAdater);
        tabLayout.setupWithViewPager(viewPager);
       // tabLayout.setTabsFromPagerAdapter(fragmentAdater);

    }







    @Override
    protected void onDestroy() {

        super.onDestroy();

        unbindService(connection);

    //    unregisterReceiver(myBroadcastReceiver);
    }



    private class MyBroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //改变view的逻辑
         /*   Guide guide = app.getGuide();
            String order = guide.getOrder();

            if (order.equals("getFoodStyleList")) {
                app.pollGuide();
                foodStyleList = guide.getFoodStyleList();
                foodAdapter = new listview_food_adapter(InterfaceActivity.this,R.layout.listview_foodmessage,foodStyleList);
                foodAdapter.notifyDataSetChanged();
            }
            if (order.equals("getOrderList")) {
                app.pollGuide();
                orderMList = guide.getOderMList();
                Fragment_order.orderAdapter = new listview_order_adapter(InterfaceActivity.this,R.layout.listview_order,orderMList);
                Fragment_order.orderAdapter.notifyDataSetChanged();
            }*/

         Guide guide = app.getGuide();
         if (guide == null) {

         } else {
             String order = guide.getOrder();
             if (order.equals("newOrder")) {
                 Toast.makeText(InterfaceActivity.this,"你有一个新订单",Toast.LENGTH_SHORT);
             }
         }
        }
    }


}
