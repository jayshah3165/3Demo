package com.expandablerecyclerviewwebservice;



public interface ResponseListener {
    // API ResponseModel Listener
    public void onResponse(String tag, Const.API_RESULT result, Object obj);

}
