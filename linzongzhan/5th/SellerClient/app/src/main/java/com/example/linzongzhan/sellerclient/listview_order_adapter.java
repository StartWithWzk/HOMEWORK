package com.example.linzongzhan.sellerclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class listview_order_adapter extends ArrayAdapter<OrderM> {

    private int resourceId;

    public listview_order_adapter (Context context, int textViewResourceId, List<OrderM> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrderM orderM = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView order_buyer = (TextView) view.findViewById(R.id.order_buyer);
        TextView order_food = (TextView) view.findViewById(R.id.order_food);
        TextView order_time = (TextView) view.findViewById(R.id.order_time);
        TextView order_status = (TextView) view.findViewById(R.id.order_status);
        order_buyer.setText(orderM.getBuyer());
        order_food.setText(orderM.getFood());
        order_time.setText(orderM.getTime());
        if (orderM.getStatus() == 0) {
            order_status.setText("处理中");
        } else if (orderM.getStatus() == 1) {
            order_status.setText("已接受");
        } else if (orderM.getStatus() == 2) {
            order_status.setText("已取消");
        }
        return view;
    }
}
