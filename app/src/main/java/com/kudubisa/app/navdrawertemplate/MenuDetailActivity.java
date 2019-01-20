package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.model.FoodIngredient;
import com.kudubisa.app.navdrawertemplate.recycler.adapter.FoodIngredientRecyclerAdapter;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 7/1/18.
 */

public class MenuDetailActivity extends AppCompatActivity {
    private Context context;

    private List<FoodIngredient> foodIngredientList;

    private ProgressBar progressBar;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        context = getApplicationContext();

        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.rvIngredientList);

        Intent intent = getIntent();

        String menuId = intent.getStringExtra("menu_id");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle(intent.getStringExtra("name"));

        TextView calorie = findViewById(R.id.tvCalorie);
        TextView carbohydrate = findViewById(R.id.tvCarbohydrate);
        TextView protein = findViewById(R.id.tvProtein);
        TextView fat = findViewById(R.id.tvFat);
        TextView description = findViewById(R.id.tvDescription);
        ImageView menuPicture= findViewById(R.id.picture);

        calorie.setText("Calorie : " + intent.getStringExtra("calorie") + " KKal");
        carbohydrate.setText("Carbohydrate : " + intent.getStringExtra("carbohydrate") + " gr");
        protein.setText("Protein : " + intent.getStringExtra("protein") + " gr");
        fat.setText("Fat : " + intent.getStringExtra("fat") + " gr");
        description.setText(intent.getStringExtra("description"));
        String picture = intent.getStringExtra("photo");

        if (!picture.equals("null")) {
            String pictureUrl = Common.getFullUrl(picture);
            Picasso.get().load(pictureUrl).into(menuPicture);
        } else {
            menuPicture.setImageDrawable(getDrawable(R.drawable.cake));
        }

        getMenuDetail(menuId);
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

    private void getMenuDetail(String menuId) {

        JSONObject params = new JSONObject();
        MyHTTPRequest myHTTPRequest = new MyHTTPRequest(this, new View(this),
                "/menu/" + menuId, "GET", params, new MyHTTPRequest.HTTPResponse() {
                @Override
                public void response(String body, View view) {
                    try {
                        JSONObject menu = new JSONObject(body);
                        JSONArray ingredients = menu.optJSONArray("menu_items");

                        foodIngredientList = new ArrayList<>();

                        FoodIngredient foodIngredient;

                        for (int i = 0; i < ingredients.length(); i++) {
                            JSONObject ingredient = ingredients.optJSONObject(i);
                            foodIngredient = new FoodIngredient();
                            foodIngredient.setName(ingredient.getString("food_ingredient_name"));
                            foodIngredient.setCategoryName(ingredient.getString("food_ingredient_category_name"));
                            foodIngredient.setWeight(ingredient.getString("food_ingredient_weight"));
                            foodIngredientList.add(foodIngredient);
                        }

                        FoodIngredientRecyclerAdapter adapter = new FoodIngredientRecyclerAdapter(foodIngredientList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
        progressBar);

        myHTTPRequest.execute();
    }
}