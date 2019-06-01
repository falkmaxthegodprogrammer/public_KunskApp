package pvt19grupp1.kunskapp.com.kunskapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.ClusterMarker;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.util.ClusterManagerRenderer;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import pvt19grupp1.kunskapp.com.kunskapp.util.DirectionsJSONParser;
import pvt19grupp1.kunskapp.com.kunskapp.util.ScreenshotUtils;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.ERROR_DIALOG_REQUEST;
import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.PERMISSIONS_REQUEST_ENABLE_GPS;

public class QuizMapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.SnapshotReadyCallback {

    private static final String TAG = "QuizMapFragment";
    View view;
    private PlaceListViewModel mPlacesListViewModel;
    private QuizPlaceViewModel mQuizPlacesViewModel;
    private SearchView searchView;

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private MapView mMapView;
    private List<Marker> mapMarkersSelected = new ArrayList<>();
    private List<GooglePlaceModel> userCreatedGooglePlaces = new ArrayList<>();

    private List<QuizPlace> selectedPlacesList = new ArrayList<>();

    private List<Marker> googlePlaceAPIMarkers = new ArrayList<>();
    private List<Marker> userCreatedMarkers = new ArrayList<>();

    private Drawable customMapIcon;
    BitmapDescriptor markerIcon;

    private ClusterManager<ClusterMarker> mClusterManager;
    private ClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();

    private ArrayList<LatLng> markerPoints = new ArrayList<>();
    private List<LatLng> totalLatLngPoints = new ArrayList<>();
    private List<LatLng> quizPlacesLatLngPoints = new ArrayList<>();

    private double totalDistance;
    private FusedLocationProviderClient mFusedLocationClient;

    private boolean mLocationPermissionGranted = false;

    private GeoPoint lastKnownLocation;

    public QuizMapFragment() {

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
        mPlacesListViewModel = ViewModelProviders.of(getActivity()).get(PlaceListViewModel.class);
        mQuizPlacesViewModel = ViewModelProviders.of(getActivity()).get(QuizPlaceViewModel.class);
        subscribeObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_map_fragment, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mMapView = view.findViewById(R.id.mapView);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapView, mapFragment).commit();
        }
        initGoogleMap(savedInstanceState);
        mapFragment.getMapAsync(this);
        setHasOptionsMenu(true);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return view;
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
                    System.out.println("onComplete: " + lastKnownLocation.getLatitude() + "," + location.getLongitude());
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // mapFragment.getMapAsync(this);
    }

    public void clearGoogleAPIMarkers() {
        if (googlePlaceAPIMarkers.size() == 0) {
            return;
        }

        for (Marker m : googlePlaceAPIMarkers) {
            m.remove();
        }
    }


    private void reLoadQuizPlacesViewModel() {
        List<GooglePlaceModel> listToLoad = new ArrayList<>();
        listToLoad.addAll(mPlacesListViewModel.getGooglePlaces().getValue());
        mPlacesListViewModel.clearGooglePlaces();

        for (GooglePlaceModel gpm : listToLoad) {
            mPlacesListViewModel.addPlace(gpm);
        }
    }

    private void subscribeObservers() {
        mPlacesListViewModel.getGooglePlaces().observe(getActivity(), new Observer<List<GooglePlaceModel>>() {
            @Override
            public void onChanged(@Nullable List<GooglePlaceModel> places) {

                if(places != null) {
                    if(places.size() == 0) {
                        if (!googlePlaceAPIMarkers.isEmpty()) {
                            clearGoogleAPIMarkers();
                        }
                    }
                }

                    for (GooglePlaceModel place : places) {
                        if (!place.isUserCreated()) {
                            MarkerOptions m = new MarkerOptions().position(new LatLng(place.getLat(), place.getLng())).title(place.getName()).snippet("Klicka på mig för att lägga till i ditt quiz!");
                            Marker marker = mMap.addMarker(m);
                            googlePlaceAPIMarkers.add(marker);
                        } else if (place.isUserCreated()) {
                            MarkerOptions m = new MarkerOptions().position(new LatLng(place.getLat(), place.getLng())).title(place.getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).snippet("Klicka på mig för att lägga till i ditt quiz!");
                            Marker marker = mMap.addMarker(m);
                            userCreatedGooglePlaces.add(place);
                            userCreatedMarkers.add(marker);
                        }
                    }

            }
        });

        mQuizPlacesViewModel.getQuizPlaces().observe(getActivity(), new Observer<List<QuizPlace>>() {
            @Override
            public void onChanged(@Nullable List<QuizPlace> quizPlaces) {

                System.out.println("ON CHANGED CALL... MY QUIZ PLACES");
                if (quizPlaces.size() == 0) {
                    if (mMap != null) {
                        mMap.clear();
                    }
                    clearLocalDataStructures();
                    ((CreateQuizWalkActivity) getActivity()).updateQuizInfoTextDefaultMsg();
                }

                markerPoints.clear();
                quizPlacesLatLngPoints.clear();

                for (int i = 0; i < quizPlaces.size(); i++) {
                    QuizPlace quizPlace = quizPlaces.get(i);
                    System.out.println(quizPlaces.size() + " ------- QUIZPLACES SIZE");
                    if (quizPlace != null) {
                        addCustomMapMarker(quizPlace);
                    }
                    markerPoints.add(new LatLng(quizPlace.getLatitude(), quizPlace.getLongitude()));
                    quizPlacesLatLngPoints.add(new LatLng(quizPlace.getLatitude(), quizPlace.getLongitude()));
                }

                if (markerPoints.size() >= 2) {
                    LatLng origin = markerPoints.get(markerPoints.size() - 2);
                    LatLng dest = markerPoints.get(markerPoints.size() - 1);
                    String url = getDirectionsUrl(origin, dest);
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);
                }

                if (quizPlacesLatLngPoints.size() == 2) {
                    totalDistance = SphericalUtil.computeDistanceBetween(markerPoints.get(0), markerPoints.get(1));
                }

                if (mQuizPlacesViewModel.getQuizPlaces().getValue() != null) {
                    if (mQuizPlacesViewModel.getQuizPlaces().getValue().size() > 0) {
                        ((CreateQuizWalkActivity) getActivity()).updateQuizInfoText(mQuizPlacesViewModel.getQuizPlaces().getValue().size(), (int) totalDistance, (int) totalDistance / 120);
                    }
                }
                //
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        getLastKnownLocation();
        mMap.setMyLocationEnabled(true);

        LatLng sthlm = new LatLng(59.325695, 18.071869);
        float zoomLevel = 14.0f;

        mMap.setBuildingsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sthlm, zoomLevel));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.325695, 18.071869), 14.0f));
        //

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //  mMap.clear();
                //  mapMarkersSelected.clear();
            }
        });

        mMap.setOnPolylineClickListener(this);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLngTest) {
                //MarkerOptions clickMarker = new MarkerOptions().position(latLngTest).title("Min egen marker!");

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("LÄGG TILL EGEN PLATS");
                builder1.setCancelable(true);

                final LinearLayout linearLayout = new LinearLayout(getActivity());
                final EditText input = new EditText(getActivity());

                final CheckBox checkBox = new CheckBox(getActivity());

                checkBox.setChecked(true);
                checkBox.setText("Lägg till plats i quiz-promenad");

                linearLayout.setOrientation(LinearLayout.VERTICAL);

                linearLayout.addView(input);
                linearLayout.addView(checkBox);

                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder1.setView(linearLayout);

                final float zoomLevel = mMap.getCameraPosition().zoom + 0.3f;

                builder1.setPositiveButton(
                        "LÄGG TILL PLATS!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                if (checkBox.isChecked()) {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngTest, zoomLevel));
                                    mQuizPlacesViewModel.addQuizPlace(new QuizPlace(input.getText().toString(), latLngTest.latitude, latLngTest.longitude));
                                } else {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngTest, zoomLevel));
                                    GooglePlaceModel gpm = new GooglePlaceModel(latLngTest, input.getText().toString());
                                    mPlacesListViewModel.addPlace(gpm);
                                    userCreatedGooglePlaces.add(gpm);
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "Avbryt",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    public void searchPlaceApi(String query, String language) {
        mPlacesListViewModel.searchPlaceApi(query, language);
    }

    public void zoomToRouteBounds() {
        LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng point : totalLatLngPoints) {
            bc.include(point);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 190));
    }

    public Bitmap getBitmapScreenshot() {
        return ScreenshotUtils.getScreenShot(mMapView);
    }

    public void takeScreenshot() {
        // ((CreateQuizWalkActivity)getActivity()).getQuizWalkInstance().setBitMapQuizRoute(ScreenshotUtils.getScreenShot(mMapView));
    }


    public void clearClusterMarkers() {
        mClusterMarkers.clear();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), mMap.getCameraPosition().zoom));
        mapMarkersSelected.add(marker);
        // marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        marker.showInfoWindow();
        return true;
    }

    public void zoomToLocation(LatLng latLng) {
        float zoom = mMap.getCameraPosition().zoom + 0.4f;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void addCustomMapMarker(QuizPlace quizPlace) {
        if (mMap != null) {

            if (mClusterManager == null) {
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mMap);
            }

            if (mClusterManagerRenderer == null) {
                mClusterManagerRenderer = new ClusterManagerRenderer(
                        getActivity(),
                        mMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            String snippet = "Lägg till frågor!";
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

        try {
            mClusterManager.cluster();
        } catch (NullPointerException e) {

        }

        //     setCameraView();
    }

    public void reDrawSavedMapState(List<QuizPlace> quizPlaceList) {

        for (QuizPlace qp : quizPlaceList) {
            addCustomMapMarker(qp);
        }

        PolylineOptions lineOptions = new PolylineOptions();

      /*   lineOptions.addAll(totalLatLngPoints);
        lineOptions.clickable(true);
        lineOptions.width(12);
//        lineOptions.color(ContextCompat.getColor(getActivity(), R.color.colorAccent));

        totalDistance += SphericalUtil.computeLength(totalLatLngPoints);

        // Drawing polyline in the Google Map for the entire route
        mMap.addPolyline(lineOptions); */

    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        polyline.setColor(0xE16E79FF);
        polyline.setZIndex(1);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String waypoints = "";

        for (int i = 2; i < markerPoints.size(); i++) {
            LatLng point = (LatLng) markerPoints.get(i);
            if (i == 2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + "&alternatives=false" + waypoints;

        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&mode=walking" + "&key=" + ConstantKeys.API_GOOGLE_DIRECTIONS_KEY;
        return url;
    }

    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        marker.setTitle(marker.getTitle());
        marker.setSnippet("\n" + marker.getTitle() + " - lägg till i Quiz?");
        builder.setMessage(marker.getSnippet())
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        marker.setSnippet("Ändrar text.. " + marker.getTitle());

                        QuizPlace qp = new QuizPlace(marker.getTitle(), marker.getPosition().latitude, marker.getPosition().longitude);
                        mQuizPlacesViewModel.addQuizPlace(qp);
                        float zoomLevel = mMap.getCameraPosition().zoom;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), zoomLevel));
                        Toast.makeText(getActivity(), " Lade till + " + qp.getName() + " till tipspromenaden!", Toast.LENGTH_LONG);
                        dialog.dismiss();
                        marker.remove();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSnapshotReady(Bitmap bitmap) {

    }


    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = new ArrayList<>();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }
            }

            lineOptions.addAll(points);
            lineOptions.clickable(true);
            lineOptions.width(12);
            lineOptions.color(ContextCompat.getColor(getActivity(), R.color.colorAccent));

            totalLatLngPoints.addAll(points);
            totalDistance += SphericalUtil.computeLength(points);

            // Drawing polyline in the Google Map for the entire route
            mMap.addPolyline(lineOptions);
        }
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

    public List<LatLng> getTotalLatLngPoints() {
        return totalLatLngPoints;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void clearLocalDataStructures() {
        totalLatLngPoints.clear();
        markerPoints.clear();
        clearClusterMarkers();
    }

    public void clearMap() {
//        mMap.clear();
    }

    private void getLocationPermission() {
        /*
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


}