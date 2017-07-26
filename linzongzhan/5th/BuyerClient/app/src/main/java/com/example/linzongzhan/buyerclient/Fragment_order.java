package com.example.linzongzhan.buyerclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.linzongzhan.buyerclient.R.layout.fragment_order;


/**
 * Created by linzongzhan on 2017/7/24.
 */

public class Fragment_order extends Fragment{

    private static final String TAG = "Fragment_order";
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Guide guide = CustomApplication.getGuide();
            if (guide == null) {

            } else {
                String order = guide.getOrder();

                Log.d(TAG,guide.getOrder());
                if (order.equals("getOrderList")) {
                    CustomApplication.pollGuide();
                    Log.d(TAG, "onReceive: 刷新lsitview");
                    CustomApplication.pollGuide();
                    orderMList = guide.getOderMList();
                    orderAdapter = new listview_order_adapter(getContext(),R.layout.listview_order,orderMList);
                    orderView.setAdapter(orderAdapter);

                }
            }
        }
    };

    private IntentFilter intentFilter;

    public  ListView orderView;

    public static listview_order_adapter orderAdapter;

    private List<OrderM> orderMList = new ArrayList<>();

 //   private LocalBroadcastManager broadcastManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_order,container,false);




    //    broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.get");
        getActivity().registerReceiver(myReceiver,intentFilter);







        orderAdapter = new listview_order_adapter(getContext(),R.layout.listview_order,orderMList);

        orderView = (ListView) view.findViewById(R.id.fragment_listview_order1);

        orderView.setAdapter(orderAdapter);

        orderView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrderM orderM = orderMList.get(i);
                final String time = orderM.getTime();
                //更改订单状态
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("提示");
                alert.setMessage("确定取消订单？");
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Command command2 = new Command();
                        command2.setName(CustomApplication.getName());
                        command2.setTime(time);
                        command2.setOrder("cancelOrder");
                        ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command2);
                        Toast.makeText(getContext(),"已取消",Toast.LENGTH_SHORT).show();

                        Command command1 = new Command();
                        command1.setOrder("getOrderList");
                        command1.setName(CustomApplication.getName());
                        command1.setGoal(CustomApplication.getName());
                        ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command1);

                        Command command = new Command();
                        command.setOrder("getSell");
                        command.setName(CustomApplication.getName());
                        command.setGoal(CustomApplication.getName());
                        ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command);
                    }
                }).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

  /*  private class MyBroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //改变view的逻辑
            Guide guide = app.getGuide();
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
            }
        }
    }*/


}
