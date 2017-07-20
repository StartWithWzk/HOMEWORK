package com.example.qgui.demo;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.qgui.R;
import com.example.qgui.adapter.SimpleFragmentPagerAdapter;
import com.example.qgui.base.TopbarBaseActivity;

public class MainActivity extends TopbarBaseActivity {

//  private RecyclerView mRecyclerView;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        setTitles(R.string.homepage);

        setTopLeftButton(R.drawable.ic_white_arrow, new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });

        setTopRightButton("button", R.drawable.ic_white_chat, new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(MainActivity.this, BookInfoActivity.class);
                startActivity(intent);
            }
        });

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("蔡静怡");

//        List<Book> bookList = new ArrayList<>();
//        Book book = new Book("天龙八部");
//        for(int i=0; i<15; i++){
//            bookList.add(book);
//        }
//        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(manager);
//        BookAdapter adapter = new BookAdapter(bookList);
//        mRecyclerView.setAdapter(adapter);
    }
}
