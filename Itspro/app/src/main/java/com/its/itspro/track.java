package com.its.itspro;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;

/**
 * Created by sai on 23/3/17.
 */

public class track extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap map;
    private SupportMapFragment supportMapFragment;
    private GoogleApiClient mGoogleApiClient;
    private Location lastlocation;
    private LocationRequest lr;
    private double lat, lon;
    public Context conn1;
    private int REQUEST_LOCATION = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //conn1 = container.getContext();
        //Log.i("Activity", conn1.toString());
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        return inflater.inflate(R.layout.track, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("client for google api", mGoogleApiClient.toString());

        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.trackmap);
        supportMapFragment.getMapAsync(this);
        getActivity().setTitle("Track yourself");

        lr = LocationRequest.create();
        lr.setInterval(10000);
        lr.setFastestInterval(7000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.i("log i ", "client connected");
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        Log.i("log i ", "Client disconnected");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(), "Granted!!", Toast.LENGTH_LONG).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(getContext(), "Not Granted!!", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("entered onconnected", "true");


       /* LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,lr,this);*/
    }

/*    private void getCurrentLocation(View view) {
        int a = view.getId();
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        lastlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastlocation != null) {
            Log.i("lattitude", lastlocation.toString());
        }

    }*/

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"Connection to server Failed!",Toast.LENGTH_LONG).show();
        Log.i("connection","failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("LAT:  ", String.valueOf(lat));
        Log.i("LON:  ", String.valueOf(lon));
        Log.i("called","on location changed");
        lat = location.getLatitude();
        lon = location.getLongitude();
        CameraUpdate update = newLatLngZoom(new LatLng(lat,lon),12);
        map.animateCamera(update);
    }
}
