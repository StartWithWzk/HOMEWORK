package com.example.linzongzhan.buyerclient;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerActivity extends AppCompatActivity {

    private static final String TAG = "SellerActivity";

    private List<foodStyle> foodStyleList = new ArrayList<>();

    private ListView foodListView;

    private listview_food_adapter foodAdapter;

    private String sellername;

    public MyService.ServiceWay mServiceWay;

    private IntentFilter intentFilter;

    private MyBroadcastReceiver myBroadcastReceiver;

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.selleractivity_layout);

        Intent intent = getIntent();
        sellername = intent.getStringExtra("sellname");

        foodAdapter = new listview_food_adapter(SellerActivity.this,R.layout.listview_foodmessage,foodStyleList);
        foodListView = (ListView) findViewById(R.id.selleractivity_listview);
        foodListView.setAdapter(foodAdapter);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.get");
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver,intentFilter);


        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                foodStyle foodStyle = foodStyleList.get(i);
                final String foodname = foodStyle.getName();

                AlertDialog.Builder alert = new AlertDialog.Builder(SellerActivity.this);
                alert.setTitle("提示");
                alert.setMessage("确定下单？");
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Command command = new Command();
                        command.setName(CustomApplication.getName());
                        command.setGoal(sellername);
                        command.setFoodStytle(foodname);

                        Date now = new Date();
                        DateFormat d = DateFormat.getDateTimeInstance();
                        String time = d.format(now);

                     /*   SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SS E");
                        String time = format.format(now); */

                        command.setTime(time);
                        command.setOrder("placeAnOrder");
                        mServiceWay.sendMessage(command);

                        Command command1 = new Command();
                        command1.setOrder("getOrderList");
                        command1.setName(CustomApplication.getName());
                        command1.setGoal(CustomApplication.getName());
                        mServiceWay.sendMessage(command1);

                        Command command2 = new Command();
                        command2.setOrder("getSell");
                        command2.setName(CustomApplication.getName());
                        command2.setGoal(CustomApplication.getName());
                        mServiceWay.sendMessage(command);

                        Toast.makeText(SellerActivity.this,"下单成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).show();
            }
        });


        Intent serviceIntent = new Intent(this,MyService.class);
        startService (serviceIntent);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unregisterReceiver(myBroadcastReceiver);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.interface_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:

                Command command = new Command();
                command.setName(CustomApplication.getName());
                command.setOrder("getFoodStyle");
                command.setGoal(sellername);
                mServiceWay.sendMessage(command);

                break;
        }
        return true;
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {


            Guide guide = CustomApplication.getGuide();
            if (guide == null) {

            } else {
                String order = guide.getOrder();
                if (order.equals("getFoodStyleList")) {
                    CustomApplication.pollGuide();

             /*       Gson gson = new Gson();
                    String line = gson.toJson(guide);
                    Log.d(TAG, line); */

                    foodStyleList = guide.getFoodStyleList();

                    foodAdapter = new listview_food_adapter(SellerActivity.this,R.layout.listview_foodmessage,foodStyleList);
                    foodListView.setAdapter(foodAdapter);
                }
            }
        }
    }

}
