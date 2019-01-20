package com.kudubisa.app.navdrawertemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.kudubisa.app.navdrawertemplate.ConsultationAcitivity;
import com.kudubisa.app.navdrawertemplate.MainActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.remote.Common;

/**
 * Created by asus on 8/5/18.
 */

public class HomeFragment extends Fragment
        implements View.OnClickListener{

    private RecyclerView recyclerView;
    private Common common;
    private Context context;
    private ImageButton imBtnConsultation, imgBtnMenu, imBtnProfile, imBtnTips;

    public HomeFragment() {
        common = new Common();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionbarTitle("Home");
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnStartConsultation = (Button) view.findViewById(R.id.btnStartConsultation);
        imBtnConsultation = (ImageButton) view.findViewById(R.id.imgBtnConsultation);
        imgBtnMenu = (ImageButton) view.findViewById(R.id.imgBtnMenu);
        imBtnProfile = (ImageButton) view.findViewById(R.id.imgBtnProfile);
        imBtnTips = (ImageButton) view.findViewById(R.id.imgBtnTips);
        btnStartConsultation.setOnClickListener(this);
        imBtnConsultation.setOnClickListener(this);
        imgBtnMenu.setOnClickListener(this);
        imBtnProfile.setOnClickListener(this);
        imBtnTips.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.tips_list_recycler_view);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartConsultation:
                Intent intent = new Intent(getContext(), ConsultationAcitivity.class);
                startActivity(intent);
                break;
            case R.id.imgBtnConsultation:
                loadFragment(new ConsultationFragment());
                break;
            case R.id.imgBtnMenu:
                loadFragment(new MenuListFragment());
                break;
            case R.id.imgBtnProfile:
                loadFragment(new ProfileFragment());
                break;
            case R.id.imgBtnTips:
                loadFragment(new TipsFragment());
                break;
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "MY_FRAGMENT");
        ft.addToBackStack(null);
        ft.commit();
    }
}
