package com.wsusingretrofitdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wsusingretrofitdemo.custom.CustomDialog;
import com.wsusingretrofitdemo.custom.DateSetListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    public static boolean DO_SOP = true;
    public static DatePickerDialog datePickerDialog = null;

    public static final String MY_PREFERENCE = "Fartini";

    public static HttpLoggingInterceptor getlogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
   /* *//* Find index of Sting from String Array*//*
    public static int indexOfSpinnerArray(ArrayList<SpinnerModel> strArray, String id, boolean isTitle) {
        int index;
        for (index = 0; index < strArray.size(); index++) {
            Utils.print("================== VALUE > >> " + strArray.size() + ":::" + strArray.get(index).id + ":::" + id + ":::" + index);
            if (isTitle ? strArray.get(index).title.equals(id):strArray.get(index).id.equals(id)) {
                Utils.print("================== INNNNN > >> " + strArray.get(index).id + ":::" + id + ":::" + index);
                break;
            }
        }

        return index >= strArray.size() ? 0 : index;
    }*/

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(getlogging())
            .build();
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Const.API_HOST)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build();

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
 /*   public static void setToken(Context context) {
        String token = FirebaseInstanceId.getInstance().getToken() + "";
       Utils.print("Toekn=============="+token);
        if (token != null && !token.equalsIgnoreCase("null") && !token.isEmpty())
            Pref.setStringValue(context, Const.PREF_GCMTOKEN, token);


    }*/

    public static void print(String mesg) {
        if (Utils.DO_SOP) {

            if (mesg.length() > 1000) {
                int maxLogSize = 1000;
                for (int i = 0; i <= mesg.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > mesg.length() ? mesg.length() : end;
                    Log.v("Print ::", mesg.substring(start, end));
                }
            } else {
                Log.i("Print ::", mesg);
            }
        }
    }



    public static void setDateTimeField(Context context, final TextView dateText) {
        setDateTimeField(context, dateText, null);
    }

    public static void setDateTimeField(Context context, final TextView dateText, final DateSetListener listner) {
        final Calendar newCalendar = Calendar.getInstance();

        int month = newCalendar.get(Calendar.MONTH);
        int year = newCalendar.get(Calendar.YEAR);
        int day = newCalendar.get(Calendar.DAY_OF_MONTH);

        if (dateText.getText().toString().trim().contains("-")) //"yyyy-MM-dd"
        {
            month = Integer.parseInt(dateText.getText().toString().trim().split("-")[1]) - 1;
            year = Integer.parseInt(dateText.getText().toString().trim().split("-")[2]);
            day = Integer.parseInt(dateText.getText().toString().trim().split("-")[0]);

        } else if (dateText.getText().toString().trim().contains("/"))//"yyyy/MM/dd"
        {
            month = Integer.parseInt(dateText.getText().toString().trim().split("/")[1]) - 1;
            year = Integer.parseInt(dateText.getText().toString().trim().split("/")[2]);
            day = Integer.parseInt(dateText.getText().toString().trim().split("/")[0]);

        }
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                dateText.setText(Utils.pad(dayOfMonth) + "-" + Utils.pad((monthOfYear + 1)) + "-" + Utils.pad(year));
                if (listner != null)
                    listner.onDateSet(view, year, monthOfYear, dayOfMonth);

            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
//        dateText.setText(Utils.pad(newCalendar.get(Calendar.YEAR)) + "-" + Utils.pad((newCalendar.get(Calendar.MONTH)+1))  + "-" + Utils.pad(newCalendar.get(Calendar.DAY_OF_MONTH)));
    }


    /*public static void logOut(Context context) {
        Pref.setStringValue(context, Const.PREF_USERID, "");
        Pref.setStringValue(context, Const.PREF_LANGUAGE, Const.EN);
        Pref.setStringValue(context, Const.PREF_USERID, "");
        Pref.setStringValue(context, Const.PREF_USER_EMAIL, "");
        Pref.setStringValue(context, Const.PREF_USER_PROFILE_PIC,"");
        Pref.setStringValue(context, Const.PREF_USER_TYPE, "");
        Pref.setBooleanValue(context, Const.PREF_IS_LOGIN, false);
        Pref.setStringValue(context, Const.PREF_GCMTOKEN, "");
    }
*/

    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();

        DisplayMetrics dm = res.getDisplayMetrics();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Pref.setStringValue(context.getApplicationContext(), Const.PREF_LANGUAGE, lang);

        /*  Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conf.setLocale(myLocale);
            LocaleList localeList = new LocaleList(myLocale);
            LocaleList.setDefault(localeList);
            conf.setLocales(localeList);
            context = context.createConfigurationContext(conf);
            res.updateConfiguration(conf, res.getDisplayMetrics());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(myLocale);
            context = context.createConfigurationContext(conf);
            res.updateConfiguration(conf, res.getDisplayMetrics());
        } else {
            conf.locale = myLocale;
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }
        Pref.setStringValue(context.getApplicationContext(), Const.PREF_LANGUAGE, lang);*/
    }


    public static void print(Exception e) {
        if (Utils.DO_SOP) {
            e.printStackTrace();
        }
    }

    /*Check Network Status  : Connect with internet or not
     * if isOnline function return true means connect with internet well
      * */
   public static boolean isOnline(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = conMgr.getActiveNetworkInfo();
            if (info != null && info.isConnected())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /* Find index of Sting from String Array*/
    public static int indexOfStringArray(String[] strArray, String strFind) {
        int index;

        for (index = 0; index < strArray.length; index++)
            if (strArray[index].equals(strFind))
                break;

        return index >= strArray.length ? 0 : index;
    }
  /*  public static int indexOfStringArrayForCountryCode(List<CountryModel> strArray, String strFind) {
        int index = 0;

        for (index = 0; index < strArray.size(); index++)
            if (strArray.get(index).countryCode.equals(strFind.split(" ")[0]))
                break;

        return index >= strArray.size() ? 0 : index;
    }

*/
    /* Convert millisecond to Data  */
    public static String millisToDate(long millis, String format) {
        return new SimpleDateFormat(format).format(new Date(millis));
    }


    public static Date convertStringToDate(String strDate, String parseFormat) {
        try {
            if (!strDate.isEmpty())
                return new SimpleDateFormat(parseFormat).parse(strDate);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateStringToString(String strDate,
                                                   String currentFormat, String parseFormat) {
        try {
            if (strDate != null && !strDate.isEmpty())
                return convertDateToString(
                        convertStringToDate(strDate, currentFormat), parseFormat);
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(Date objDate, String parseFormat) {
        try {

            return new SimpleDateFormat(parseFormat).format(objDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /* get Day difference form current time to specific date */
    public static int getDaysDifference(Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - System.currentTimeMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        daysdiff = (int) diffDays;
        return daysdiff;
    }

/* Use This Function to maintain stack of Open Activity*/

    public static void addActivities(String key, Activity _activity) {
        if (Const.screen_al == null)
            Const.screen_al = new HashMap<String, Activity>();
        if (_activity != null)
            Const.screen_al.put(key, _activity);
    }

    /* Close All Open activity from back stack */
    public static void closeAllScreens() {
        closeAllScreens("");
    }


    /* Close Specific Open activity from back stack */
    public static void closeAllScreens(String key) {

        if (Const.screen_al == null || Const.screen_al.size() <= 0)
            return;
        if (key != null && !key.equalsIgnoreCase("")) {
            if (Const.screen_al.get(key) != null)
                Const.screen_al.get(key).finish();
        } else {
            for (Iterator<Map.Entry<String, Activity>> it = Const.screen_al
                    .entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Activity> entry = it.next();

                if (entry.getValue() != null) {
                    entry.getValue().finish();
                    it.remove();
                }

            }
        }
    }

    /* Use this function to make two digit of date or time */

    public static String pad(int c) {
        return c >= 10 ? String.valueOf(c) : "0" + String.valueOf(c);
    }

    public static String removePad(int c) {
        return c >= 10 ? String.valueOf(c) : (String.valueOf(c).length() > 1 ? String.valueOf(c).substring(1) : String.valueOf(c));
    }

//    public static void logOut(Context context) {
//        Pref.removeValue(context, Const.PREF_USERID);
//        Pref.removeValue(context, Const.PREF_LANGUAGE);
//        Pref.removeValue(context, Const.PREF_USER_EMAIL);
//        Pref.removeValue(context, Const.PREF_USER_NAME);
//        Pref.removeValue(context, Const.PREF_USER_BIRTH_DATE);
//        Pref.removeValue(context, Const.PREF_USER_PROFILE_PIC);
//        Pref.removeValue(context, Const.PREF_ISLOGIN);
//        Pref.removeValue(context, Const.PREF_ISVERIFY);
//        Pref.removeValue(context, Const.PREF_IS_TUTORIAL);
//
//    }

/* This Function User for apply Font in app*/

    public static void applyFontFace(Context context, final View view) {
        try {
            if (view instanceof ViewGroup) {

                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    try {


                        applyFontFace(context, child);
                    } catch (NullPointerException e) {
                        continue;
                    }

                }
            } else if (view instanceof CheckBox) {
                if (view.getTag() == null)
                    return;

                if (Integer.parseInt(view.getTag().toString()) == 100)
                    ((CheckBox) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 200)//BOLD //UBUNTU-M.TTF
                    ((CheckBox) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 300)
                    ((CheckBox) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-L.TTF"));

            } else if (view instanceof Button) {
                if (view.getTag() == null)
                    return;
                if (Integer.parseInt(view.getTag().toString()) == 100)
                    ((Button) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 200)//BOLD
                    ((Button) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 300)
                    ((Button) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-L.TTF"));


            } else if (view instanceof EditText) {
                if (view.getTag() == null)
                    return;
                if (Integer.parseInt(view.getTag().toString()) == 100)
                    ((EditText) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 200)//BOLD
                    ((EditText) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 300)
                    ((EditText) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-L.TTF"));
            } else if (view instanceof TextView) {
                if (view.getTag() == null)
                    return;

                if (view.getTag().equals("GoogleCopyrights"))
                    return;
                if (Integer.parseInt(view.getTag().toString()) == 100)
                    ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 200) //BOLD
                    ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-R.TTF"));
                else if (Integer.parseInt(view.getTag().toString()) == 300)
                    ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), "UBUNTU-L.TTF"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showLog(String tag, String msg) {
        Log.e(tag, msg);
    }
    public static String setSharedPreBoolean(Context context, String key, boolean text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(key, text);
        editor.apply();
        return key;
    }
   /* public static String[] convertStringArray() {


        String[] objList = new String[Const.countryCurrencyList.size()];

        for (int index = 0; index < Const.countryCurrencyList.size(); index++)
            objList[index] = Const.countryCurrencyList.get(index).countryCode + " " + Const.countryCurrencyList.get(index).countryName;

        return objList;
    }*/
    /*public static Dialog countryPicker(Context context, final ImageView img, final TextView txtCountryCode, final ResponseListener listnerer) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        if (Const.countryCurrencyList == null) {
            Const.countryCurrencyList = new ArrayList<CountryModel>(Arrays.asList(Pref.getStringValue(context, Const.PREF_LANGUAGE, Const.EN ).equalsIgnoreCase(Const.EN) ? Const.CURRENCYANDCOUNTRY : Const.CURRENCYANDCOUNTRY_AR));
            Collections.sort(Const.countryCurrencyList, new Comparator<CountryModel>() {
                public int compare(CountryModel o1, CountryModel o2) {
                    return o1.countryName.trim().compareTo(o2.countryName.trim());
                }
            });
//        }
        String[] stringArray = Utils.convertStringArray();

        builder.setSingleChoiceItems(stringArray, Utils.indexOfStringArrayForCountryCode(Const.countryCurrencyList, txtCountryCode.getText().toString()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                txtCountryCode.setText(Const.countryCurrencyList.get(index).countryCode);
                img.setImageResource(Const.countryCurrencyList.get(index).image);
                if (listnerer != null)
                    listnerer.onResponse(Const.COUNTRY_PIKER, Const.API_RESULT.SUCCESS,index);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }*/
   public static void showProgress(Context context) {
        try {
            if (Const.custDailog != null && Const.custDailog.isShowing())
                Const.custDailog.dismiss();

            if (Const.custDailog == null)
                Const.custDailog = new CustomDialog(context);
            Const.custDailog.setCancelable(false);
            Const.custDailog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public static void dismissProgress() {
        if (Const.custDailog != null && Const.custDailog.isShowing())
            Const.custDailog.dismiss();
        Const.custDailog = null;
    }

    public static void hideKeyBoard(Context context, View view) {
        try {
            if (context == null)
                return;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyBoard(Context context) {
        try {
            if (context == null)
                return;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToastMessage(Context context, String mgs, boolean isShort) {
        Toast.makeText(context, mgs, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }


    public static void resizeDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public static void resizeFullDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }


    public static String getTextValue(View view) {
        if (view instanceof EditText)
            return ((EditText) view).getText().toString().trim();
        else if (view instanceof TextView)
            return ((TextView) view).getText().toString().trim();
        else
            return "";

    }


    public static String getTextValueString(String view) {
        if (view instanceof String)
            return ((String) view).trim();
        else
            return "";

    }

    public static boolean isEmptyText(View view) {
        if (view == null)
            return true;
        else
            return getTextValue(view).equalsIgnoreCase("");


    }


    public static void printHashKey(Context ctx) {

        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(
                    "com.cyclo", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("Utils :: ",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Utils :: ", "" + e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("Utils :: ", "" + e);
        }

    }

/*    public static String getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 1, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return getRealPathFromURI(Uri.parse(path), context);
    }

    public static String getRealPathFromURI(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }*/

    public static void viewHidePasswordToggle(EditText textView, ImageView imageView) {
        if (imageView.getTag() == null)
            imageView.setTag("0");
        if (imageView.getTag().equals("1")) {
            imageView.setTag("0");
//            imageView.setImageResource(R.drawable.hide);

            textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            textView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            imageView.setTag("1");
//            imageView.setImageResource(R.drawable.show);
//            textView.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());


        }
        textView.setSelection(textView.getText().toString().length());
    }


    public static String getText(View view) {
        if (view instanceof EditText)
            return ((EditText) view).getText().toString().trim();
        else if (view instanceof TextView)
            return ((TextView) view).getText().toString().trim();
        else
            return "";

    }

    public static String timeMillisToDate(long timeMillis, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return formatter.format(calendar.getTime());
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static final int PERMISSION_REQUEST_CODE = 1;

    public static boolean checkpermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                return true;
            } else {
                return false;
            }
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            return true;
        }
    }


    public static boolean checkAllpermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
                return true;
            } else {
                return false;
            }
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    public static boolean checkPermission(Activity activity) {
        int resultWrite = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (resultWrite == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public static void requestPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Please allow write external Storage", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }



    public static String saveImage(Bitmap newBitmap, Context context) {

        String savePath = "";
        File imageFile = null;
        try {
            imageFile = File.createTempFile("tempFile", ".jpg", context.getExternalCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
            imageFile = new File("/mnt/sdcard/tempFitFile.jpg");
        }
        if (imageFile.exists())
            imageFile.delete();

        savePath = imageFile.getAbsolutePath();
//        Utils.print("===========file >>> " + savePath + ">>>" + imageFile.getAbsolutePath());
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return savePath;
    }

/*
    private static AmazonS3Client sS3Client;
    private static CognitoCachingCredentialsProvider sCredProvider;
    private static TransferUtility sTransferUtility;


    private static CognitoCachingCredentialsProvider getCredProvider(Context context) {
        if (sCredProvider == null) {
            sCredProvider = new CognitoCachingCredentialsProvider(
                    context.getApplicationContext(),
                    Const.COGNITO_POOL_ID,
                    Regions.fromName(Const.COGNITO_POOL_REGION));
        }

        return sCredProvider;
    }
    public static AmazonS3Client getS3Client(Context context) {
        if (sS3Client == null) {
            sS3Client = new AmazonS3Client(getCredProvider(context.getApplicationContext()));
            sS3Client.setRegion(Region.getRegion(Regions.fromName(Const.BUCKET_REGION)));
        }
        return sS3Client;
    }

    public static TransferUtility getTransferUtility(Context context) {
        if (sTransferUtility == null) {
            sTransferUtility = new TransferUtility(getS3Client(context.getApplicationContext()),
                    context.getApplicationContext());
        }

        return sTransferUtility;


    }*/

    public static boolean hasNavBar (Resources resources)
    {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }
    public static  int getNavigationBarHeight(Context context) {

        Resources resources = context.getResources();

        int resourceId = hasNavBar(resources)?resources.getIdentifier("navigation_bar_height", "dimen", "android"):0;
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

   /* public static void getLocationFromAddress(Context context, final String strAddress, String title, String placeid, ResponseListener listener) {
        try {
            Utils.showProgress(context);
            new GetLatLongFromAddressAPI(listener).execute(strAddress,title,placeid);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
   /* public static Dialog setDialogImage(Context context, String path, int dummyPic) {
        final Dialog dialogImage = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImage.setContentView(R.layout.dailog_image);
        dialogImage.show();

        TouchImageView imgProfilePic1 = (TouchImageView) dialogImage.findViewById(R.id.imgProfilePic1);
        ImageView imgCancel = (ImageView) dialogImage.findViewById(R.id.imgCancel);

        imgProfilePic1.setImageResource(dummyPic);
        if (path != null && !path.isEmpty())
        {
            if (path.startsWith("http"))
                Glide.with(context).load(path).placeholder(dummyPic).error(dummyPic).into(imgProfilePic1);
            else
                Glide.with(context).load(Uri.fromFile(new File(path))).placeholder(dummyPic).error(dummyPic).into(imgProfilePic1);

        }

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage.dismiss();
            }
        });
        return dialogImage;
    }
*/



}
