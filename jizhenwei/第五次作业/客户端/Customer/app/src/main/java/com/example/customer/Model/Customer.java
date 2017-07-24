package com.example.customer.Model;


import com.google.gson.Gson;


import java.sql.SQLException;
import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/21.
 */
public class Customer  {
    private String name = null;
    private String password = null;
    private int status;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 0;
    public static final int ALREADY_ONLINE = 2;
    public static final int PASSWORD_ERR = 3;
    public static final int NOT_FOUND = 4;
    public static final int ALREADY_EXIST = 5;
    public static final int SUCCESS = 6;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }





    public static Customer castFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Customer.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", objectId=" + objectId +
                '}';
    }
}
