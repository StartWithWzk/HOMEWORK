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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.supplier.Control.Controler;
import com.example.supplier.Model.Dish;
import com.example.supplier.R;
import com.example.supplier.uitl.Tool;
import com.example.supplier.View.frag.DishFrag;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/23.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {
    Context context;
    List<Dish> list;
    DishFrag frag;

    public DishAdapter(Context context, List<Dish> list, DishFrag frag) {
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
                Dish dish = list.get(holder.getAdapterPosition());

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView dishName, dishPrice;
        RatingBar ratingBar;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            dishName = (TextView) itemView.findViewById(R.id.dish_item_name);
            dishPrice = (TextView) itemView.findViewById(R.id.dish_item_sellingprice);
            card = (CardView) itemView.findViewById(R.id.dish_item_card);
            ratingBar = (RatingBar) itemView.findViewById(R.id.dish_item_ratingbar);
            card.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 1, 0, "修改菜式");
            menu.add(0, 2, 1, "删除菜式");
            frag.postion = this.getAdapterPosition();
        }
    }

}
