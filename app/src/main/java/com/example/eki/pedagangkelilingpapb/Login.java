package com.example.eki.pedagangkelilingpapb;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    Button login;
    EditText email;
    EditText pass;
    TextView register;

    RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    HttpURLConnection myConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = (Button) findViewById(R.id.button);
        email = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        register= (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        requestQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Login.this) ;
                progressDialog.setMessage("Loading");
                progressDialog.show();
               // new sendLogin().execute("http://192.168.1.10/pedagang/index.php/c_admin/uploadLogin/" + email.getText()+"/"+pass.getText());
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                String url = "http://192.168.1.10/pedagang/index.php/c_admin/uploadLogin/"+email.getText()+"/"+pass.getText();
// Instantiate the RequestQueue.


// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                if (response.equalsIgnoreCase("1")){
                                    progressDialog.setMessage("Sukses");
                                    progressDialog.show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(Login.this, MapPedagang.class);
                                    intent.putExtra("EMAIL_USER", email.getText());
                                    startActivity(intent);
                                   //INTENT TANPA EXTRA startActivity(new Intent(Login.this,MapPedagang.class));
                                }
                                else {
                                    progressDialog.setMessage("Gagal");
                                    progressDialog.show();;
                                   // email.setText("LOGIN GAGAL");
                                    progressDialog.dismiss();
                                    email.setText("");
                                    email.setHint("Login Gagal");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        email.setText("GAGAL!");
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
    }


    class sendLogin extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Login.this) ;
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            StringBuilder sb = null;
            BufferedReader reader = null;
            String serverResponse = null;
            try {

                URL url = new URL("192.168.1.10/pedagang/index.php/c_admin/uploadLogin/tes/tes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                //Log.e("statusCode", "" + statusCode);
                if (statusCode == 200) {
                    sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                }

                connection.disconnect();
                if (sb != null)
                    serverResponse = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //All your UI operation can be performed here
            System.out.println(s);
        }
    }

}