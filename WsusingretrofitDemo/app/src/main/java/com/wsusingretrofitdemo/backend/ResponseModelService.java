package com.wsusingretrofitdemo.backend;


import java.util.ArrayList;

public class ResponseModelService {

    public int success;
    public String msg;
    public ArrayList<Object> result;

    public int success() {
        return success;
    }

    public void setsuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }


}
