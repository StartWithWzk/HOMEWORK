package com.example.linzongzhan.buyerclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/25.
 */

public class Fragment_seller extends Fragment {

    private static final String TAG = "Fragment_seller";

    private List<String> sellList = new ArrayList<>();

    private listview_seller_adapter sellAdapter;

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Guide guide = CustomApplication.getGuide();
            if (guide == null) {

            } else {
                String order = guide.getOrder();
                if (order.equals("getSellList")) {
                    CustomApplication.pollGuide();
                    sellList = guide.getSellList();
                    sellAdapter = new listview_seller_adapter(getContext(),R.layout.listview_seller,sellList);
                    sellView.setAdapter(sellAdapter);
                }
            }
        }
    };

    private ListView sellView;

    private IntentFilter intentFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller,container,false);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.get");
        getActivity().registerReceiver(myReceiver,intentFilter);

        sellAdapter = new listview_seller_adapter(getContext(),R.layout.listview_seller,sellList);
        sellView = (ListView) view.findViewById(R.id.fragment_listview_seller);
        sellView.setAdapter(sellAdapter);

        sellView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = sellList.get(i);
                Intent intent = new Intent(getContext(),SellerActivity.class);
                intent.putExtra("sellname",name);
                startActivity(intent);

                String sellername = sellList.get(i);
                Command command = new Command();
                command.setName(CustomApplication.getName());
                command.setOrder("getFoodStyle");
                command.setGoal(sellername);
                ((InterfaceActivity)getActivity()).mServiceWay.sendMessage(command);

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
