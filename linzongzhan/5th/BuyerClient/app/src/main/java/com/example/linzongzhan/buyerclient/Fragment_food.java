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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/24.
 */

public class Fragment_food extends Fragment {

    private static final String TAG = "Fragment_food";

    private List<foodStyle> foodStyleList = new ArrayList<>();

    private listview_food_adapter foodAdapter;

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Guide guide = CustomApplication.getGuide();
            if (guide == null) {

            } else {
                String order = guide.getOrder();


                if (order.equals("getFoodStyleList")) {
                    CustomApplication.pollGuide();
                    foodStyleList = guide.getFoodStyleList();
                    foodAdapter = new listview_food_adapter(getContext(),R.layout.listview_foodmessage,foodStyleList);
                    foodView.setAdapter(foodAdapter);
                }
            }
        }
    };

    private ListView foodView;

    private IntentFilter intentFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food,container,false);
      // ((InterfaceActivity)getActivity()).mServiceWay

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.get");
        getActivity().registerReceiver(myReceiver,intentFilter);

        foodAdapter = new listview_food_adapter(getContext(),R.layout.listview_foodmessage,foodStyleList);
        foodView = (ListView) view.findViewById(R.id.fragment_listview_food1);
        foodView.setAdapter(foodAdapter);

        foodView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foodStyle foodStyle = foodStyleList.get(i);
                final String foodname = foodStyle.getName();
                String[] items = {"增加","删除","修改"};
                AlertDialog.Builder listDialog = new AlertDialog.Builder(getContext());
                listDialog.setTitle(foodname);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                //增加
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogview_add,null);
                                final EditText add_foodname = (EditText) view.findViewById(R.id.add_foodname);
                                final EditText add_cost = (EditText) view.findViewById(R.id.add_cost);
                                final EditText add_price = (EditText) view.findViewById(R.id.add_price);
                                AlertDialog.Builder customizeDialog = new AlertDialog.Builder(getContext());
                          //      View dialogView_add = LayoutInflater.from(getContext()).inflate(R.layout.dialogview_add,null);
                                customizeDialog.setTitle("增加");
                                customizeDialog.setView(view);
                                customizeDialog.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //发送
                                                Command command2 = new Command();
                                                command2.setOrder("addFood");
                                                command2.setName(CustomApplication.getName());
                                                command2.setFoodStytle(add_foodname.getText().toString());
                                                command2.setCost(Integer.parseInt(add_cost.getText().toString()));
                                                command2.setPrice(Integer.parseInt(add_price.getText().toString()));
                                                ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command2);

                                            }
                                        }).show();

                                break;
                            case 1:
                                //删除
                                Command command2 = new Command();
                                command2.setOrder("deleteFood");
                                command2.setName(CustomApplication.getName());
                                command2.setFoodStytle(foodname);
                                ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command2);
                                break;
                            case 2:
                                //修改
                                String[] item = {"菜式名","成本","价格"};
                                AlertDialog.Builder listDialog1 = new AlertDialog.Builder(getContext());
                                listDialog1.setTitle("要修改的项");
                                listDialog1.setItems(item, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                //菜式名
                                                final EditText editText = new EditText(getContext());
                                                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                                                alertdialog.setTitle("请输入新菜式名").setView(editText);
                                                alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Command command3 = new Command();
                                                        command3.setName(CustomApplication.getName());
                                                        command3.setFoodProject("foodname");
                                                        command3.setResultString(editText.getText().toString());
                                                        command3.setOrder("changeFood");
                                                        command3.setFoodStytle(foodname);
                                                        ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command3);

                                                    }
                                                }).show();
                                                break;
                                            case 1:
                                                //成本
                                                final EditText editText1 = new EditText(getContext());
                                                AlertDialog.Builder alertdialog1 = new AlertDialog.Builder(getContext());
                                                alertdialog1.setTitle("请输入新成本").setView(editText1);
                                                alertdialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Command command3 = new Command();
                                                        command3.setName(CustomApplication.getName());
                                                        command3.setFoodProject("cost");
                                                        command3.setOrder("changeFood");
                                                        command3.setFoodStytle(foodname);
                                                        command3.setResultInt(Integer.parseInt(editText1.getText().toString()));
                                                        ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command3);

                                                    }
                                                }).show();
                                                break;
                                            case 2:
                                                //价格
                                                final EditText editText2 = new EditText(getContext());
                                                AlertDialog.Builder alertdialog2 = new AlertDialog.Builder(getContext());
                                                alertdialog2.setTitle("请输入新价格").setView(editText2);
                                                alertdialog2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Command command3 = new Command();
                                                        command3.setName(CustomApplication.getName());
                                                        command3.setFoodProject("price");
                                                        command3.setOrder("changeFood");
                                                        command3.setFoodStytle(foodname);
                                                        command3.setResultInt(Integer.parseInt(editText2.getText().toString()));
                                                        ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command3);

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
        });










        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
