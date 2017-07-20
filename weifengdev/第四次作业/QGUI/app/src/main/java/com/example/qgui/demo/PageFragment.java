package com.example.qgui.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qgui.R;
import com.example.qgui.adapter.BookAdapter;

/**
 * Created by 黄伟烽 on 2017/7/19.
 */

public class PageFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static final String ARG_PAGE = "ARG_PAGE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        BookAdapter adapter = new BookAdapter();
        mRecyclerView.setAdapter(adapter);
        return  view;
    }
}
