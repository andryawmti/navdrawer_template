package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.component.CooperMethod;
import com.kudubisa.app.navdrawertemplate.remote.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by asus on 8/17/18.
 */

public class ConsultationAcitivity extends AppCompatActivity implements
    Spinner.OnItemSelectedListener, View.OnClickListener{

    private double activityPercentage = 0;
    private int weight = 0;
    private int sleepTime = 0;
    private int pregnancyAge = 0;
    private TextView validationWarning;
    private EditText edWeight;
    private EditText edSleepTime;
    private EditText edPregnancyAge;
    private Button btnSubmit, btnCancel;

    private Context context;
    private Common common;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        context = getApplicationContext();
        common = new Common();

        edWeight = (EditText) findViewById(R.id.weight);
        edSleepTime = (EditText) findViewById(R.id.sleepingTime);
        edPregnancyAge = (EditText) findViewById(R.id.pregnancyAge);
        validationWarning = (TextView) findViewById(R.id.validationWarning);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        String[] momsActivity = {"Select Activity", "Bed Rest", "Sangat Ringan", "Ringan", "Sedang", "Berat"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item,
                momsActivity);

        Spinner activitySpinner = (Spinner) findViewById(R.id.activitySpinner);
        activitySpinner.setAdapter(spinnerAdapter);
        activitySpinner.setOnItemSelectedListener(this);

        /* Set pregnancy age */
        try {
            edPregnancyAge.setFocusable(false);
            edPregnancyAge.setClickable(false);
            JSONObject userJson = new JSONObject(Common.getUserRaw(context));
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDate dateStart = LocalDate.parse(userJson.getString("pregnancy_start_at"));
            LocalDate dateEnd = currentTime.toLocalDate();
            int weeks = Common.getWeeksFromTwoDate(dateStart, dateEnd);
            edPregnancyAge.setText(String.valueOf(weeks));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /* End of set pregnancy age */

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String activity = parent.getSelectedItem().toString();
        switch (activity) {
            case "Bed Rest" :
                activityPercentage = 0.1;
                break;
            case "Sangat Ringan" :
                activityPercentage = 0.3;
                break;
            case "Ringan" :
                activityPercentage = 0.5;
                break;
            case "Sedang" :
                activityPercentage = 0.7;
                break;
            case "Berat" :
                activityPercentage = 1;
                break;
            default :
                activityPercentage = 0;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (isFormValid()) {
                    startCooperCalculations();
                } else {
                    validationWarning.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btnCancel:
                this.finish();
                getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private boolean isFormValid() {
        if (edWeight.getText().toString().isEmpty()) return false;
        if (edPregnancyAge.getText().toString().isEmpty()) return false;
        if (edSleepTime.getText().toString().isEmpty()) return false;
        if (activityPercentage == 0) return false;
        return true;
    }

    private void startCooperCalculations() {
        JSONObject userJson;
        weight = Integer.parseInt(edWeight.getText().toString());
        sleepTime = Integer.parseInt(edSleepTime.getText().toString());
        pregnancyAge = Integer.parseInt(edPregnancyAge.getText().toString());
        try {
            userJson = new JSONObject(common.getUserRaw(context));
            CooperMethod cooperMethod = new CooperMethod();
            double bbih = cooperMethod.getBBIH(userJson.getInt("height"), pregnancyAge);
            double kTidur = cooperMethod.getKTidur(bbih, sleepTime);
            double aktifitas = cooperMethod.getAktifitas(activityPercentage, kTidur);
            double calorie = cooperMethod.getSda(aktifitas, 0.07);
            calorie = (int)Math.floor(calorie);
            validationWarning.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, ConsultationResultActivity.class);
            intent.putExtra("calorie", String.valueOf(calorie));
            intent.putExtra("weight", String.valueOf(weight));
            intent.putExtra("sleepTime", String.valueOf(sleepTime));
            intent.putExtra("pregnancyAge", String.valueOf(pregnancyAge));
            intent.putExtra("activity", String.valueOf(activityPercentage));
            startActivity(intent);
            this.finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
