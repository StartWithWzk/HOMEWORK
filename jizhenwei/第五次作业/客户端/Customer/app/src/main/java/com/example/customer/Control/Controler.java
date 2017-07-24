package com.example.customer.Control;

import android.app.Application;

import com.example.customer.Model.Customer;
import com.example.customer.Model.DataOperator;
import com.example.customer.Model.Dish;
import com.example.customer.Model.Order;
import com.example.customer.Model.Supplier;
import com.example.customer.Util.Tool;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class Controler {
    private static Controler mControler = null;
    private static DataOperator mOperator = null;

    public interface OnUserStatusListener {
        void onResult(int feedback, Customer customer);
    }

    public interface OnGetSupplierListListener {
        void onGet(List<Supplier> list);
    }

    public interface OnGetOrderListListener {
        void onGet(List<Order> list);
    }

    public interface OnGetDishListListener {
        void onGet(List<Dish> list);
    }

    public interface OnOrderListener {
        void onOrder();
    }

    private Controler() {

    }

    public static Controler getInstance() {
        if (mControler != null) {
            return mControler;
        } else {
            mControler = new Controler();
            mControler.mOperator = DataOperator.getInstance();
            return mControler;
        }
    }

    public void login(String name, String password, final OnUserStatusListener listener) {
        final Customer customer = new Customer();
        final Gson gson = new Gson();
        customer.setName(name);
        customer.setPassword(password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] feedbackArray = mOperator.login(customer).split("!@#");
                listener.onResult(Integer.parseInt(feedbackArray[0]), gson.fromJson(feedbackArray[1], Customer.class));
            }
        }).start();

    }

    public void register(String name, String password, final OnUserStatusListener listener) {
        final Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onResult(mOperator.register(customer), customer);
            }
        }).start();

    }

    public void logout(final OnUserStatusListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onResult(mOperator.logout(), null);
            }
        }).start();
    }

    public void getSupplierList(final OnGetSupplierListListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onGet(mOperator.getSupplierList());
            }
        }).start();
    }

    public void getOrderList(final OnGetOrderListListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onGet(mOperator.getOrderList(Tool.getUser()));
            }
        }).start();
    }

    public void getDishList(final Supplier supplier, final OnGetDishListListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onGet(mOperator.getDishList(supplier));
            }
        }).start();
    }

    public void logoutInMe() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.logoutInMe();
            }
        }).start();

    }

    public void order(final Dish dish, final OnOrderListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.order(dish);
                listener.onOrder();
            }
        }).start();
    }

    public void cancelOrder(final Order order, final OnOrderListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.cancelorder(order);
                listener.onOrder();
            }
        }).start();

    }

    public void gradeOrder(final Order order, final OnOrderListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.gradeOrder(order);
                listener.onOrder();
            }
        }).start();

    }
    public void doneOrder(final Order order, final OnOrderListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.doneOrder(order);
                listener.onOrder();
            }
        }).start();

    }
}
