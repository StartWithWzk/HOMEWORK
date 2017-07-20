package com.example.linzongzhan.myui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by linzongzhan on 2017/7/20.
 */

public class listview_adapter extends ArrayAdapter<String> {

    private int resourceid;

    public listview_adapter(Context context, int textViewResourceId, List<String> objects) {
        super(context,textViewResourceId,objects);
        resourceid = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
        return view;
    }
}
