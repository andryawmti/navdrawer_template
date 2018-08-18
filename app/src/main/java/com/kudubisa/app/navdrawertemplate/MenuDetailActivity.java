package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.remote.Common;

import org.w3c.dom.Text;

/**
 * Created by asus on 7/1/18.
 */

public class MenuDetailActivity extends AppCompatActivity {
    private Context context;
    private Common common;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        context = getApplicationContext();
        common = new Common();
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));

        TextView calorie = (TextView) findViewById(R.id.calorie);
        TextView desc = (TextView) findViewById(R.id.description);
        TextView cooking = (TextView) findViewById(R.id.cooking_instruction);
        ImageView menuPicture= (ImageView) findViewById(R.id.picture);

        calorie.setText(intent.getStringExtra("calorie"));
        desc.setText(intent.getStringExtra("desc"));
        cooking.setText(intent.getStringExtra("cooking"));

        String picture = intent.getStringExtra("picture");
        if (!picture.equals("null")){
            String pictureUrl = common.getFullUrl(picture);
            Glide.with(context).load(pictureUrl).into(menuPicture);
        }
        menuPicture.setImageDrawable(getDrawable(R.drawable.cake));

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
}