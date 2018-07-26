package com.expandablerecyclerviewwebservice;

import java.util.ArrayList;


public class ResponseModel<T> {

    public int success;
    public String message;
    public ArrayList<T> result;

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

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }


}
