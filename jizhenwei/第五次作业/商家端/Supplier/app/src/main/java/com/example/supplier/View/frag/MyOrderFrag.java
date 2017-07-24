package com.example.supplier.View.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.supplier.Control.Controler;
import com.example.supplier.Model.Order;
import com.example.supplier.R;
import com.example.supplier.uitl.Tool;
import com.example.supplier.View.adapter.OrderAdapter;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class MyOrderFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.frag_refresh_recycler,container,false);
        recyclerView = (RecyclerView) content.findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout) content.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Tool.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        onRefresh();
                    }
                });
            }
        }).start();

        return content;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        Tool.log("刷新中");
        Controler.getInstance().getOrderList(new Controler.OnGetOrderListListener() {
            @Override
            public void onGet(final List<Order> list) {
                Tool.log("orderList",list);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(new OrderAdapter(getContext(),list,MyOrderFrag.this));
                        refreshLayout.setRefreshing(false);
                    }
                });

            }
        });
    }

}
