package com.example.linzongzhan.sellerclient;

/**
 * Created by linzongzhan on 2017/7/23.
 */

public class Command {

    //标识身份为商家或买家
    private int identity = 0;
    //用户名
    private String name = "";
    //要求的操作
    private String order = "";
    //密码
    private String password = "";
    //此信息的目标
    private String goal = "";
    //菜式
    private String foodStytle = "";
    //时间
    private String time = "";

    private int cost = 0;

    private int price = 0;

    private String foodProject = ""; //要更改的菜式的项目

    private String resultString = "";//更改菜式的名字

    private int resultInt = 0; //更改菜式cost或price


    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getFoodStytle() {
        return foodStytle;
    }

    public void setFoodStytle(String foodStytle) {
        this.foodStytle = foodStytle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFoodProject() {
        return foodProject;
    }

    public void setFoodProject(String foodProject) {
        this.foodProject = foodProject;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public int getResultInt() {
        return resultInt;
    }

    public void setResultInt(int resultInt) {
        this.resultInt = resultInt;
    }
}
