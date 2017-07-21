package com.example.linzongzhan.myui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;


public class presentation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_presentation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.presentation_toolbar);
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back1);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.presentation_collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("  ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.pre_viewpage);
        fragment_page_adapter fragment_page_adapter = new fragment_page_adapter(getSupportFragmentManager(),this);
       // FindFragment findFragment = new FindFragment();
        viewPager.setAdapter(fragment_page_adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.pre_tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }
}
