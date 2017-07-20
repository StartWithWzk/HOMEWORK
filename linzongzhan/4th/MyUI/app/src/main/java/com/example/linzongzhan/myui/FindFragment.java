package com.example.linzongzhan.myui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/21.
 */

public class FindFragment extends Fragment {

    private TabLayout tab_FindFragment_title;
    private ViewPager vp_FindFragment_pager;
    private FragmentPagerAdapter fAdapter;

    private List<Fragment> list_fragment;
    private List<String> list_title;
    private my_fragment my_fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pre_recycle_view, container, false);
        initControls(view);
        return view;
    }

    private void initControls(View view) {
      //  tab_FindFragment_title = (TabLayout)view.findViewById(R.id.pre_tablayout);
        vp_FindFragment_pager = (ViewPager)view.findViewById(R.id.pre_viewpage);
        my_fragment = new my_fragment();
        list_fragment = new ArrayList<>();
        list_fragment.add(my_fragment);
        list_fragment.add(my_fragment);
        list_fragment.add(my_fragment);

        list_title = new ArrayList<>();
        list_title.add("我的出借");
        list_title.add("我的出售");
        list_title.add("我的资料");

        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);

        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));

        fAdapter = new Find_tab_Adapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);
        vp_FindFragment_pager.setAdapter(fAdapter);
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
    }
}
