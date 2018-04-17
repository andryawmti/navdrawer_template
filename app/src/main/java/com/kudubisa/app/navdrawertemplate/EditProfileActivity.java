package com.kudubisa.app.navdrawertemplate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.fragment.DatePickerFragment;
import com.kudubisa.app.navdrawertemplate.model.LoginCredentials;
import com.kudubisa.app.navdrawertemplate.model.User;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;
import com.mobsandgeeks.saripaar.DateFormats;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by asus on 4/15/18.
 */

public class EditProfileActivity extends AppCompatActivity {

    private final static String LOGIN_PREFS = "login_prefs";
    private final static String EMAIL = "email";
    private final static  String USER_RAW = "userRaw";
    private String updateProfileUrl = "/api/user/";

    SharedPreferences preferences;

    @NotEmpty(message = "First Name should not be empty")
    EditText etFirstName;

    @NotEmpty(message = "Last Name should not be empty")
    EditText etLastName;

    @NotEmpty(message = "Address should not be empty")
    EditText etAddress;

    @NotEmpty(message = "Weight should not be empty")
    EditText etWeight;

    @Email(message = "Email not valid")
    EditText etEmail;

    Validator validator;

    TextView tvBirthdate;
    TextView tvPregnancyStart;

    ProgressBar progressBar;

    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tvBirthdate = (TextView) findViewById(R.id.tvBirthdate);
        tvPregnancyStart = (TextView) findViewById(R.id.tvPregnancyStart);
        TextView tvEditFotoProfile = (TextView) findViewById(R.id.edit_foto_profile);

        RelativeLayout relEditAddress = (RelativeLayout) findViewById(R.id.rel_edit_address);
        RelativeLayout relEditEmail = (RelativeLayout) findViewById(R.id.rel_edit_email);
        RelativeLayout relEditPassword = (RelativeLayout) findViewById(R.id.rel_edit_password);
        RelativeLayout relEditBirthdate = (RelativeLayout) findViewById(R.id.relEditBirthDate);
        RelativeLayout relEditPregnancyStart = (RelativeLayout) findViewById(R.id.relEditPregnancyStart);

        etAddress = (EditText) findViewById(R.id.etAddress);
        etFirstName = (EditText) findViewById(R.id.etEditFirstName);
        etLastName = (EditText) findViewById(R.id.etEditLastName);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etEmail = (EditText) findViewById(R.id.etEmail);

        relEditAddress.setOnClickListener(onClickListener);
        relEditEmail.setOnClickListener(onClickListener);
        relEditPassword.setOnClickListener(onClickListener);
        relEditBirthdate.setOnClickListener(onClickListener);
        relEditPregnancyStart.setOnClickListener(onClickListener);

        tvEditFotoProfile.setOnClickListener(onClickListener);

        Button btnSave = (Button) findViewById(R.id.btnSaveProfile);
        view = btnSave;
        btnSave.setOnClickListener(onClickListener);

        validator = new Validator(this);
        validator.setValidationListener(validationListener);

        setDataField();
    }

    private void setDataField(){
        JSONObject userJson = getProfile();
        try {
            tvBirthdate.setText(userJson.getString("birth_date").substring(0,10));
            tvPregnancyStart.setText(userJson.getString("pregnancy_start_at").substring(0,10));

            etFirstName.setText( userJson.getString("first_name"));
            etLastName.setText(userJson.getString("last_name"));
            etWeight.setText(String.valueOf(userJson.getInt("weight")));
            etAddress.setText(userJson.getString("address"));
            etEmail.setText(userJson.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerFragment datePickerFragment;
            switch (view.getId()) {
                case R.id.relEditBirthDate:
                    datePickerFragment = DatePickerFragment.newInstance(R.id.tvBirthdate);
                    datePickerFragment.show(getFragmentManager(), "datePicker");
                    break;
                case R.id.relEditPregnancyStart:
                    datePickerFragment = DatePickerFragment.newInstance(R.id.tvPregnancyStart);
                    datePickerFragment.show(getFragmentManager(), "datePicker");
                    break;
                case R.id.edit_foto_profile:
                    break;
                case R.id.btnSaveProfile:
                    validator.validate();
                    break;
                case R.id.rel_edit_address:
                    showHideEditAddress(R.id.etAddress, R.id.ivEditAddress);
                    break;
                case R.id.rel_edit_email:
                    showHideEditAddress(R.id.etEmail, R.id.ivEditEmail);
                    break;
                case R.id.rel_edit_password:
                    Intent intent = new Intent(EditProfileActivity.this, EditPasswordActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHideEditAddress(int editText, int imgView){
        final EditText edText = (EditText) findViewById(editText);
        ImageView imView = (ImageView) findViewById(imgView);
        int v = edText.getVisibility();
        if (v == View.GONE) {
            Log.d("fuck", "you");
            imView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_keyboard_arrow_down_black_24dp, getTheme()));
            edText.setAlpha(0.0f);
            edText.animate()
                    .alpha(1.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            edText.setVisibility(View.VISIBLE);
                        }
                    });

        }else{
//            Log.d("fuck", "her");
//            imView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
//                    R.drawable.ic_chevron_right_black_24dp, getTheme()));
//            edText.setAlpha(1.0f);
//            edText.animate()
//                    .alpha(1.0f)
//                    .setDuration(300)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            edText.setVisibility(View.GONE);
//                        }
//                    });
        }
    }

    Validator.ValidationListener validationListener = new Validator.ValidationListener() {
        @Override
        public void onValidationSucceeded() {
            ifFormValid();
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(getApplicationContext());

                // Display error messages ;)
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    };

    private void ifFormValid(){
//        Toast.makeText(this, "Data is valid", Toast.LENGTH_SHORT).show();
        saveProfile();
    }

    private String getUserRaw(){
        preferences = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        return preferences.getString(USER_RAW,"");
    }

    private void setUserRaw(String userRaw){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, etEmail.getText().toString());
        editor.putString(USER_RAW, userRaw);
        editor.commit();
    }

    private JSONObject getProfile(){
        JSONObject user;
        try {
            user = new JSONObject(getUserRaw());
            updateProfileUrl += user.getString("id")+"?api_token="+user.getString("api_token");
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveProfile(){
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("first_name", etFirstName.getText().toString());
            userJson.put("last_name", etLastName.getText().toString());
            userJson.put("address", etAddress.getText().toString());
            userJson.put("email", etEmail.getText().toString());
            userJson.put("birth_date", tvBirthdate.getText().toString());
            userJson.put("pregnancy_start", tvPregnancyStart.getText().toString());
            userJson.put("weight", etWeight.getText().toString());

            MyHTTPRequest myHTTPRequest = new MyHTTPRequest(this, view, updateProfileUrl,
                    "POST", userJson, httpResponse, progressBar);
            myHTTPRequest.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    MyHTTPRequest.HTTPResponse httpResponse = new MyHTTPRequest.HTTPResponse() {
        @Override
        public void response(String body, View view) {
            try {
                JSONObject jsonObject = new JSONObject(body);
                setUserRaw(jsonObject.getString("user"));
                Toast.makeText(EditProfileActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
