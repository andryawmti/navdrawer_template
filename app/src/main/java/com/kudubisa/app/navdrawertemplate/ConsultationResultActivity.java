package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.recycler.adapter.MenuSuggestionRecyclerAdapter;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asus on 8/17/18.
 */

public class ConsultationResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private Intent intent;
    private TextView tvCalorieNeed;
    private String calorie, weight, sleepTime, pregnancyAge, activity;
    private ProgressBar progressBar;

    private Common common;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_result);
        context = getApplicationContext();
        intent = getIntent();
        common = new Common();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        tvCalorieNeed = (TextView) findViewById(R.id.tvCalorieNeed);
        recyclerView = (RecyclerView) findViewById(R.id.rvMenuSuggestion);
        calorie = intent.getStringExtra("calorie");
        weight = intent.getStringExtra("weight");
        sleepTime = intent.getStringExtra("sleepTime");
        pregnancyAge = intent.getStringExtra("pregnancyAge");
        activity = intent.getStringExtra("activity");
        tvCalorieNeed.setText(calorie+" Kalori");
        saveResult();
    }

    private void saveResult(){
        String userId = "";
        String apiToken = "";
        String url = "/api/consultation?api_token=";
        JSONObject jsonParam = new JSONObject();
        try {
            JSONObject userJson = new JSONObject(common.getUserRaw(context));
            userId = userJson.getString("id");
            apiToken = userJson.getString("api_token");
            url += apiToken;
            jsonParam.put("user_id", userId);
            jsonParam.put("calorie", calorie);
            jsonParam.put("weight", weight);
            jsonParam.put("sleep_time", sleepTime);
            jsonParam.put("pregnancy_age", pregnancyAge);
            jsonParam.put("activity", activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHTTPRequest httpRequest = new MyHTTPRequest(context, new View(context), url, "POST",
                jsonParam, response, progressBar);
        httpRequest.execute();
    }

    MyHTTPRequest.HTTPResponse response = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            Toast.makeText(context, body, Toast.LENGTH_LONG).show();
            initRecyclerView();
        }
    };

    private void initRecyclerView() {
        MenuSuggestionRecyclerAdapter adapter = new MenuSuggestionRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
