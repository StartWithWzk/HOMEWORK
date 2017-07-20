package com.example.linzongzhan.myui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class bookpage extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookpage);

     //   Toolbar toolbar = (Toolbar) findViewById(R.id.boo_toolbar);
      //  setSupportActionBar(toolbar);
      //  ActionBar actionBar = getSupportActionBar();
        button = (Button) findViewById(R.id.bookage_homepage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bookpage.this,homepage.class);
                startActivity(intent);
            }
        });
    }
}
