package com.example.android.douban.model;

/**
 * Created by Derrick on 2018/6/8.
 */

public class Response<T> {

    private String code;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
