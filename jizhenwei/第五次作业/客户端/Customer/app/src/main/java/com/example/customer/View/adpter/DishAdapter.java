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
import com.example.customer.R;
import com.example.customer.Util.Tool;
import com.example.customer.View.frag.OrderFrag;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/23.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {
    Context context;
    List<Dish> list;
    OrderFrag frag;

    public DishAdapter(Context context, List<Dish> list, OrderFrag frag) {
        this.context = context;
        this.list = list;
        this.frag = frag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dish dish = list.get(position);
        holder.dishName.setText("Dish: " + dish.getName());
        holder.dishPrice.setText("Price: " + dish.getSellingPrice());
        float grade;
        if (dish.getGradeCount() == 0) {
            grade = 0;
        } else {
            grade = (float) dish.getGradeSum() / (float) dish.getGradeCount();
        }
        holder.ratingBar.setRating(grade);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, dishPrice;
        RatingBar ratingBar;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            dishName = (TextView) itemView.findViewById(R.id.dish_item_name);
            dishPrice = (TextView) itemView.findViewById(R.id.dish_item_sellingprice);
            card = (CardView) itemView.findViewById(R.id.dish_item_card);
            ratingBar = (RatingBar) itemView.findViewById(R.id.dish_item_ratingbar);
        }
    }

    private void showDialog(final Dish dish) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("确认订单");
        dialog.setMessage("Dish: " + dish.getName() + "\n" + "Price: " + dish.getSellingPrice());
        dialog.setCancelable(false);
        dialog.setPositiveButton("下单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Controler.getInstance().order(dish, new Controler.OnOrderListener() {
                    @Override
                    public void onOrder() {
                        Tool.toast("已下单");
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
        View view = LayoutInflater.from(context).inflate(R.layout.dish_item, null, false);
        float grade;
        if (dish.getGradeCount() == 0) {
            grade = 0;
        } else {
            grade = (float) dish.getGradeSum() / (float) dish.getGradeCount();
        }
        ((TextView) view.findViewById(R.id.dish_item_name)).setText(dish.getName());
        ((TextView) view.findViewById(R.id.dish_item_sellingprice)).setText(dish.getSellingPrice() + "");
        ((RatingBar) view.findViewById(R.id.dish_item_ratingbar)).setRating(grade);
        dialog.setView(view);
        dialog.show();
    }
}
