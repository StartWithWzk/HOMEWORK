package com.example.linzongzhan.myui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by linzongzhan on 2017/7/20.
 */

public class my_fragment extends Fragment {

    private List<String> stringList = new ArrayList<>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.pre_recycle_view,container,false);

        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");

        listView =  view.findViewById(R.id.list_view);

        listview_adapter listview_adapter = new listview_adapter(getActivity(),R.layout.listview,stringList);

        listView.setAdapter(listview_adapter);
        return view;
    }


}
