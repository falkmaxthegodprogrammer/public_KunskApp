package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.ClusterMarker;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.util.ClusterManagerRenderer;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

public class QuizWalkMapFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private MapView mMapView;
    View view;

    private ClusterManager<ClusterMarker> mClusterManager;
    private ClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();

    public QuizWalkMapFragment() {

    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(ConstantKeys.MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_walk_map_fragment, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.quiz_walk_mapview);
        mMapView = view.findViewById(R.id.quiz_walk_mapview);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.quiz_walk_mapview, mapFragment).commit();
        }
        initGoogleMap(savedInstanceState);
        mapFragment.getMapAsync(this);
        setHasOptionsMenu(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMaxZoomPreference(14.8f);

        LatLng sthlm = new LatLng(59.325695, 18.071869);
        float zoomLevel = 14.0f;

        mMap.setBuildingsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sthlm, zoomLevel));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.325695, 18.071869), 14.0f));
        //
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(ConstantKeys.MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(ConstantKeys.MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    public void drawPolylines(List<LatLng> totalLatLngPoints) {
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(totalLatLngPoints);
        lineOptions.clickable(true);
        lineOptions.width(12);
        lineOptions.color(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        mMap.addPolyline(lineOptions);
    }

    public void zoomToRouteBounds(List<LatLng> totalLatLngPoints) {
        LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng point : totalLatLngPoints) {
            bc.include(point);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 180));
     //   mMap.moveCamera(CameraUpdateFactory.zoomBy(-0.5f));
    }

    public void addMarkersFromQuizWalkList(List<QuizPlace> quizPlaces) {
        for(int i = 0; i < quizPlaces.size(); i++) {
            addCustomMapMarker(quizPlaces.get(i), i+1);
        }
    }

    private void addCustomMapMarker(QuizPlace quizPlace, int i){
        if(mMap != null){

            if(mClusterManager == null){
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mMap);
            }

            if(mClusterManagerRenderer == null){
                mClusterManagerRenderer = new ClusterManagerRenderer(
                        getActivity(),
                        mMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            String snippet = "Plats nummer " + i + " i rundan.";
            String title = "" + quizPlace.getName();

            int icon = R.drawable.ic_exclamation;

            ClusterMarker newClusterMarker = new ClusterMarker(
                    new LatLng(quizPlace.getLatitude(), quizPlace.getLongitude()),
                    title,
                    snippet,
                    icon,
                    quizPlace
            );
            mClusterManager.addItem(newClusterMarker);
            mClusterMarkers.add(newClusterMarker);
        }

        try{
            mClusterManager.cluster();
        } catch(NullPointerException e) {

        }

        //     setCameraView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        //mMapView.onStart();
        //  mapFragment.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();

        //    mapFragment.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mMapView.onLowMemory();
        super.onLowMemory();
        //   mapFragment.onLowMemory();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    public void clearMap() {
        mMap.clear();
    }
}
