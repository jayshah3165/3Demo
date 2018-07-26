package com.expandablerecyclerviewwebservice;


import java.util.ArrayList;

public class ResponseModelService {

    public int success;
    public String message;
    public ArrayList<Object> result;

    public int success() {
        return success;
    }

    public void setsuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
