package com.kudubisa.app.navdrawertemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudubisa.app.navdrawertemplate.AccountSettingsActivity;
import com.kudubisa.app.navdrawertemplate.EditProfileActivity;
import com.kudubisa.app.navdrawertemplate.MainActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Created by asus on 4/14/18.
 */

public class ProfileFragment extends Fragment{

    public ProfileFragment() {
    }

    private TextView tvProfileName, tvFullName, tvEmail, tvAddress, tvBirthDate, tvPregnancy, tvBloodType, tvHeight;
    private ImageView profilePhoto;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionbarTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePhoto = view.findViewById(R.id.profile_picture);
        tvProfileName = view.findViewById(R.id.profile_name);
        tvFullName = view.findViewById(R.id.tv_full_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddress = view.findViewById(R.id.tv_address);
        tvBirthDate = view.findViewById(R.id.tv_birth_date);
        tvPregnancy = view.findViewById(R.id.tv_pregnancy);
        tvHeight = view.findViewById(R.id.tv_height);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        FloatingActionButton fab_account_settings = (FloatingActionButton) view.findViewById(R.id.fab_edit_profile);
        fab_account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, EditProfileActivity.class);
                context.startActivity(intent);
            }
        });

        setFieldValues();

        return view;
    }

    private void setFieldValues(){
        progressBar.setVisibility(View.VISIBLE);
        JSONObject userJson;
        String userRaw = Common.getUserRaw(getContext());
        try {
            userJson = new JSONObject(userRaw);
            String fullName = userJson.getString("first_name")+" "+userJson.getString("last_name");
            String photoUrl = Common.getFullUrl(userJson.getString("photo"));

            Picasso.get().load(photoUrl).into(profilePhoto);

            tvProfileName.setText(userJson.getString("first_name"));
            tvFullName.setText(fullName);
            tvEmail.setText(userJson.getString("email"));
            tvAddress.setText(userJson.getString("address"));
            tvBirthDate.setText(userJson.getString("birth_date"));

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDate dateStart = LocalDate.parse(userJson.getString("pregnancy_start_at"));
            LocalDate dateEnd = currentTime.toLocalDate();

            int pregnancyAge = Common.getWeeksFromTwoDate(dateStart, dateEnd);

            tvPregnancy.setText(String.valueOf(pregnancyAge) + " Week(s)");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setFieldValues();
    }
}
