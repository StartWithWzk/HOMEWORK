package com.example.supplier.View.frag;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.supplier.Control.Controler;
import com.example.supplier.Model.Dish;
import com.example.supplier.Model.Order;
import com.example.supplier.MyApplication;
import com.example.supplier.R;
import com.example.supplier.View.adapter.DishAdapter;
import com.example.supplier.uitl.Tool;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/23.
 */

public class DishFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    List<Dish> mList;
    public int postion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.frag_refresh_recycler, container, false);
        recyclerView = (RecyclerView) content.findViewById(R.id.recycler);
        registerForContextMenu(recyclerView);
        refreshLayout = (SwipeRefreshLayout) content.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        onRefresh();
        return content;
    }

    @Override
    public void onRefresh() {
        Tool.log("刷新中");
        refreshLayout.setRefreshing(true);
        Controler.getInstance().getDishList(Tool.getUser(), new Controler.OnGetDishListListener() {
            @Override
            public void onGet(final List<Dish> list) {
                Tool.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mList = list;
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(new DishAdapter(getContext(), list, DishFrag.this));
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //修改
                showSetDishDialog(mList.get(postion));
                break;
            case 2:
                //删除
                Controler.getInstance().deleteDish(mList.get(postion), new Controler.OnSuccessListener() {
                    @Override
                    public void onSuccess() {
                        Tool.toast("成功删除");
                        Tool.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onRefresh();
                            }
                        });
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showSetDishDialog(final Dish dish) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.set_dish_window, null, false);
        final EditText name, cost, price;
        name = (EditText) view.findViewById(R.id.dish_name_et);
        cost = (EditText) view.findViewById(R.id.dish_cost_et);
        price = (EditText) view.findViewById(R.id.dish_price_et);
        name.setText(dish.getName());
        cost.setText(dish.getCost() + "");
        price.setText(dish.getSellingPrice() + "");
        dialog.setTitle("填写菜式信息");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String nameStr = name.getText().toString();
                String costStr = cost.getText().toString();
                String priceStr = price.getText().toString();
                if (nameStr.equals("") || costStr.equals("") || priceStr.equals("")) {
                    Tool.toast("请填写完整信息");
                    return;
                } else {

                    dish.setName(nameStr);
                    dish.setCost(Double.parseDouble(costStr));
                    dish.setSellingPrice(Double.parseDouble(priceStr));
                    Controler.getInstance().setDish(dish, new Controler.OnSuccessListener() {
                        @Override
                        public void onSuccess() {
                            Tool.toast("修改成功");
                            Tool.runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       dialog.dismiss();
                                                       onRefresh();
                                                   }
                                               }
                            );
                        }
                    });
                }
            }
        });
        dialog.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }
    public void showAddDishDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.set_dish_window, null, false);
        final EditText name, cost, price;
        name = (EditText) view.findViewById(R.id.dish_name_et);
        cost = (EditText) view.findViewById(R.id.dish_cost_et);
        price = (EditText) view.findViewById(R.id.dish_price_et);
        dialog.setTitle("填写菜式信息");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String nameStr = name.getText().toString();
                String costStr = cost.getText().toString();
                String priceStr = price.getText().toString();
                if (nameStr.equals("") || costStr.equals("") || priceStr.equals("")) {
                    Tool.toast("请填写完整信息");
                    return;
                } else {
                    Dish dish = new Dish();
                    dish.setName(nameStr);
                    dish.setCost(Double.parseDouble(costStr));
                    dish.setSellingPrice(Double.parseDouble(priceStr));
                    dish.setSupplierId(Tool.getUser().getObjectId());
                    Controler.getInstance().newDish(dish, new Controler.OnSuccessListener() {
                        @Override
                        public void onSuccess() {
                            Tool.toast("修改成功");
                            Tool.runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       dialog.dismiss();
                                                       onRefresh();
                                                   }
                                               }
                            );
                        }
                    });
                }
            }
        });
        dialog.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }

}
