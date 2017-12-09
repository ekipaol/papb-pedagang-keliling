package com.example.eki.pedagangkelilingpapb;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.in;

public class Register extends AppCompatActivity {

    EditText txtName, txtEmail, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName = (EditText) findViewById(R.id.input_name);
        txtEmail = (EditText) findViewById(R.id.input_email);
        txtPass = (EditText) findViewById(R.id.input_pass);

    }

    public void onReg (View view){
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPass.getText().toString();
        String type = "register";
        BackgroundRegister backgroundWorker = new BackgroundRegister(this);
        backgroundWorker.execute(type, name, email, password);
    }

}
