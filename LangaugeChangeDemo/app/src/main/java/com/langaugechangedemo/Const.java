package com.langaugechangedemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;


import java.util.HashMap;
import java.util.List;

public class Const {
    public static String APP_NAME = "Demo";
    public static final String PREF_FILE = APP_NAME + "_PREF";

    public static String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/" + APP_NAME;




    // PREF

    public static String PREF_LANGUAGE = "PREF_LANGUAGE";

    public static String EN = "en";
    public static String ZH = "zh";
    public static String FR = "fr";
    public static String ES = "es";



















}