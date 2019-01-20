package com.kudubisa.app.navdrawertemplate.remote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by asus on 4/14/18.
 */

public class MyHTTPRequest extends AsyncTask<String, Void, String> {
    private static final String MY_HTTP_REQUEST = "MyHTTPRequest";

    @SuppressLint("StaticFieldLeak")
    Context mContext;
    JSONObject mJSONObject;
    MJSONParser mjsonParser;
    String mUrl;
    String method;
    HTTPResponse mHttpResponse;
    View mView;
    ProgressBar mProgressBar;

    public MyHTTPRequest(Context context, View view, String url, String method, JSONObject jsonObject,
                         HTTPResponse httpResponse, ProgressBar progressBar) {
        this.mContext = context;
        this.mView = view;
        this.mUrl = url;
        this.method = method;
        this.mJSONObject = jsonObject;
        this.mHttpResponse = httpResponse;
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String error = "HTTP Response Code is not 200 or 202";
        String body;

        int responseCode;

        try{

            URL url = new URL(Common.getApiUrl(this.mUrl));

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);

            if (method.equals("POST")) {
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                mjsonParser = new MJSONParser(mJSONObject);

                bufferedWriter.write(mjsonParser.getJSONObject());
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();
            }

            responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {
                Log.d(MY_HTTP_REQUEST, "response_code:" + responseCode);

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder= new StringBuilder();

                String stringLine;

                while (( stringLine = bufferedReader.readLine() ) != null) {
                    stringBuilder.append(stringLine);
                }

                Log.d(MY_HTTP_REQUEST, "HTTP Request result : " + stringBuilder.toString());

                body = stringBuilder.toString();

                return body;
            }

            return error;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(MY_HTTP_REQUEST,e.getMessage());

            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String body) {
        super.onPostExecute(body);

        mHttpResponse.response(body, mView);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public static abstract class HTTPResponse{
        public abstract void response(String body, View view);
    }
}
