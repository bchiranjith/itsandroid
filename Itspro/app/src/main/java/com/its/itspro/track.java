package com.its.itspro;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;

/**
 * Created by sai on 23/3/17.
 */

public class track extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap map;
    private SupportMapFragment supportMapFragment;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest lr;
    private double lat , lon;
    public Context conn1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        conn1 = container.getContext();
        Log.i("Activity", conn1.toString());
        mGoogleApiClient = new GoogleApiClient.Builder(conn1)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        return inflater.inflate(R.layout.track, container, false);
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
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(-32, 151);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        /*
        Log.i("Activity1",getActivity().getApplicationContext().toString());
        map.setMyLocationEnabled(true);
        */
        Log.i("on map ready callback","executed");
        if (ActivityCompat.checkSelfPermission(conn1, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(conn1, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location a = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i("lattitude",a.toString());
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sydney, 10);
        map.animateCamera(update);
    }




    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        Log.i("log i ", "client connected");
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        Log.i("log i ", "Client disconnected");
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("entered onconnected", "true");

        if (ActivityCompat.checkSelfPermission(conn1, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(conn1, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,lr, this);


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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
