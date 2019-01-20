package com.kudubisa.app.navdrawertemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.squareup.picasso.Picasso;

public class TipsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_detail);

        Intent intent = getIntent();

        ProgressBar progressBar = findViewById(R.id.progressBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));

        TextView authorName = findViewById(R.id.author_name);
        authorName.setText(intent.getStringExtra("author_name"));
        TextView createdAt = findViewById(R.id.created_at);
        createdAt.setText(intent.getStringExtra("created_at"));
        TextView content = findViewById(R.id.content);
        content.setText(intent.getStringExtra("content"));
        ImageView picture = findViewById(R.id.picture);

        String photoUrl = intent.getStringExtra("photo");

        if (photoUrl.equals("null")) {
            picture.setImageDrawable(this.getDrawable(R.drawable.lisa));
        } else {
            Picasso.get().load(Common.getFullUrl(photoUrl)).into(picture);
        }

        progressBar.setVisibility(View.GONE);
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
