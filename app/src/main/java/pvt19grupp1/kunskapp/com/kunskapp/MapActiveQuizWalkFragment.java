package pvt19grupp1.kunskapp.com.kunskapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.location.zzas;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.ClusterMarker;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.util.ClusterManagerRenderer;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;

import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MapActiveQuizWalkFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private MapView mMapView;
    View view;

    private static final String TAG = "ActiveQuizWalkMapFragment";

    private ClusterManager<ClusterMarker> mClusterManager;
    private ClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationClient;
    private GeoPoint lastKnownLocation;
    private LatLng lastKnownLatLng;
    private QuizWalk quizWalkTest;
    private LocationRequest locationRequest;
    private GoogleApiClient apiClient;
    private LocationCallback locationCallBack;
    private int nextQuizPlace = 0;
    private int quizPlacesTotal;

    public MapActiveQuizWalkFragment() {
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_active_quizwalk, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_active_quizwalk_fragment);
        mMapView = view.findViewById(R.id.map_view_active_quizwalk);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map_view_active_quizwalk, mapFragment).commit();
        }
        initGoogleMap(savedInstanceState);
        mapFragment.getMapAsync(this);
        setHasOptionsMenu(false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastKnownLocation();
        quizPlacesTotal = quizWalkTest.getQuizPlaces().size();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapFragment.getMapAsync(this);

    }

    public void zoomToMyLocation() {
        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())).
                zoom(18.5f).
                tilt(55).
                bearing(25).
                build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void startPingingLocation() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    lastKnownLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                    LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    zoomToMyLocation();
                    double distanceToNextQuizPlace = SphericalUtil.computeDistanceBetween(latLngLocation, new LatLng(quizWalkTest.getQuizPlaces().get(nextQuizPlace).getLatitude(), quizWalkTest.getQuizPlaces().get(nextQuizPlace).getLongitude()));
                    Log.d(TAG, "onLocationResult - LatLng: " + location.getLatitude() + "," + location.getLongitude() + " - Distance to next place: " + (int) distanceToNextQuizPlace);

                    /***
                     * Updating QuizInfoBar in
                     * QuizWalkActivity
                     */

                    ((QuizWalkActivity)getActivity()).setInfoTextQuizRunning((int) distanceToNextQuizPlace, nextQuizPlace);

                    /***
                      * Logic for displaying next questions etc.
                      */

                    if (distanceToNextQuizPlace < 15) {
                        showReceiveQuestionDialog();
                    }
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMapMarkers(quizWalkTest.getQuizPlaces());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        mMap.setBuildingsEnabled(true);
        mMap.setMyLocationEnabled(true);

        LatLng sthlm = new LatLng(59.3311, 18.0019);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(new LatLng(59.3311, 18.0019)).
                        zoom(18.5f).
                        tilt(55).
                        bearing(25).
                        build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                return true;
            }
        });

        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(quizWalkTest.getLatLngPoints());
        lineOptions.clickable(true);
        lineOptions.width(12);
        lineOptions.color(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        mMap.addPolyline(lineOptions);

        PolylineOptions lineOptions2 = new PolylineOptions();
        lineOptions2.add(new LatLng(59.3311, 18.0019), quizWalkTest.getLatLngPoints().get(0));
        lineOptions2.color(Color.CYAN);
        lineOptions2.width(10);
        mMap.addPolyline(lineOptions2);

        zoomToRouteBounds(quizWalkTest.getLatLngPoints());
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

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    lastKnownLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                    LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    System.out.println("onComplete: " + "number: " + " check." + lastKnownLocation.getLatitude() + "," + location.getLongitude());

                }
            }
        });
    }

    public void addMapMarkers(List<QuizPlace> quizPlaces){
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

            for(QuizPlace quizPlace : quizPlaces) {
                String snippet = "2 frågor.";
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
                MarkerOptions m = new MarkerOptions().position(new LatLng(quizPlace.getLatitude(), quizPlace.getLongitude())).title(quizPlace.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).snippet("Antal frågor: 2");
                mMap.addMarker(m);
                }
            }
            mClusterManager.cluster();
    }

    private void getLocationPermission() {
        /***
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {

                } else {
                    getLocationPermission();
                }
            }
        }
    }

    public void stopPingingLocation() {
        mFusedLocationClient.removeLocationUpdates(locationCallBack);
    }

    public void showReceiveQuestionDialog() {
        stopPingingLocation();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Du är nu inom 15 meter från " + quizWalkTest.getQuizPlaces().get(nextQuizPlace).getName() + "! Gör dig redo för fråga!");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Visa fråga!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: VISAR FRÅGA");
                        startPingingLocation();
                        dialog.cancel();
                        startNextQuestion();
                        nextQuizPlace++;
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void startNextQuestion() {
        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putParcelableArrayListExtra("questions", quizWalkTest.getQuizPlaces().get(nextQuizPlace).getQuestions());
        startActivity(intent);
    }


    public QuizWalk getQuizWalkTest() {
        return quizWalkTest;
    }

    public void setQuizWalkTest(QuizWalk quizWalkTest) {
        this.quizWalkTest = quizWalkTest;
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
        stopPingingLocation();
        mMapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        startPingingLocation();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        stopPingingLocation();

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
        stopPingingLocation();
    }

    public void clearMap() {
        mMap.clear();
    }

}
