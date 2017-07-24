package com.example.supplier.Control;

import com.example.supplier.Model.Customer;
import com.example.supplier.Model.DataOperator;
import com.example.supplier.Model.Dish;
import com.example.supplier.Model.Order;
import com.example.supplier.Model.Supplier;
import com.example.supplier.uitl.Tool;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class Controler {
    private static Controler mControler = null;
    private static DataOperator mOperator = null;

    public interface OnUserStatusListener {
        void onResult(int feedback, Supplier supplier);
    }


    public interface OnGetOrderListListener {
        void onGet(List<Order> list);
    }

    public interface OnGetDishListListener {
        void onGet(List<Dish> list);
    }

    public interface OnSuccessListener {
        void onSuccess();
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
        final Supplier supplier = new Supplier();
        final Gson gson = new Gson();
        supplier.setName(name);
        supplier.setPassword(password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] feedbackArray = mOperator.login(supplier).split("!@#");
                listener.onResult(Integer.parseInt(feedbackArray[0]), gson.fromJson(feedbackArray[1], Supplier.class));
            }
        }).start();

    }

    public void register(String name, String password, final OnUserStatusListener listener) {
        final Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setPassword(password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onResult(mOperator.register(supplier), supplier);
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

    public void newDish(final Dish dish, final OnSuccessListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.newDish(dish);
                listener.onSuccess();
            }
        }).start();
    }

    public void setDish(final Dish dish, final OnSuccessListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.setDish(dish);
                listener.onSuccess();
            }
        }).start();
    }

    public void deleteDish(final Dish dish, final OnSuccessListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.deleteDish(dish);
                listener.onSuccess();
            }
        }).start();
    }

    public void acceptOrder(final Order order, final OnSuccessListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOperator.acceptOrder(order);
                listener.onSuccess();
            }
        }).start();
    }
}
