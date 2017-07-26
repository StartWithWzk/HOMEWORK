package com.example.linzongzhan.sellerclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class listview_food_adapter extends ArrayAdapter<foodStyle> {

    private int resourceId;

    private static final String TAG = "listview_food_adapter";

    public listview_food_adapter (Context context, int textViewResourceId, List<foodStyle> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        foodStyle foodStyle = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView food_name = (TextView) view.findViewById(R.id.food_name);
        TextView food_cost = (TextView) view.findViewById(R.id.food_cost);
        TextView food_price = (TextView) view.findViewById(R.id.food_price);
        TextView food_grade = (TextView) view.findViewById(R.id.food_grade);
        TextView food_amount = (TextView) view.findViewById(R.id.food_amount);
        Log.d(TAG, foodStyle.getName());
        food_name.setText(foodStyle.getName());
        food_cost.setText(foodStyle.getCost() + "");
        food_price.setText(foodStyle.getPrice() + "");
        food_grade.setText(foodStyle.getGrade() + "");
        food_amount.setText(foodStyle.getAmount() + "");
        return view;
    }
}
