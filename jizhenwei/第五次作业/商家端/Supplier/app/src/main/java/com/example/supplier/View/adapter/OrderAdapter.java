package com.example.supplier.View.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.supplier.Control.Controler;
import com.example.supplier.Model.Dish;
import com.example.supplier.Model.Order;
import com.example.supplier.MyApplication;
import com.example.supplier.R;
import com.example.supplier.uitl.Tool;
import com.example.supplier.View.frag.MyOrderFrag;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/23.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<Order> list;
    MyOrderFrag frag;

    public OrderAdapter(Context context, List<Order> list, MyOrderFrag frag) {
        this.context = context;
        this.list = list;
        this.frag = frag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(holder.getAdapterPosition()).getStatus() == Order.WAIT) {
                    showDialog(list.get(holder.getAdapterPosition()));
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = list.get(position);
        holder.supplier.setText("Supplier: " + order.getSupplierName());
        holder.customer.setText("Customer: " + order.getCustomerName());
        holder.dish.setText("Dish: " + order.getDishName());
        holder.date.setText("Date: " + order.getDate());
        switch (order.getStatus()) {
            case Order.WAIT:
                holder.statusWait.setVisibility(View.VISIBLE);
                holder.statusDoing.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.GONE);
                break;
            case Order.DOING:
                holder.statusWait.setVisibility(View.GONE);
                holder.statusDoing.setVisibility(View.VISIBLE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.GONE);
                break;
            case Order.DONE:
                holder.statusWait.setVisibility(View.GONE);
                holder.statusDoing.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.VISIBLE);
                holder.statusCancel.setVisibility(View.GONE);
                break;
            case Order.CANCEL_BY_CUSTOMER:
                holder.statusWait.setVisibility(View.GONE);
                holder.statusDoing.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView supplier, customer, dish, date, statusWait, statusDoing, statusDone, statusCancel;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            supplier = (TextView) itemView.findViewById(R.id.order_item_supplier);
            customer = (TextView) itemView.findViewById(R.id.order_item_customer);
            date = (TextView) itemView.findViewById(R.id.order_item_date);
            dish = (TextView) itemView.findViewById(R.id.order_item_dish);
            statusWait = (TextView) itemView.findViewById(R.id.order_item_status_wait);
            statusDoing = (TextView) itemView.findViewById(R.id.order_item_status_doing);
            statusDone = (TextView) itemView.findViewById(R.id.order_item_status_done);
            statusCancel = (TextView) itemView.findViewById(R.id.order_item_status_cancel);
            card = (CardView) itemView.findViewById(R.id.order_item_card);

        }

    }

    private void showDialog(final Order order) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("接受订单");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Controler.getInstance().acceptOrder(order, new Controler.OnSuccessListener() {
                    @Override
                    public void onSuccess() {
                        Tool.toast("已接单");
                        Tool.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                frag.onRefresh();
                            }
                        });

                    }
                });
            }
        });
        dialog.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.order_item, null, false);
        ((TextView) view.findViewById(R.id.order_item_supplier)).setText("Supplier: " + order.getSupplierName());
        ((TextView) view.findViewById(R.id.order_item_customer)).setText("Customer: " + order.getCustomerName());
        ((TextView) view.findViewById(R.id.order_item_date)).setText("Date: " + order.getDate());
        ((TextView) view.findViewById(R.id.order_item_dish)).setText("Dish: " + order.getDishName());

        dialog.setView(view);
        dialog.show();
    }
}
