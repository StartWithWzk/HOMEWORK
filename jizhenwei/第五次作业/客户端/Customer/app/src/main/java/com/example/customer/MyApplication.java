package com.example.customer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.customer.Util.Tool;

import java.util.Stack;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class MyApplication extends Application {
    public static Context context = null;
    public static Stack<Activity> activityStack = new Stack<>();
    public static SharedPreferences preferences = null;
    @Override
    public void onCreate() {
        context = getApplicationContext();
        setActivityLifecycleCallbacks();
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        super.onCreate();
    }
    private void setActivityLifecycleCallbacks(){
       registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
           @Override
           public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
               Tool.log(activity,"run");
               activityStack.push(activity);
           }

           @Override
           public void onActivityStarted(Activity activity) {

           }

           @Override
           public void onActivityResumed(Activity activity) {

           }

           @Override
           public void onActivityPaused(Activity activity) {

           }

           @Override
           public void onActivityStopped(Activity activity) {

           }

           @Override
           public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

           }

           @Override
           public void onActivityDestroyed(Activity activity) {
                Tool.log(activityStack.pop(),"pop");
           }
       });
    }
}
