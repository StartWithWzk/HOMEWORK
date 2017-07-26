package com.example.linzongzhan.buyerclient;

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
 * Created by linzongzhan on 2017/7/25.
 */

public class listview_seller_adapter extends ArrayAdapter <String> {


    private int resourceId;

    public listview_seller_adapter (Context context, int textViewResourceId, List<String> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String seller_name = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView sellername = (TextView) view.findViewById(R.id.seller_name);
        sellername.setText(seller_name);
        return view;
    }
}
