package com.kudubisa.app.navdrawertemplate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kudubisa.app.navdrawertemplate.R;

/**
 * Created by asus on 8/5/18.
 */

public class HelpFragment extends Fragment {

    public HelpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        return view;
    }
}
