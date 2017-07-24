package com.example.customer.View.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customer.Control.Controler;
import com.example.customer.Model.Dish;
import com.example.customer.Model.Order;
import com.example.customer.Model.Supplier;
import com.example.customer.MyApplication;
import com.example.customer.R;
import com.example.customer.Util.Tool;
import com.example.customer.View.adpter.DishAdapter;
import com.example.customer.View.adpter.OrderAdapter;
import com.example.customer.View.adpter.SupplierAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class OrderFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    public static final int MODE_SUPPLIER = 0;
    public static final int MODE_DISH = 1;
    public int mode = MODE_SUPPLIER;
    public Supplier supplierSelected;
    public Dish dishSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.frag_refresh_recycler, container, false);
        recyclerView = (RecyclerView) content.findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout) content.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        onRefresh();
        return content;
    }

    @Override
    public void onRefresh() {
        Tool.log("刷新中");
        refreshLayout.setRefreshing(true);
        switch (mode) {
            case MODE_SUPPLIER:
                Controler.getInstance().getSupplierList(new Controler.OnGetSupplierListListener() {
                    @Override
                    public void onGet(final List<Supplier> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(llm);
                                recyclerView.setAdapter(new SupplierAdapter(getContext(), list,OrderFrag.this));
                                refreshLayout.setRefreshing(false);
                            }
                        });

                    }
                });
                break;
            case MODE_DISH:
                Controler.getInstance().getDishList(supplierSelected, new Controler.OnGetDishListListener() {
                    @Override
                    public void onGet(final List<Dish> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(llm);
                                recyclerView.setAdapter(new DishAdapter(getContext(),list,OrderFrag.this));
                                refreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
                break;
        }


    }


}
