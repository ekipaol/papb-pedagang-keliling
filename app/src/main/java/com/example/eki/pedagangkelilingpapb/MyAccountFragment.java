package com.example.eki.pedagangkelilingpapb;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class MyAccountFragment extends Fragment {

    TextView txt_name, txt_username, txt_info, txt_email, txt_contact, txt_gender;
    ImageView img_pembeli;
    EditText editName, editUsername, editEmail, editContact;
    Spinner editGender;
    Button btn_edit;
    String url = "http://10.0.2.2/asongan/parsing.php";

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
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);


        txt_name = (TextView) view.findViewById(R.id.txt_name);
        txt_username = (TextView) view.findViewById(R.id.txt_username);
        txt_info = (TextView) view.findViewById(R.id.txt_info);
        txt_email = (TextView) view.findViewById(R.id.txt_email);
        txt_contact = (TextView) view.findViewById(R.id.txt_contact);
        txt_gender = (TextView) view.findViewById(R.id.txt_gender);
        img_pembeli = (ImageView) view.findViewById(R.id.img_pembeli);
        editName = (EditText) view.findViewById(R.id.editName);
        editUsername = (EditText) view.findViewById(R.id.editUserName);
        editEmail = (EditText) view.findViewById(R.id.editEmail);
        editContact = (EditText) view.findViewById(R.id.editContact);
        editGender = (Spinner) view.findViewById(R.id.editGender);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);


        Glide.with(this).load("https://www.thefamouspeople.com/profiles/images/emma-watson-5.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into((ImageView) view.findViewById(R.id.img_pembeli));




        return view;
    }
}
