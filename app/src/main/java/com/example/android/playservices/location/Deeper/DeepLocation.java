package com.example.android.playservices.location.Deeper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.playservices.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class DeepLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    /*
    a documentation for the location by the following steps

    1- adding dependency in gradle.build app file :
         compile 'com.google.android.gms:play-services:11.0.4'
    2- add the Meta-Data in the manifest
         <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version"></meta-data>
    3- implements the following classes
            @GoogleApiClient.ConnectionCallbacks ,
            @GoogleApiClient.OnConnectionFailedListener ,
            @LocationListener
    4- check if API >22 you  need a run time permission
        @isLocationPermitted
    5- is so, then initialize your GoogleApiClient
        @initializeGoogleAPiClinet
    6- then Create Location Request
        @createLocationRequest
    7- always check the PlayService Status
        @checkPlayServices
     */


    private static final long UPDATE_INTERVAL = 100;
    private static final long FATEST_INTERVAL = 10;
    private static final float DISPLACEMENT = 10;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 7172;
    String TAG = "DEEP_TAG";
    TextView longitude;
    TextView latitude;
    GoogleApiClient googleApiClient;
    Location locationService;
    LocationRequest mLocationRequest;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_location);
        initializeViews();
        initializeGoogleAPiClinet();
        longitude.setText("hey man from deep");
        if (isLocationPermitted()) {
            initializeGoogleAPiClinet();
            createLocationRequest();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isLocationPermitted()) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                checkPlayServices();

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isLocationPermitted()) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            //Request location updates:
                            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
                            //Request location updates:

                        }
                    }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationService = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (locationService != null) {
            longitude.append("  " + String.valueOf(locationService.getLongitude()));
            latitude.setText(String.valueOf(locationService.getLatitude()));

        }
        Snackbar.make(findViewById(R.id.latitude), "connected", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "connection is suspended");
        Snackbar.make(findViewById(R.id.latitude), "suspended", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {

        longitude.append("  " + String.valueOf(location.getAltitude()));
        latitude.setText(String.valueOf(location.getLatitude()));

    }

    //   initialize your views
    private void initializeViews() {
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
    }


    // initialize your Api Component
    private synchronized void initializeGoogleAPiClinet() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }
    // Initialize Location Request
    private  void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    private boolean isLocationPermitted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.alertTitle)
                        .setMessage(R.string.alertMessage)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(DeepLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create().show();
            } else {

                ActivityCompat.requestPermissions(DeepLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {

            return true;
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
}
