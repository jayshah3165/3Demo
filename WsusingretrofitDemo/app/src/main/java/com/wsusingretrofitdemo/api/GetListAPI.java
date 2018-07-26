package com.wsusingretrofitdemo.api;

import android.app.Activity;
import android.content.Context;

import com.wsusingretrofitdemo.backend.ResponseListener;
import com.wsusingretrofitdemo.backend.ResponseModel;
import com.wsusingretrofitdemo.model.ListModel;
import com.wsusingretrofitdemo.utils.Const;
import com.wsusingretrofitdemo.utils.Pref;
import com.wsusingretrofitdemo.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class GetListAPI {

    private final static String API = Const.LIST_API;
    private ResponseListener responseListener = null;
    private Context context = null;
    private static IRequestData iRequestData = null;


    public ArrayList<ListModel> objList = null;

    public GetListAPI(Context _context, ResponseListener _responseListener) {
        this.context = _context;
        responseListener = _responseListener;
        iRequestData = Utils.retrofit.create(IRequestData.class);
    }

    // Request Api Param
    private interface IRequestData {
        @FormUrlEncoded
        @POST(API)
        Call<ResponseModel<ListModel>> getResponseData(
                @Field("user_id") String user_id,
                @Field("device_type") String deviceType,
                @Field("device_token") String device_token,
                @Field("language") String language
        );
    }

   public Void execute() {
     try {
            Call<ResponseModel<ListModel>> call = iRequestData.getResponseData(Pref.getStringValue(context, Const.PREF_USERID, ""), Const.DEVICE_TYPE, Pref.getStringValue(context, Const.PREF_GCMTOKEN, ""), Pref.getStringValue(context, Const.PREF_LANGUAGE, Const.EN));
            call.enqueue(new Callback<ResponseModel<ListModel>>() {
                @Override
                public void onResponse(Call<ResponseModel<ListModel>> call, Response<ResponseModel<ListModel>> response) {
                    int status = 0;
                    String mesg = "";
                    try {
                        Utils.print("Message==>"+ response.body().success + "::" + response.body().msg + "");
                        status = response.body().success;
                        mesg = response.body().msg;
                        objList = new ArrayList<ListModel>();
                        if (status == 1) {
                            objList.addAll(response.body().getResult());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    doCallBack(status, mesg);
                }

                @Override
                public void onFailure(Call<ResponseModel<ListModel>> call, Throwable t) {
                    Utils.print("Upload error:"+t.getMessage());
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