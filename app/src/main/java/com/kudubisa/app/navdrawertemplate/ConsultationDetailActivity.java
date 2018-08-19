package com.kudubisa.app.navdrawertemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kudubisa.app.navdrawertemplate.recycler.adapter.MenuSuggestionRecyclerAdapter;

/**
 * Created by asus on 8/19/18.
 */

public class ConsultationDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Intent intent;
    private TextView tvCalorieNeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_detail);
        intent = getIntent();
        tvCalorieNeed = (TextView) findViewById(R.id.tvCalorieNeed);
        tvCalorieNeed.setText(intent.getStringExtra("calorie"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Konsultasi");
        recyclerView = (RecyclerView) findViewById(R.id.rvMenuSuggestion);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
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

    private void initRecyclerView() {
        MenuSuggestionRecyclerAdapter adapter = new MenuSuggestionRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
