package com.wsusingretrofitdemo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;

import com.wsusingretrofitdemo.custom.CustomDialog;

import java.util.HashMap;
import java.util.List;

public class Const {
    public static String APP_NAME = "";//App NAme
    public static final String PREF_FILE = APP_NAME + "_PREF";

    public static String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/" + APP_NAME;

    // Api ResponseModel
    public static enum API_RESULT {
        SUCCESS, FAIL
    }

    // API CALL
    public static String API_BASE_URL = " ";//http://fdsfsdfdsf.net
    public static String API_HOST = API_BASE_URL + "/api/";

    public static String CHAT_SERVER_URL = API_BASE_URL + ":2021/";

    public static final String USER_PRIVACY_SETTING = API_BASE_URL + "/privacy_policy";
    public static final String DRIVER_PRIVACY_SETTING = API_BASE_URL + "/privacy_policy";

//Country Picker

    public static final String COUNTRY_PIKER = "COUNTRY_PIKER";

    public static String[] stringCurrencyArray = null;

    public static HashMap<String, Activity> screen_al;

    public static CustomDialog custDailog = null;

    public static ProgressDialog progressDialog = null;

    public static String DEVICE_TYPE = "1";//1=android 2=ios

    public static String STATUS = "Status";
    public static String MESSAGE = "Message";
    public static String ERROR = "Error";

    public static final int INTENT100 = 100;
    public static final int INTENT200 = 200;

    //TRIP STATUS TAG
    public static final String NEW_TRIP = "new_trip";


    // PREF
    public static String PREF_IS_LOGIN = "PREF_IS_LOGIN";
    public static String PREF_USER_TYPE = "PREF_USER_TYPE";
    public static String PREF_LANGUAGE = "PREF_LANGUAGE";
    public static String PREF_GCMTOKEN = "PREF_GCMTOKEN";
    public static String PREF_USERID = "PREF_USERID";
    public static String PREF_USER_PROFILE_PIC = "PREF_USER_PROFILE_PIC";
    public static String PREF_USER_EMAIL = "PREF_USER_EMAIL";
    public static String EN = "en";
    public static String ZH = "zh";

    // Api
    public static final String USER_REGISTRATION_API = "register";
    public static final String LIST_API = "LIST_API";
    public static final String DELETE_API = "DELETE_API";

}


