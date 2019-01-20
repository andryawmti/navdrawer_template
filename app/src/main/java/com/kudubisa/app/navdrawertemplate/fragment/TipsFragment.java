package com.kudubisa.app.navdrawertemplate.fragment;

import android.content.Context;
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
import com.kudubisa.app.navdrawertemplate.model.Tips;
import com.kudubisa.app.navdrawertemplate.recycler.adapter.TipsRecyclerAdapter;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 8/5/18.
 */

public class TipsFragment extends Fragment {
    private List<Tips> tipsList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionbarTitle("Tips Untuk Ibu");
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        recyclerView = view.findViewById(R.id.tips_list_recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        context = getContext();
        initTipsRecyclerView(view);
        return view;
    }

    private void initTipsRecyclerView(View view) {
        String url = "/article";
        JSONObject reqParams = new JSONObject();
        MyHTTPRequest myHTTPRequest = new MyHTTPRequest(context, view, url, "GET",
                reqParams, response, progressBar);

        myHTTPRequest.execute();

    }

    MyHTTPRequest.HTTPResponse response = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONArray tipsArray = new JSONArray(body);
                tipsList = new ArrayList<>();
                for (int i = 0; i < tipsArray.length(); i++) {
                    Tips tips = new Tips();
                    JSONObject jsonTips = tipsArray.optJSONObject(i);
                    tips.setId(jsonTips.getString("id"));
                    tips.setAuthorId(jsonTips.getString("admin_id"));
                    tips.setAuthorName(jsonTips.getString("admin_name"));
                    tips.setTitle(jsonTips.getString("title"));
                    tips.setBody(jsonTips.getString("content"));
                    tips.setPicture(jsonTips.getString("photo"));
                    tips.setCreatedAt(jsonTips.getString("created_at"));
                    tips.setUpdatedAt(jsonTips.getString("updated_at"));
                    tipsList.add(tips);
                }
                TipsRecyclerAdapter tipsRecyclerAdapter = new TipsRecyclerAdapter(tipsList, context);
                recyclerView.setAdapter(tipsRecyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setHasFixedSize(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
