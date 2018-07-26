package com.expandablerecyclerviewwebservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;



import java.util.HashMap;

public class Const {
    public static String APP_NAME = "Demo";
    public static final String PREF_FILE = APP_NAME + "_PREF";

    public static String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/" + APP_NAME;

    // Api ResponseModel
    public static enum API_RESULT {
        SUCCESS, FAIL
    }

    // API CALL
    public static String API_BASE_URL = "http://12.345.67.890";

    public static String API_HOST = API_BASE_URL + "/api/";


    // PREF
    public static String PREF_LANGUAGE = "PREF_LANGUAGE";

    public static String PREF_USERID = "PREF_USERID";

    public static final String GET_CONTACT_LIST_API = "CONTACTLIST_API";




}