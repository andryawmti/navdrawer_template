package com.kudubisa.app.navdrawertemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.remote.Common;
import com.kudubisa.app.navdrawertemplate.remote.MyHTTPRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by asus on 4/22/18.
 */

public class SignUpActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private Button btnSignup;

    private Validator validator;

    @NotEmpty(message = "First name is required")
    private EditText etFirstName;

    @NotEmpty(message = "Last name is required")
    private EditText etLastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    private EditText etEmail;

    @NotEmpty(message = "Password is required")
    @Password(message = "Password is not valid")
    private EditText etPassword;

    @NotEmpty(message = "Password Confirmation is required")
    @ConfirmPassword(message = "Password confirmation is not match")
    private EditText etConfirm;

    private View view;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignup = findViewById(R.id.btnSignup);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);

        validator = new Validator(this);
        validator.setValidationListener(validationListener);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        view = btnSignup;
        context = getApplicationContext();
    }

    Validator.ValidationListener validationListener = new Validator.ValidationListener() {
        @Override
        public void onValidationSucceeded() {
            signUp();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void signUp(){
        String url = "/user/signup";
        JSONObject userSignup = new JSONObject();
        try {
            userSignup.put("first_name", etFirstName.getText().toString());
            userSignup.put("last_name", etLastName.getText().toString());
            userSignup.put("email", etEmail.getText().toString());
            userSignup.put("password", etPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyHTTPRequest myHTTPRequest = new MyHTTPRequest(context, view, url,
                "POST", userSignup, new MyHTTPRequest.HTTPResponse() {
            @Override
            public void response(String body, View view) {

                try {
                    JSONObject result = new JSONObject(body);
                    if (result.getBoolean("success")) {
                        Toast.makeText(context, result.getString("message"), Toast.LENGTH_LONG).show();
                        String userJsonRaw = result.getString("user");
                        Common.setUserRaw(userJsonRaw, context);
                        Common.setEmail(etEmail.getText().toString(), context);
                        Common.setPassword(etPassword.getText().toString(), context);
                        ifSignUpSuccess();
                    } else {
                        Toast.makeText(context, result.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, progressBar);

        myHTTPRequest.execute();
    }

    private void ifSignUpSuccess(){
        Context context = SignUpActivity.this;
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        SignUpActivity.this.finish();
    }

}