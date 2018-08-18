package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kudubisa.app.navdrawertemplate.recycler.adapter.MenuSuggestionRecyclerAdapter;

/**
 * Created by asus on 8/17/18.
 */

public class ConsultationResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private Intent intent;
    private TextView tvCalorieNeed;
    private String calorie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_result);
        context = getApplicationContext();
        intent = getIntent();
        tvCalorieNeed = (TextView) findViewById(R.id.tvCalorieNeed);
        recyclerView = (RecyclerView) findViewById(R.id.rvMenuSuggestion);
        calorie = intent.getStringExtra("calorie");
        tvCalorieNeed.setText(calorie+" Kalori");
        initRecyclerView();
    }

    private void initRecyclerView() {
        MenuSuggestionRecyclerAdapter adapter = new MenuSuggestionRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
