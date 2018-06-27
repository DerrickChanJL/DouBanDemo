package com.example.android.douban.model;

import java.util.List;

/**
 * Created by Derrick on 2018/6/8.
 */

public class UserBean {

    private int id;
    private String username;
    private String password;
    private List<String> collection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getCollection() {
        return collection;
    }

    public void setCollection(List<String> collection) {
        this.collection = collection;
    }
}
