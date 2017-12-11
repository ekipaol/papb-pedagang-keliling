package com.example.eki.pedagangkelilingpapb;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyAccountFragment extends Fragment {

    TextView txt_name, txt_username, txt_info, txt_email, txt_contact, txt_gender;
    ImageView img_pembeli;
    EditText editName, editUsername, editEmail, editContact,editAddress;
    Spinner editGender;
    Button btn_edit;
    String emailExtra;
    private ProgressDialog progressDialog;
    View view;


    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void getRequest(EditText txtResult, String SUrl) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_my_account, container, false);
        Bundle args = getArguments();
        emailExtra = args.getString("email");

        //proses mengambil nama dari database
        String url3 = "http://192.168.43.83/pedagang/index.php/c_admin/getNama/"+emailExtra;
        // ini IP tethering ke HP 192.168.43.83
        // ini IP konek ke wifi kos eki 192.168.1.10
        RequestQueue queue3 = Volley.newRequestQueue(getContext());
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        editName = (EditText) view.findViewById(R.id.editName);
                        editName.setText(response);

                        String url3 = "http://192.168.43.83/pedagang/index.php/c_admin/getAlamat/"+emailExtra;
                        // ini IP tethering ke HP 192.168.43.83
                        // ini IP konek ke wifi kos eki 192.168.1.10
                        RequestQueue queue3 = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        editAddress = (EditText) view.findViewById(R.id.editAddress);
                                        editAddress.setText(response);
                                        String url3 = "http://192.168.43.83/pedagang/index.php/c_admin/getContact/"+emailExtra;
                                        // ini IP tethering ke HP 192.168.43.83
                                        // ini IP konek ke wifi kos eki 192.168.1.10
                                        RequestQueue queue3 = Volley.newRequestQueue(getContext());
                                        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                        editContact = (EditText) view.findViewById(R.id.editContact);
                                                        editContact.setText(response);


                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                        queue3.add(stringRequest3);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue3.add(stringRequest3);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue3.add(stringRequest3);


        txt_name = (TextView) view.findViewById(R.id.txt_name);

        txt_info = (TextView) view.findViewById(R.id.txt_info);
        txt_email = (TextView) view.findViewById(R.id.txt_email);

        txt_contact = (TextView) view.findViewById(R.id.txt_contact);
        txt_gender = (TextView) view.findViewById(R.id.txt_gender);
        img_pembeli = (ImageView) view.findViewById(R.id.img_pembeli);
        editName = (EditText) view.findViewById(R.id.editName);
        editAddress=(EditText)view.findViewById(R.id.editAddress);
        editEmail = (EditText) view.findViewById(R.id.editEmail);
        editEmail.setText(emailExtra);
        editContact = (EditText) view.findViewById(R.id.editContact);
        editGender = (Spinner) view.findViewById(R.id.editGender);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                progressDialog = new ProgressDialog(getContext()) ;
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                String url4 = "http://192.168.43.83/pedagang/index.php/c_admin/updateUser/"+editEmail.getText().toString()+"/"+editName.getText().toString().replaceAll(" ","%20")+"/"+editContact.getText().toString()+"/"+editAddress.getText().toString().replaceAll(" ","%20");
                RequestQueue queue4 = Volley.newRequestQueue(getContext());
                StringRequest stringRequest4 = new StringRequest(Request.Method.GET, url4,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue4.add(stringRequest4);

                progressDialog.dismiss();
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        });


        Glide.with(this).load("https://www.theatrework.org/sites/default/files/styles/medium/public/images/BLANK_PROFILE.png?itok=n8DB7YbT")
                .apply(RequestOptions.circleCropTransform())
                .into((ImageView) view.findViewById(R.id.img_pembeli));




        return view;
    }
}
