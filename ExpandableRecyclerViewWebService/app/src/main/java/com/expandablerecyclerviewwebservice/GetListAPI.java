package com.expandablerecyclerviewwebservice;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import java.util.ArrayList;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class GetListAPI {

    private final static String API = Const.GET_CONTACT_LIST_API;
    private ResponseListener responseListener = null;
    private Context context = null;
    private static IRequestData iRequestData = null;



    public ArrayList<MobileOS> mobileOSes;

    public GetListAPI(Context _context, ResponseListener _responseListener) {
        this.context = _context;
        responseListener = _responseListener;
        iRequestData = Utils.retrofit.create(IRequestData.class);
    }

    // Request Api Param
    private interface IRequestData {
        @FormUrlEncoded
        @POST(API)
        Call<ResponseModel<Phone>> getResponseData(
                @Field("userid") String user_id,
                @Field("device_type") String deviceType,
                @Field("token") String token,
                @Field("language") String language
        );
    }

    public Void execute() {
        ArrayList<Phone> iphones = new ArrayList<>();
        iphones.add(new Phone("iPhone 4"));
        iphones.add(new Phone("iPhone 4S"));
        iphones.add(new Phone("iPhone 5"));
        iphones.add(new Phone("iPhone 5S"));
        iphones.add(new Phone("iPhone 6"));
        iphones.add(new Phone("iPhone 6Plus"));
        iphones.add(new Phone("iPhone 6S"));
        iphones.add(new Phone("iPhone 6S Plus"));

        ArrayList<Phone> nexus = new ArrayList<>();
        nexus.add(new Phone("Nexus One"));
        nexus.add(new Phone("Nexus S"));
        nexus.add(new Phone("Nexus 4"));
        nexus.add(new Phone("Nexus 5"));
        nexus.add(new Phone("Nexus 6"));
        nexus.add(new Phone("Nexus 5X"));
        nexus.add(new Phone("Nexus 6P"));
        nexus.add(new Phone("Nexus 7"));

        ArrayList<Phone> windowPhones = new ArrayList<>();
        windowPhones.add(new Phone("Nokia Lumia 800"));
        windowPhones.add(new Phone("Nokia Lumia 710"));
        windowPhones.add(new Phone("Nokia Lumia 900"));
        windowPhones.add(new Phone("Nokia Lumia 610"));
        windowPhones.add(new Phone("Nokia Lumia 510"));
        windowPhones.add(new Phone("Nokia Lumia 820"));
        windowPhones.add(new Phone("Nokia Lumia 920"));
        mobileOSes = new ArrayList<>();

        mobileOSes.add(new MobileOS("iOS", iphones));
        mobileOSes.add(new MobileOS("Android", nexus));
        mobileOSes.add(new MobileOS("Window Phone", windowPhones));
        /*try {

            Call<ResponseModel<ConatctModel>> call = iRequestData.getResponseData(Utils.getSharedPreString(context, Const.PREF_USERID), Const.DEVICE_TYPE, Utils.getSharedPreString(context, Const.DEVICE_TOKEN), Const.LANGUAGE);
            call.enqueue(new Callback<ResponseModel<ConatctModel>>() {


                @Override
                public void onResponse(Call<ResponseModel<ConatctModel>> call, Response<ResponseModel<ConatctModel>> response) {
                    int status = 0;
                    String mesg = "";
                    try {
                        Utils.print("Message==>"+ response.body().success + "::" + response.body().message + "");

                        status = response.body().success;
                        mesg = response.body().message;
                        objList = new ArrayList<ConatctModel>();
                        if (status == 1) {
                            objList.addAll(response.body().getResult());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    doCallBack(status, mesg);
                }

                @Override
                public void onFailure(Call<ResponseModel<ConatctModel>> call, Throwable t) {
                    Utils.print("Upload error:"+ t.getMessage());
                    doCallBack(-2, t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        doCallBack(1, "");
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