package com.example.linzongzhan.buyerclient;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class ActivityCollecter {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity (Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity (Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll () {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
            activities.clear();
        }
    }
}
