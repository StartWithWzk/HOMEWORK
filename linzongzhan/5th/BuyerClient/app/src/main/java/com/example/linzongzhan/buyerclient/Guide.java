package com.example.linzongzhan.buyerclient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class Guide {

    private String goal;//目标地址

    private int identity;//同上

    private String order ;

    private List<String> sellList = new ArrayList<String>(); //商家列表

    private List<String> foodNameList = new ArrayList<>();

    private List<String> gradeList = new ArrayList<>();

    private List<OrderM> orderMList = new ArrayList<>(); //订单信息列表

    private List<foodStyle> foodStyleList = new ArrayList<>();

    private int orderStatus;

    private int result;//登录或注册结果，0为失败，1为成功

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public void setOrder (String order) {
        this.order = order;
    }

    public String getOrder () {
        return order;
    }

    public void setSell (String string) {
        sellList.add(string);
    }

    public List getSellList () {
        return sellList;
    }

    public void setFoodName (String foodName) {
        foodNameList.add(foodName);
    }

    public List getFoodNameList () {
        return foodNameList;
    }

    public void setGrade (String grade) {
        gradeList.add(grade);
    }

    public List getGradeList () {
        return gradeList;
    }

    public void setOrderM (OrderM orderM) {
        orderMList.add(orderM);
    }

    public List getOderMList () {
        return orderMList;
    }

    public void setFoodStyle (foodStyle foodStyle) {
        foodStyleList.add(foodStyle);
    }

    public List getFoodStyleList () {
        return foodStyleList;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
