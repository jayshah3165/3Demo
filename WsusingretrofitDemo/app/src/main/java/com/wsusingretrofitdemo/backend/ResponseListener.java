package com.wsusingretrofitdemo.backend;


import com.wsusingretrofitdemo.utils.Const;

public interface ResponseListener {
    // API ResponseModel Listener
    public void onResponse(String tag, Const.API_RESULT result, Object obj);

}
