package com.example.customer.View.adpter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.customer.Control.Controler;
import com.example.customer.Model.Dish;
import com.example.customer.Model.Order;
import com.example.customer.MyApplication;
import com.example.customer.R;
import com.example.customer.Util.Tool;
import com.example.customer.View.frag.MyOrderFrag;
import com.example.customer.View.frag.OrderFrag;

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
                Order order = list.get(holder.getAdapterPosition());
                if (order.getStatus() != Order.CANCEL_BY_CUSTOMER && order.getStatus() != Order.DONE) {
                    showDialog(order);
                }
            }
        });
        holder.ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnsureDialog(list.get(holder.getAdapterPosition()));
            }
        });
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRateDialog(list.get(holder.getAdapterPosition()));
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
                holder.ensure.setVisibility(View.GONE);
                holder.rate.setVisibility(View.GONE);
                holder.statusWait.setVisibility(View.VISIBLE);
                holder.statusDoing.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.GONE);
                break;
            case Order.DOING:
                holder.ensure.setVisibility(View.VISIBLE);
                holder.rate.setVisibility(View.GONE);
                holder.statusWait.setVisibility(View.GONE);
                holder.statusDoing.setVisibility(View.VISIBLE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.GONE);
                break;
            case Order.DONE:
                if (order.getIsRated() == 0) {
                    holder.rate.setVisibility(View.VISIBLE);
                } else {
                    holder.rate.setVisibility(View.GONE);
                }
                holder.ensure.setVisibility(View.GONE);
                holder.statusWait.setVisibility(View.GONE);
                holder.statusDoing.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.VISIBLE);
                holder.statusCancel.setVisibility(View.GONE);
                break;
            case Order.CANCEL_BY_CUSTOMER:
                holder.ensure.setVisibility(View.GONE);
                holder.rate.setVisibility(View.GONE);
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
        TextView supplier, customer, dish, date, statusWait, statusDoing, statusDone, statusCancel,ensure,rate;
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
            ensure = (TextView) itemView.findViewById(R.id.order_item_ensure);
            rate = (TextView) itemView.findViewById(R.id.order_item_rate);
            card = (CardView) itemView.findViewById(R.id.order_item_card);
        }
    }

    private void showDialog(final Order order) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.order_item, null, false);
        ((TextView) view.findViewById(R.id.order_item_supplier)).setText("Supplier: " + order.getSupplierName());
        ((TextView) view.findViewById(R.id.order_item_customer)).setText("Customer: " + order.getCustomerName());
        ((TextView) view.findViewById(R.id.order_item_date)).setText("Date: " + order.getDate());
        ((TextView) view.findViewById(R.id.order_item_dish)).setText("Dish: " + order.getDishName());
        dialog.setTitle("取消订单");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Controler.getInstance().cancelOrder(order, new Controler.OnOrderListener() {
                    @Override
                    public void onOrder() {
                        Tool.toast("已取消");
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
        dialog.setView(view);
        dialog.show();
    }

    private void showEnsureDialog(final Order order) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.order_item, null, false);
        ((TextView) view.findViewById(R.id.order_item_supplier)).setText("Supplier: " + order.getSupplierName());
        ((TextView) view.findViewById(R.id.order_item_customer)).setText("Customer: " + order.getCustomerName());
        ((TextView) view.findViewById(R.id.order_item_date)).setText("Date: " + order.getDate());
        ((TextView) view.findViewById(R.id.order_item_dish)).setText("Dish: " + order.getDishName());
        dialog.setTitle("确认收获");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Controler.getInstance().doneOrder(order, new Controler.OnOrderListener() {
                    @Override
                    public void onOrder() {
                        Tool.toast("已收货");
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
        dialog.setView(view);
        dialog.show();
    }
    private void showRateDialog(final Order order) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.rate, null, false);
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.grade);
        dialog.setTitle("评分");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                order.setStar(ratingBar.getNumStars());
                order.setIsRated(1);
                Controler.getInstance().gradeOrder(order, new Controler.OnOrderListener() {
                    @Override
                    public void onOrder() {
                        Tool.toast("已评分");
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
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
}
