package com.kudubisa.app.navdrawertemplate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kudubisa.app.navdrawertemplate.R;

/**
 * Created by asus on 8/17/18.
 */

public class ConsultationFragment extends Fragment {

    private View view;

    public ConsultationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consultation, container, false);
        return view;
    }
}
