package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.fragment.HomeFragment;
import com.kudubisa.app.navdrawertemplate.fragment.HelpFragment;
import com.kudubisa.app.navdrawertemplate.fragment.MenuListFragment;
import com.kudubisa.app.navdrawertemplate.fragment.ProfileFragment;
import com.kudubisa.app.navdrawertemplate.fragment.TipsFragment;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    ProgressBar progressBar;

    SharedPreferences preferences;
    private final static String LOGIN_PREFS = "login_prefs";
    private final static String EMAIL = "email";
    private final static String PASSWORD = "password";

    Context context = MainActivity.this;

    ImageView profilePicture;
    TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        drawerLayout = findViewById(R.id.drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            VectorDrawableCompat indicator = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu_black_24dp, getTheme());

            if (indicator != null) {

                indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));

                supportActionBar.setHomeAsUpIndicator(indicator);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        NavigationView navView = findViewById(R.id.nav_view);

        View headerView = navView.getHeaderView(0);

        profilePicture = headerView.findViewById(R.id.navheader_profile);
        profileName = headerView.findViewById(R.id.navheader_profile_name);

        setNavHeaderProfile();

        navView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        loadFragment(new HomeFragment());
    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = new Fragment();
            item.setChecked(true);
            switch (item.getItemId()){
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.profile_settings:
                    fragment = new ProfileFragment();
                    break;
                case R.id.logout:
                    logout();
                    break;
                case R.id.menu_list:
                    fragment = new MenuListFragment();
                    break;
                case R.id.consultation:
                    fragment = new HomeFragment();
                    break;
                case R.id.help:
                    fragment = new HelpFragment();
                    break;
                case R.id.tips:
                    fragment = new TipsFragment();
                    break;
            }

            loadFragment(fragment);

            drawerLayout.closeDrawers();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        int x = menu.size();

        Log.d("size:", String.valueOf(x));

        for (int i = 0; i < x; i++) {
            Drawable icon = menu.getItem(i).getIcon();
            icon.mutate();
            icon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            return true;
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment){
        hideWelcome();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.content_frame, fragment, "MY_FRAGMENT");

        ft.addToBackStack(null);

        ft.commit();
    }

    private void hideWelcome(){
        RelativeLayout relContenFrame = findViewById(R.id.rel_content_frame);
        relContenFrame.setVisibility(View.GONE);
    }

    private void logout(){
        modifyPreferences();
        ifLogoutSuccess();
    }

    private void ifLogoutSuccess(){
        Context context = MainActivity.this;
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        MainActivity.this.finish();

    }

    private void modifyPreferences(){
        Common.setEmail("", this);
        Common.setPassword("", this);
    }

    public void setActionbarTitle(String title){
        /*getSupportActionBar().setTitle(title);*/
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    public void setNavHeaderProfile() {
        try {
            JSONObject jsonObject = new JSONObject(Common.getUserRaw(context));
            String photoPath = jsonObject.getString("photo");

            if (!photoPath.equals("null")) {
                Glide.with(context).load(Common.getFullUrl(photoPath)).into(profilePicture);
            }

            String fullName = jsonObject.getString("first_name") + " " + jsonObject.getString("last_name");
            profileName.setText(fullName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
