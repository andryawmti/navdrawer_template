package com.kudubisa.app.navdrawertemplate.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kudubisa.app.navdrawertemplate.R;

/**
 * Created by asus on 8/5/18.
 */

@SuppressLint("ValidFragment")
public class ConsultationDialog extends DialogFragment {
    private ConsultaionDialogListener listener;
    private double activityPercentage = 0;
    private int weight = 0;
    private int sleepTime = 0;
    private int pregnancyAge = 0;
    private TextView validationWarning;
    private EditText edWeight;
    private EditText edSleepTime;
    private EditText edPregnancyAge;

    @SuppressLint("ValidFragment")
    public ConsultationDialog(ConsultaionDialogListener listener) {
        this.listener = listener;
    }

    public interface ConsultaionDialogListener {
        void onDialogPositiveClick(int pregnancyAge, int sleepTime, int weight, double activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_consultation, null);
        edWeight = (EditText) view.findViewById(R.id.weight);
        edSleepTime = (EditText) view.findViewById(R.id.sleepingTime);
        edPregnancyAge = (EditText) view.findViewById(R.id.pregnancyAge);
        validationWarning = (TextView) view.findViewById(R.id.validationWarning);

        String[] momsActivity = {"Pilih Aktivitas", "Bed Rest", "Sangat Ringan", "Ringan", "Sedang", "Berat"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                momsActivity);

        Spinner activitySpinner = (Spinner) view.findViewById(R.id.activitySpinner);
        activitySpinner.setAdapter(spinnerAdapter);
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edWeight.setText(getArguments().getString("weight"));
        edSleepTime.setText(getArguments().getString("sleepTime"));
        edPregnancyAge.setText(getArguments().getString("pregnancyAge"));

        weight = Integer.parseInt(edWeight.getText().toString());
        sleepTime = Integer.parseInt(edSleepTime.getText().toString());
        pregnancyAge = Integer.parseInt(edPregnancyAge.getText().toString());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(view);
        dialogBuilder.setTitle("Konsultasi");
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*if (isFormValid()) {
                    listener.onDialogPositiveClick(pregnancyAge, sleepTime, weight, activityPercentage);
                } else {
                    validationWarning.setVisibility(View.VISIBLE);
                }*/
            }
        });
        return dialogBuilder.create();
    }

    private boolean isFormValid() {
        if (edWeight.getText().toString().isEmpty()) return false;
        if (edPregnancyAge.getText().toString().isEmpty()) return false;
        if (edSleepTime.getText().toString().isEmpty()) return false;
        return true;
    }
}
