package com.example.qgui.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.qgui.R;
import com.example.qgui.base.TopbarBaseActivity;

public class BookInfoActivity extends TopbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_book_info;
    }

    @Override
    protected void init(final Bundle savedInstanceState) {
        setTitles(R.string.bookinfo);

        setTopLeftButton(R.drawable.ic_green_arrow, new OnClickListener() {
            @Override
            public void onClick() {

                Intent intent = new Intent(BookInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setTopRightButton("button", R.drawable.ic_green_chat, new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(BookInfoActivity.this, "点击了菜单", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
