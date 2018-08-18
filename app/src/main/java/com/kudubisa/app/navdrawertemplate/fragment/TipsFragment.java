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
    private Common common;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionbarTitle("Tips Untuk Ibu");
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.tips_list_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        common = new Common();
        context = getContext();
        initTipsRecyclerView(view);
        return view;
    }

    private void initTipsRecyclerView(View view) {
        JSONObject user = null;
        String apiToken = "";
        try {
            user = new JSONObject(common.getUserRaw(context));
            apiToken = user.getString("api_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "api/tips?api_token="+apiToken;
        JSONObject reqParams = new JSONObject();
        MyHTTPRequest myHTTPRequest = new MyHTTPRequest(context, view, url, "GET",
                reqParams, response, progressBar);

        myHTTPRequest.execute();

    }

    MyHTTPRequest.HTTPResponse response = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONObject jsonBody = new JSONObject(body);
                JSONArray tipsArray = jsonBody.optJSONArray("tips_list");
                tipsList = new ArrayList<>();
                for (int i = 0; i < tipsArray.length(); i++) {
                    Tips tips = new Tips();
                    JSONObject jsonTips = tipsArray.optJSONObject(i);
                    tips.setId(jsonTips.optString("id"));
                    tips.setAuthor(jsonTips.optString("author"));
                    tips.setTitle(jsonTips.optString("title"));
                    tips.setBody(jsonTips.optString("body"));
                    tips.setCreatedAt(jsonTips.optString("created_at"));
                    tips.setUpdatedAt(jsonTips.optString("updated_at"));
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
