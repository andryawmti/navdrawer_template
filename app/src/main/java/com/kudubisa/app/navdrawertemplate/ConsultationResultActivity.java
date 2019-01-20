package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.model.Menu;
import com.kudubisa.app.navdrawertemplate.recycler.adapter.MenuSuggestionRecyclerAdapter;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 8/17/18.
 */

public class ConsultationResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private String calorie, weight, sleepTime, pregnancyAge, activity;
    private ProgressBar progressBar;

    private List<Menu> menuList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_result);
        context = getApplicationContext();

        Intent intent = getIntent();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        TextView tvCalorieNeed = findViewById(R.id.tvCalorieNeed);

        recyclerView = findViewById(R.id.rvMenuSuggestion);
        calorie = intent.getStringExtra("calorie");
        weight = intent.getStringExtra("weight");
        sleepTime = intent.getStringExtra("sleepTime");
        pregnancyAge = intent.getStringExtra("pregnancyAge");
        activity = intent.getStringExtra("activity");
        tvCalorieNeed.setText(calorie + " Kalori");

        saveResult();
    }

    private void saveResult(){
        String userId;
        JSONObject jsonParam = new JSONObject();
        try {
            JSONObject userJson = new JSONObject(Common.getUserRaw(context));
            userId = userJson.getString("id");
            jsonParam.put("user_id", userId);
            jsonParam.put("calorie_need", calorie);
            jsonParam.put("weight", weight);
            jsonParam.put("bed_time", sleepTime);
            jsonParam.put("pregnancy_age", pregnancyAge);
            jsonParam.put("activity", activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHTTPRequest httpRequest = new MyHTTPRequest(context, new View(context), "/consultation", "POST",
                jsonParam, response, progressBar);
        httpRequest.execute();
    }

    MyHTTPRequest.HTTPResponse response = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONObject result = new JSONObject(body);
                JSONArray menuSuggestion = result.optJSONArray("menu_suggestion");
                menuList = new ArrayList<>();

                for (int i = 0; i < menuSuggestion.length(); i++) {
                    JSONObject menuJson = menuSuggestion.getJSONObject(i);
                    Menu menu = new Menu();
                    menu.setAddedBy(menuJson.getString("admin_name"));
                    menu.setName(menuJson.getString("name"));
                    menu.setCalorie(menuJson.getString("calorie"));
                    menu.setCarbohydrate(menuJson.getString("carbohydrate"));
                    menu.setProtein(menuJson.getString("protein"));
                    menu.setFat(menuJson.getString("fat"));
                    menu.setPhoto(menuJson.getString("photo"));
                    menu.setCreatedAt(menuJson.getString("created_at"));
                    menu.setUpdatedAt(menuJson.getString("updated_at"));
                    menuList.add(menu);
                    initRecyclerView();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private void initRecyclerView() {
        MenuSuggestionRecyclerAdapter adapter = new MenuSuggestionRecyclerAdapter(this, menuList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
