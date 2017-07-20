package com.example.qgui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.qgui.R;

/**
 * Created by 黄伟烽 on 2017/7/18.
 */

public abstract class TopbarBaseActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mTvTitle;

    OnClickListener mOnClickListenerTopLeft;
    OnClickListener mOnClickListenerTopRight;

    int menuResId;
    String menuStr;

    public interface OnClickListener{
        void onClick();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);

        mToolbar.setTitle("呵呵呵");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init(savedInstanceState);
    }

    protected void setTitles(int title){
        if(title != 0){
            mTvTitle.setText(title);
        }
    }

    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener){
        mToolbar.setNavigationIcon(iconResId);
        this.mOnClickListenerTopLeft = onClickListener;
    }

    protected  void setTopRightButton(String menuStr, int menuResId, OnClickListener onClickListener){
        this.menuResId = menuResId;
        this.menuStr = menuStr;
        mOnClickListenerTopRight = onClickListener;
    }

    protected abstract int getContentView();
    protected abstract void init(Bundle savedInstanceState);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menuResId != 0 || !TextUtils.isEmpty(menuStr)){
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menuResId != 0){
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if(!TextUtils.isEmpty(menuStr)){
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            mOnClickListenerTopLeft.onClick();
        }
        else if(item.getItemId() == R.id.menu_1){
            mOnClickListenerTopRight.onClick();
        }
        return true;
    }
}
