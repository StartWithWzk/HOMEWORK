package com.example.supplier.Model;


import com.google.gson.Gson;

/**
 * Created by 小吉哥哥 on 2017/7/21.
 */
public class Dish  {
    private double cost;
    private double sellingPrice;
    private int gradeCount;
    private int gradeSum;
    private long supplierId;
    public static final int SUEECSS = 1;
    private String name;
    private long objectId;

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(int gradeCount) {
        this.gradeCount = gradeCount;
    }

    public int getGradeSum() {
        return gradeSum;
    }

    public void setGradeSum(int gradeSum) {
        this.gradeSum = gradeSum;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public static Dish castfromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Dish.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "cost=" + cost +
                ", sellingPrice=" + sellingPrice +
                ", gradeCount=" + gradeCount +
                ", gradeSum=" + gradeSum +
                ", supplierId=" + supplierId +
                ", name='" + name + '\'' +
                ", objectId=" + objectId +
                '}';
    }
}
