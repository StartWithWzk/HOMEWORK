package com.example.linzongzhan.myui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;




public class homepage extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_homepage);

  //      TabLayout tabLayout = (TabLayout) findViewById(R.id.hom_tablayout);

   //     tabLayout.addTab(tabLayout.newTab().setText("书城"));
    //    tabLayout.addTab(tabLayout.newTab().setText("发现"));
  //      tabLayout.addTab(tabLayout.newTab().setText("发布"));
   //     tabLayout.addTab(tabLayout.newTab().setText("购物车"));
  //      tabLayout.addTab(tabLayout.newTab().setText("我的"));

        textView = (TextView) findViewById(R.id.homepage_my);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this,presentation.class);
                startActivity(intent);
            }
        });
    }
}
