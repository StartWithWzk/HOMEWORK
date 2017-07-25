package com.example.customer.Model;

import android.util.Log;

import com.example.customer.Util.Tool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            return mOperator;
        }

    }

    public boolean connect() {

        if (!Tool.isMobileOk() && !Tool.isWifiOk()) {
            Tool.toast("请检查网络是否畅通");
        }
        try {
            mSocket = new Socket("192.168.43.47", 8060);
            mDos = new DataOutputStream(mSocket.getOutputStream());
            mDis = new DataInputStream(mSocket.getInputStream());
            return true;
        } catch (Exception e) {
            Tool.toast("服务器出了点小问题");
            e.printStackTrace();
            return false;
        }


    }

    public String login(Customer customer) {
        Gson gson = new Gson();
        if (!connect()) {
            return -1 + "!@#" + gson.toJson(customer);
        }
        String command = "customer!@#login!@#" + gson.toJson(customer);
        byte[] feeback = new byte[1024];

        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feeback);
            String[] feedbackArray = new String(feeback, 0, len, "UTF-8").split("!@#");
            return new String(feeback, 0, len, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return -1 + "!@#" + gson.toJson(customer);
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int register(Customer customer) {
        if (!connect()) {
            return -1;
        }
        Gson gson = new Gson();
        String command = "customer!@#register!@#" + gson.toJson(customer);
        byte[] feeback = new byte[1024];

        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feeback);
            return Integer.parseInt(new String(feeback, 0, len, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int logout() {
        if (!connect()) {
            return -1;
        }
        Customer customer = Tool.getUser();
        Gson gson = new Gson();
        String command = "customer!@#logout!@#" + gson.toJson(customer);
        byte[] feeback = new byte[1024];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feeback);
            return Integer.parseInt(new String(feeback, 0, len, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logoutInMe() {
        if (!connect()) {
            return;
        }
        Customer customer = Tool.getUser();
        Gson gson = new Gson();
        String command = "customer!@#logout!@#" + gson.toJson(customer);
        try {
            mDos.write(command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Supplier> getSupplierList() {
        if (!connect()) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        String command = "customer!@#supplier";
        byte[] feedback = new byte[1024 * 8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
            return gson.fromJson(new String(feedback, 0, len, "UTF-8"), new TypeToken<List<Supplier>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Order> getOrderList(Customer customer) {
        if (!connect()) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        String command = "customer!@#order!@#get!@#" + customer.getObjectId();
        byte[] feedback = new byte[1024 * 8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
            return gson.fromJson(new String(feedback, 0, len, "UTF-8"), new TypeToken<List<Order>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Dish> getDishList(Supplier supplier) {
        if (!connect()) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        String command = "customer!@#dish!@#" + supplier.getObjectId();
        byte[] feedback = new byte[1024 * 8];
        try {
            mDos.write(command.getBytes());
            int len = mDis.read(feedback);
            return gson.fromJson(new String(feedback, 0, len, "UTF-8"), new TypeToken<List<Dish>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void order(Dish dish) {
        if (!connect()) {
            return;
        }
        Gson gson = new Gson();
        Order order = new Order();
        order.setCustomerId(Tool.getUser().getObjectId());
        order.setDishId(dish.getObjectId());
        order.setSupplierId(dish.getSupplierId());
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(format.format(date));
        String command = "customer!@#order!@#" + gson.toJson(order);
        byte[] feedback = new byte[10];
        try {
            mDos.write(command.getBytes());
            mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelorder(Order order) {
        if (!connect()) {
            return;
        }
        Gson gson = new Gson();
        String command = "customer!@#order!@#cancel!@#" + order.getObjectId();
        byte[] feedback = new byte[10];
        try {
            mDos.write(command.getBytes());
            mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void gradeOrder(Order order) {
        if (!connect()) {
            return;
        }
        Gson gson = new Gson();
        String command = "customer!@#order!@#rate!@#" + gson.toJson(order);
        byte[] feedback = new byte[10];
        try {
            mDos.write(command.getBytes());
            mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void doneOrder(Order order) {
        if (!connect()) {
            return;
        }
        String command = "customer!@#order!@#sure!@#" + order.getObjectId();
        byte[] feedback = new byte[10];
        try {
            mDos.write(command.getBytes());
            mDis.read(feedback);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
