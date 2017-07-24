package com.example.supplier.Model;

import com.example.supplier.uitl.Tool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class DataOperator {
    private static DataOperator mOperator = null;
    private static Socket mSocket = null;
    private static DataInputStream mDis = null;
    private static DataOutputStream mDos = null;

    private DataOperator() {

    }

    public static DataOperator getInstance() {
        if (mOperator != null) {
            return mOperator;
        } else {
            mOperator = new DataOperator();
            mOperator.connect("192.168.43.47", 8076);
            return mOperator;
        }

    }

    public void connect(final String ip, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Tool.isMobileOk() && !Tool.isWifiOk()) {
                    Tool.toast("无法连接至服务器");

                }
                try {
                    mSocket = new Socket(ip, port);
                    mDos = new DataOutputStream(mSocket.getOutputStream());
                    mDis = new DataInputStream(mSocket.getInputStream());
                    Tool.log("服务器连接成功");
                    Tool.toast("服务器连接成功");
                } catch (Exception e) {
                    Tool.toast("服务器异常");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public String login(Supplier supplier) {
        Gson gson = new Gson();
        String command = "supplier!@#login!@#" + gson.toJson(supplier);
        byte[] feeback = new byte[1024];

        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feeback);
            String[] feedbackArray = new String(feeback, 0, len, "UTF-8").split("!@#");
            return new String(feeback, 0, len, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return -1 + "!@#" + gson.toJson(supplier);
        }
    }

    public int register(Supplier supplier) {
        Gson gson = new Gson();
        String command = "supplier!@#register!@#" + gson.toJson(supplier);
        byte[] feeback = new byte[1024];

        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feeback);
            return Integer.parseInt(new String(feeback, 0, len, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int logout() {
        Supplier supplier = Tool.getUser();
        Gson gson = new Gson();
        String command = "supplier!@#logout!@#" + gson.toJson(supplier);
        byte[] feeback = new byte[1024];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feeback);
            return Integer.parseInt(new String(feeback, 0, len, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void logoutInMe() {
        Supplier supplier = Tool.getUser();
        Gson gson = new Gson();
        String command = "supplier!@#logout!@#" + gson.toJson(supplier);
        try {
            mDos.write(command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrderList(Supplier supplier) {
        Gson gson = new Gson();
        String command = "supplier!@#order!@#get!@#" + supplier.getObjectId();
        byte[] feedback = new byte[1024 * 8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
            return gson.fromJson(new String(feedback, 0, len, "UTF-8"), new TypeToken<List<Order>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Dish> getDishList(Supplier supplier) {
        Gson gson = new Gson();
        String command = "supplier!@#dish!@#get!@#" + supplier.getObjectId();
        byte[] feedback = new byte[1024 * 8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
            return gson.fromJson(new String(feedback, 0, len, "UTF-8"), new TypeToken<List<Dish>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newDish(Dish dish) {
        Gson gson = new Gson();
        String command = "supplier!@#dish!@#add!@#" + gson.toJson(dish);
        byte[] feedback = new byte[8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDish(Dish newDish){
        Gson gson = new Gson();
        String command = "supplier!@#dish!@#set!@#" + gson.toJson(newDish);
        byte[] feedback = new byte[8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDish(Dish dish){
        String command = "supplier!@#dish!@#delete!@#" + dish.getObjectId();
        byte[] feedback = new byte[8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void acceptOrder(Order order){
        String command = "supplier!@#order!@#accept!@#" + order.getObjectId();
        byte[] feedback = new byte[8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
