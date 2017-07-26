package com.example.linzongzhan.sellerclient;

import android.app.Application;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class CustomApplication extends Application {

    private static Queue<Guide> guideQueue;

    public static String name;

    @Override
    public void onCreate() {
        guideQueue = new LinkedList<>();
        super.onCreate();

    }

    //入列
    public void setGuide (Guide guide) {
        guideQueue.add(guide);
    }

    //得到队列头
    public static Guide getGuide () {
        return guideQueue.peek();
    }

    //出列
    public static void pollGuide () {
        guideQueue.poll();
    }


    public static String getName() {
        return name;
    }

    public void setName(String name) {
        CustomApplication.name = name;
    }
}
