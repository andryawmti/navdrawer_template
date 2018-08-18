package com.kudubisa.app.navdrawertemplate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.MainActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.Ingredient;
import com.kudubisa.app.navdrawertemplate.model.Menu;
import com.kudubisa.app.navdrawertemplate.recycler.adapter.MenuRecyclerAdapter;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 6/5/18.
 */

public class MenuListFragment extends Fragment {
    private List<Menu> menuList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Common common = new Common();

    public MenuListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionbarTitle("Daftar Menu");
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        recyclerView = view.findViewById(R.id.menu_list_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        initMenuRecyclerView(view);
        return view;
    }

    private void initMenuRecyclerView(View view) {
        JSONObject user = null;
        String apiToken = "";
        try {
            user = new JSONObject(common.getUserRaw(getContext()));
            apiToken = user.getString("api_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL = "/api/menus?api_token="+apiToken;
        JSONObject jsonObject = new JSONObject();
        MyHTTPRequest myHTTPRequest = new MyHTTPRequest(getContext(), view, URL,
                "GET", jsonObject, retrieveMenuResponse, progressBar);
        myHTTPRequest.execute();
    }

    MyHTTPRequest.HTTPResponse retrieveMenuResponse = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONObject response = new JSONObject(body);
                JSONArray dataMenu = response.optJSONArray("menu_list");
                menuList = new ArrayList<>();
                for (int i = 0; i < dataMenu.length(); i++) {
                    JSONObject menuJson = dataMenu.optJSONObject(i);
                    Menu menu = new Menu();
                    menu.setId(menuJson.getInt("id"));
                    menu.setTitle(menuJson.getString("title"));
                    menu.setCalorie(menuJson.getString("calorie"));
                    menu.setIngredients(menuJson.getString("ingredients").replace("\\\\",""));
                    menu.setCookingInstruction(menuJson.getString("cooking_instruction"));
                    menu.setDescription(menuJson.getString("description"));
                    menu.setPicture(menuJson.getString("picture"));
                    menu.setCreatedAt(menuJson.getString("created_at"));
                    menu.setUpdatedat(menuJson.getString("updated_at"));
                    menuList.add(menu);
                }
                MenuRecyclerAdapter adapter = new MenuRecyclerAdapter(menuList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}