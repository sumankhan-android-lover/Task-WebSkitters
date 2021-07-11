package com.frndzcode.task_webskitters.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private static final String TAG = MapFragment.class.getSimpleName();
    private FragmentMapBinding binding;
    private AppCompatActivity activity;
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private Location my_location;
    private Marker my_marker;
    private LocationManager locationManager;
    private String locationProvider;

    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater,container,false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkLocationPermission();
        //binding.mapView.onResume();
//        mapFragment = (SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.mapView);
//        if (mapFragment != null) {
//            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//            if (locationManager != null && mapFragment.getMap() != null) {
//                locationMap.getMap().setMyLocationEnabled(true);
//            }
//        }
        bindView();


    }

    private void bindView() {
       /* binding.mapView.onResume();
        try {
            MapsInitializer.initialize(activity.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });*/
    }

    private void checkLocationPermission() {

    }


    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        this.googleMap = mGoogleMap;
        setUpMap();
    }

    @SuppressLint("MissingPermission")
    private void setUpMap() {
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setMyLocationEnabled(false);
//        this.googleMap.setTrafficEnabled(true);
//        this.googleMap.setIndoorEnabled(true);
//        this.googleMap.setBuildingsEnabled(true);
//        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        initializeLocationManager();
        if (my_marker == null && my_location != null) {
            my_marker = googleMap.addMarker(new MarkerOptions()
                    .flat(true)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.place_24))
                    .anchor(0.5f, 0.5f)
                    .position(
                            new LatLng(my_location.getLatitude(), my_location
                                    .getLongitude())));
        }
    }

    @SuppressLint("MissingPermission")
    private void initializeLocationManager() {
        if (getActivity()!=null)
            this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        this.locationProvider = locationManager.getBestProvider(criteria, true);
        getLastLocation();

        this.locationManager.requestLocationUpdates(locationProvider, 400, 10, this);

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        my_location = locationManager.getLastKnownLocation(locationProvider);
        Log.e(TAG, "getLastLocation: " + my_location);
        if (my_location != null) {
            LatLng coordinate = new LatLng(my_location.getLatitude(), my_location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, 16);
            googleMap.animateCamera(cameraUpdate);
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.my_location = location;

        double latitude = my_location.getLatitude();
        double longitude = my_location.getLongitude();
        LatLng coordinate = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, 16);
        googleMap.animateCamera(cameraUpdate);

        animateMarker(my_marker, my_location);
    }

    private void animateMarker(Marker marker, Location location) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final LatLng startLatLng = marker.getPosition();
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);

                double lng = t * location.getLongitude() + (1 - t)
                        * startLatLng.longitude;
                double lat = t * location.getLatitude() + (1 - t)
                        * startLatLng.latitude;

                float rotation = (float) (t * location.getBearing() + (1 - t)
                        * startRotation);

                marker.setPosition(new LatLng(lat, lng));
                marker.setRotation(rotation);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        if (locationManager != null)
            this.locationManager.requestLocationUpdates(locationProvider, 400, 10, this);
    }



    @Override
    public void onPause() {
        super.onPause();
        if (locationManager != null)
            this.locationManager.removeUpdates(this);
    }
}