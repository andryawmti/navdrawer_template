package com.kudubisa.app.navdrawertemplate.remote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by asus on 4/17/18.
 */

public class Common {
    private static final String HOST = "https://wahid.kudubisa.com";
    private static final String API = "/api";
    private static final String API_TOKEN = "6HkM4RSWUCeUkbX5LNyMvw6tbCOiqMtYaK1c0HiVOFh9DK5Ozezu3pIjBl7V";

    private final static String LOGIN_PREFS = "login_prefs";
    private final static String EMAIL = "email";
    private final static String PASSWORD = "password";
    private final static  String USER_RAW = "user_raw";

    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getHost(){
        return HOST;
    }

    public static String getFullUrl(String url){
        return HOST+url;
    }

    public static String getUserRaw(Context context){
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        return preferences.getString(USER_RAW,"");
    }

    public static void setUserRaw(String userRaw, Context context){
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_RAW, userRaw);
        editor.apply();
    }

    public static void setEmail(String email, Context context){
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public static void setPassword(String password, Context context){
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static String getApiUrl(String url) {
        return HOST + API + url + "?api_token=" + API_TOKEN;
    }

    @SuppressLint("NewApi")
    public static int getWeeksFromTwoDate(LocalDate dateStart, LocalDate dateEnd) {

        /*LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("Current DateTime : " + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1: " + date1);

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("Month : " + month + ", Day: " + day + ", seconds: " + seconds);

        LocalDateTime date2 = currentTime.withDayOfMonth(1).withYear(2019);
        System.out.println("date2: " + date2);

        LocalDate date3 = LocalDate.of(2016, Month.APRIL, 15);
        System.out.println("date3: " + date3);

        LocalTime time1 = LocalTime.of(22, 24);
        System.out.println("time1: " + time1);

        LocalTime time2 = LocalTime.parse("20:15:14");
        System.out.println("time2 : " + time2);*/

        /*For more information visit this url : https://www.tutorialspoint.com/java8/java8_datetime_api.htm*/

        return  (int) ChronoUnit.WEEKS.between(dateStart, dateEnd);
    }
}
