package com.example.HackathonApp_v02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng bontida = new LatLng(46.9089896499704, 23.814345169730142);
        googleMap.addMarker(new MarkerOptions()
                .position(bontida)
                .title("Bontida Recycling Centre"));

        LatLng oldtown = new LatLng(46.77130215233844, 23.58917393584071);
        googleMap.addMarker(new MarkerOptions()
                .position(oldtown)
                .title("Old Town Recycling Centre"));

        LatLng marasti = new LatLng(46.77615918804148, 23.6050981032797);
        googleMap.addMarker(new MarkerOptions()
                .position(marasti)
                .title("Marasti Recycling Centre"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(oldtown));
    }
}