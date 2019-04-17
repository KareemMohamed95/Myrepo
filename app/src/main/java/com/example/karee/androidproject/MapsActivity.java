package com.example.karee.androidproject;

import android.Manifest;
import android.app.Fragment;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    final static int PERMISSIONS = 1;
    final static String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap mMap;
    LocationManager Locationmanager;
    MarkerOptions Markeroptions;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Locationmanager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Markeroptions = new MarkerOptions().position(new LatLng(30.049045686803332,31.378600001335148)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title("Current Location");
        if(Build.VERSION.SDK_INT>=23&&!isPermissionsGranted())requestPermissions(Permissions,PERMISSIONS);
        else {RequestLocation();}
        if(!isLocationEnabled())
        {
            Toast.makeText(getApplicationContext(), "LOCATION DISABLED", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isLocationEnabled() {
        return Locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||Locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isPermissionsGranted() {
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
                ||checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)return true;
        else
        {
            return false;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = mMap.addMarker(Markeroptions);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng Coordinates = new LatLng(location.getLatitude(),location.getLongitude());
        marker.setPosition(Coordinates);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Coordinates));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private void RequestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = Locationmanager.getBestProvider(criteria,true);
        Locationmanager.requestLocationUpdates(provider,10000,10,this);
    }
}
