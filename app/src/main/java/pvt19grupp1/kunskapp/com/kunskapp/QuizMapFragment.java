package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;

public class QuizMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    View view;
    private PlaceListViewModel mPlacesListViewModel;


    SupportMapFragment mapFragment;
    GoogleMap mMap;

    public QuizMapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_map_fragment, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if(mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapView, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlacesListViewModel = ViewModelProviders.of(getActivity()).get(PlaceListViewModel.class);
    }

    private void subscribeObservers() {
        mPlacesListViewModel.getGooglePlaces().observe(getActivity(), new Observer<List<GooglePlaceModel>>() {
            @Override
            public void onChanged (@Nullable List<GooglePlaceModel> places) {
                mMap.clear();
                for(GooglePlaceModel place : places) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(place.getLat(), place.getLng())).title(place.getName()));
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        subscribeObservers();

        LatLng sthlm = new LatLng(	59.325695, 18.071869);
        float zoomLevel = 14.0f;
        mMap.addMarker(new MarkerOptions().position(sthlm).title("Marker in sthlm"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sthlm, zoomLevel));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.325695, 18.071869), 14.0f));
        //
        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        return true;
    }
}
