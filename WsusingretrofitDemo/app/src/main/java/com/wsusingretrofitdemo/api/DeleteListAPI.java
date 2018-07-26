package com.wsusingretrofitdemo.api;

import android.app.Activity;
import android.content.Context;

import com.wsusingretrofitdemo.backend.ResponseListener;
import com.wsusingretrofitdemo.backend.ResponseModelService;
import com.wsusingretrofitdemo.utils.Const;
import com.wsusingretrofitdemo.utils.Pref;
import com.wsusingretrofitdemo.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class DeleteListAPI {

    private final static String API = Const.DELETE_API;
    private ResponseListener responseListener = null;
    private Context context = null;
    private static IRequestData iRequestData = null;



    public DeleteListAPI(Context _context, ResponseListener _responseListener) {
        this.context = _context;
        responseListener = _responseListener;
        iRequestData = Utils.retrofit.create(IRequestData.class);
    }

    // Request Api Param
    private interface IRequestData {
        @FormUrlEncoded
        @POST(API)
        Call<ResponseModelService> getResponseData(
                @Field("id") String id,
                @Field("device_type") String deviceType,
                @Field("device_token") String device_token,
                @Field("language") String language
        );
    }

    public Void execute(String id) {
        try {

            Call<ResponseModelService> call = iRequestData.getResponseData(id,  Const.DEVICE_TYPE, Pref.getStringValue(context, Const.PREF_GCMTOKEN,""),  Pref.getStringValue(context, Const.PREF_LANGUAGE,Const.EN));
            call.enqueue(new Callback<ResponseModelService>() {


                @Override
                public void onResponse(Call<ResponseModelService> call, Response<ResponseModelService> response) {
                    int status = 0;
                    String mesg = "";
                    try {
                        Utils.print("Message==>"+ response.body().success + "::" + response.body().msg + "");

                        status = response.body().success;
                        mesg = response.body().msg;
                        if (status == 1) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    doCallBack(status, mesg);
                }

                @Override
                public void onFailure(Call<ResponseModelService> call, Throwable t) {
                    Utils.print("Upload error:"+ t.getMessage());
                    doCallBack(-2, t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Send control back to the caller which includes
     * Status: Successful or Failure Message: Its an Object, if required
     */
    private void doCallBack(final int code, final String mesg) {
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (code == 1) {
                    responseListener.onResponse(API, Const.API_RESULT.SUCCESS, null);
                } else if (code >= 0) {
                    Utils.showToastMessage(context, mesg, false);
                    responseListener.onResponse(API, Const.API_RESULT.FAIL, null);
                } else if (code < 0) {
                    responseListener.onResponse(API, Const.API_RESULT.FAIL, null);
                }

            }
        });
    }
}