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
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.MainActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.Consultation;
import com.kudubisa.app.navdrawertemplate.recycler.adapter.ConsultationRecyclerAdapter;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 8/17/18.
 */

public class ConsultationFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Common common;
    private Context context;
    private List<Consultation> consultationList;
    public ConsultationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionbarTitle("Riwayat Konsultasi");
        common = new Common();
        context = getContext();
        view = inflater.inflate(R.layout.fragment_consultation, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvConsultationList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        String url = "/api/consultations";
        String userId = "";
        String apiToken = "";
        try {
            JSONObject userJson = new JSONObject(common.getUserRaw(context));
            userId = userJson.getString("id");
            apiToken = userJson.getString("api_token");
            url += "/"+userId+"?api_token="+apiToken;
            JSONObject jsonParam = new JSONObject();
            MyHTTPRequest httpRequest = new MyHTTPRequest(context, view, url, "GET", jsonParam, response, progressBar);
            httpRequest.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    MyHTTPRequest.HTTPResponse response = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONObject jsonBody = new JSONObject(body);
                JSONArray jsonArray = jsonBody.optJSONArray("consultation_list");
                consultationList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    Consultation consultation = new Consultation();
                    consultation.setId(jsonObject.getString("id"));
                    consultation.setUserId(jsonObject.getString("user_id"));
                    consultation.setActivity(jsonObject.getString("activity"));
                    consultation.setCalorie(jsonObject.getString("calorie"));
                    consultation.setPregnancyAge(jsonObject.getString("pregnancy_age"));
                    consultation.setSleepTime(jsonObject.getString("sleep_time"));
                    consultation.setWeight(jsonObject.getString("weight"));
                    consultation.setCreatedAt(jsonObject.getString("created_at"));
                    consultation.setUpdatedAt(jsonObject.getString("updated_at"));
                    consultationList.add(consultation);
                }

                ConsultationRecyclerAdapter adapter = new ConsultationRecyclerAdapter(consultationList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
