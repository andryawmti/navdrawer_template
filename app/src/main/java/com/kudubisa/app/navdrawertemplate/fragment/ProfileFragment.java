package com.kudubisa.app.navdrawertemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kudubisa.app.navdrawertemplate.AccountSettingsActivity;
import com.kudubisa.app.navdrawertemplate.EditProfileActivity;
import com.kudubisa.app.navdrawertemplate.R;

/**
 * Created by asus on 4/14/18.
 */

public class ProfileFragment extends Fragment{
    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FloatingActionButton fab_account_settings = (FloatingActionButton) view.findViewById(R.id.fab_edit_profile);
        fab_account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, EditProfileActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
