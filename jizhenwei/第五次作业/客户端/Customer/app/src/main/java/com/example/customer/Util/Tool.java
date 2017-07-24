package com.example.customer.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.customer.Model.Customer;
import com.example.customer.MyApplication;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class Tool {
    private static Toast mToast = null;
    private static Object mObject = null;

    public static boolean isWifiOk() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    public static boolean isMobileOk() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    public static void log(Object o) {
        Log.d("日志：---------", o.toString());
    }

    public static void log(Object o, Object o1) {
        Log.d("日志：" + o + "----------", o1.toString());
    }

    public static void toast(final Object o) {

        MyApplication.activityStack.firstElement().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == mToast || !o.toString().equals(mObject.toString())) {
                    if (null == mToast) {
                        mToast = Toast.makeText(MyApplication.context, o.toString(), Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(o.toString());
                    }
                    mObject = o;
                }
                mToast.show();
            }
        });

    }
    public static void setUser(Customer customer){
        Tool.log("保存",customer);
        SharedPreferences.Editor editor = MyApplication.preferences.edit();
        editor.putString("name",customer.getName());
        editor.putString("password",customer.getPassword());
        editor.putLong("objectId",customer.getObjectId());
        editor.apply();
    }

    public static Customer getUser(){
        SharedPreferences preferences = MyApplication.preferences;
        Customer customer = new Customer();
        customer.setName(preferences.getString("name","!@#"));
        customer.setPassword(preferences.getString("password","!@#"));
        customer.setObjectId(preferences.getLong("objectId",0));
        Tool.log("取出",customer);
        if (customer.getName().equals("!@#")) {
            return null;
        } else return customer;
    }
    public static void runOnUiThread(Runnable runnable){
        MyApplication.activityStack.firstElement().runOnUiThread(runnable);
    }
}
