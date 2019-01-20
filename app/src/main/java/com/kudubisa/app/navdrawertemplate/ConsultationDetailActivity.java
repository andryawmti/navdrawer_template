package com.kudubisa.app.navdrawertemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
 * Created by asus on 8/19/18.
 */

public class ConsultationDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Intent intent;
    private TextView tvCalorieNeed;

    private List<Menu> menuList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_detail);

        intent = getIntent();

        tvCalorieNeed = findViewById(R.id.tvCalorieNeed);
        tvCalorieNeed.setText(intent.getStringExtra("calorie"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Konsultasi");

        recyclerView = findViewById(R.id.rvMenuSuggestion);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String consultationId = intent.getStringExtra("consultation_id");

        initRecyclerView(consultationId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(String consultationId) {
        String url = "/consultation/" + consultationId;

        JSONObject jsonParam = new JSONObject();
        MyHTTPRequest httpRequest = new MyHTTPRequest(this, new View(this), url, "GET", jsonParam, response, progressBar);
        httpRequest.execute();
    }

    MyHTTPRequest.HTTPResponse response = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONObject result = new JSONObject(body);
                JSONArray menus = result.optJSONArray("menu_list");

                menuList = new ArrayList<>();

                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menuJson = menus.getJSONObject(i);
                    Menu menu = new Menu();
                    menu.setName(menuJson.getString("name"));
                    menu.setAddedBy(menuJson.getString("added_by"));
                    menu.setDescription(menuJson.getString("description"));
                    menu.setPhoto(menuJson.getString("photo"));
                    menu.setCreatedAt(menuJson.getString("created_at"));
                    menu.setCalorie(menuJson.getString("calorie"));
                    menu.setCarbohydrate(menuJson.getString("carbohydrate"));
                    menu.setProtein(menuJson.getString("protein"));
                    menu.setFat(menuJson.getString("fat"));
                    menu.setId(menuJson.getString("id"));
                    menuList.add(menu);
                }

                MenuSuggestionRecyclerAdapter adapter = new MenuSuggestionRecyclerAdapter(getApplicationContext(), menuList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
