package com.example.eki.pedagangkelilingpapb;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//MASIH ERROR, GAKTAU THISNYA ISINYA NULL DI BAGIAN MAPfRAGMENT.
public class MapPedagang extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener{

    //Our Map
    private GoogleMap mMap;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;

    //Buttons
    private ImageButton buttonSave;
    private ImageButton buttonCurrent;
    private ImageButton buttonView;
    String email;

    //Google ApiClient
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pedagang);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Initializing views and adding onclick listeners
        buttonSave = (ImageButton) findViewById(R.id.buttonSave);
        buttonCurrent = (ImageButton) findViewById(R.id.buttonCurrent);
        buttonView = (ImageButton) findViewById(R.id.buttonView);
        buttonSave.setOnClickListener(this);
        buttonCurrent.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
             email = getIntent().getStringExtra("EMAIL_USER");

            RequestQueue queue = Volley.newRequestQueue(MapPedagang.this);
            String url = "http://192.168.1.10/pedagang/index.php/c_admin/updateLokasi/"+latitude+"/"+longitude+"/"+"admin";
// Instantiate the RequestQueue.


// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            String url2 = "http://192.168.1.10/pedagang/index.php/c_admin/getPedagangLat/";
// Instantiate the RequestQueue.


// Request a string response from the provided URL.
                            RequestQueue queue2 = Volley.newRequestQueue(MapPedagang.this);
                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            latitude=Double.parseDouble(response);
                                            //FOR LONGITUDE PARAMETER
                                            String url3 = "http://192.168.1.10/pedagang/index.php/c_admin/getPedagangLon/";
                                            RequestQueue queue3 = Volley.newRequestQueue(MapPedagang.this);
                                            StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            longitude=Double.parseDouble(response);
                                                            LatLng latLng = new LatLng(latitude, longitude);
                                                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.iconmini);
                                                            mMap.addMarker(new MarkerOptions()
                                                                    .position(latLng) //setting position
                                                                    .draggable(false) //Making the marker draggable
                                                                    .title("Sampel Pedagang"+latitude+", "+longitude)
                                                            .icon(icon)); //Adding a title


                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });
                                            queue3.add(stringRequest3);
                                            //END OF LONGITUDE PARAMETER

                                            // Display the first 500 characters of the response string.

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            queue2.add(stringRequest2);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);








            //moving the map to location
            moveMap();
        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude+", "+email;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonCurrent){
            getCurrentLocation();
            moveMap();
        }
    }
}
