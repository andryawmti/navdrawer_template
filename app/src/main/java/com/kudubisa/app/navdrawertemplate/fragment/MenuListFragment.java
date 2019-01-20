package com.kudubisa.app.navdrawertemplate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kudubisa.app.navdrawertemplate.MainActivity;
import com.kudubisa.app.navdrawertemplate.R;
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
        progressBar = view.findViewById(R.id.progressBar);
        initMenuRecyclerView(view);
        return view;
    }

    private void initMenuRecyclerView(View view) {
        String URL = "/menu";
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

                JSONArray dataMenu = response.optJSONArray("menus");

                List<Menu> menuList = new ArrayList<>();

                for (int i = 0; i < dataMenu.length(); i++) {
                    JSONObject menuJson = dataMenu.optJSONObject(i);

                    Menu menu = new Menu();

                    menu.setId(menuJson.getString("id"));
                    menu.setAddedBy(menuJson.getString("added_by"));
                    menu.setName(menuJson.getString("name"));
                    menu.setDescription(menuJson.getString("description"));
                    menu.setCalorie(menuJson.getString("calorie"));
                    menu.setCarbohydrate(menuJson.getString("carbohydrate"));
                    menu.setProtein(menuJson.getString("protein"));
                    menu.setFat(menuJson.getString("fat"));
                    menu.setPhoto(menuJson.getString("photo"));
                    menu.setCreatedAt(menuJson.getString("created_at"));
                    menu.setUpdatedAt(menuJson.getString("updated_at"));

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