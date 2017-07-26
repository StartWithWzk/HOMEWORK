package com.example.linzongzhan.sellerclient;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class OrderM {

    private String buyer;

    private String food;

    private String time;

    private int status;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
