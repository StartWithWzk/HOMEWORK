package com.example.supplier.Model;


import com.google.gson.Gson;

/**
 * Created by 小吉哥哥 on 2017/7/21.
 */
public class Order {
    public static final int WAIT = 0;
    public static final int DOING = 1;
    public static final int DONE = 2;
    public static final int CANCEL_BY_CUSTOMER = 3;
    private long customerId;
    private String customerName;
    private String supplierName;
    private String dishName;
    private int isRated;
    private int star;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getIsRated() {
        return isRated;
    }

    public void setIsRated(int isRated) {
        this.isRated = isRated;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    private long supplierId;
    private long dishId;
    private int status;
    private String date;
    private long objectId;

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static Order castfromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Order.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerId=" + customerId +
                ", supplierId=" + supplierId +
                ", dishId=" + dishId +
                ", status=" + status +
                ", date='" + date + '\'' +
                ", objectId=" + objectId +
                '}';
    }
}
