package pvt19grupp1.kunskapp.com.kunskapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.ClusterMarker;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.util.ClusterManagerRenderer;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

public class QuizMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "QuizMapFragment";
    View view;
    private PlaceListViewModel mPlacesListViewModel;
    private QuizPlaceViewModel mQuizPlacesViewModel;
    private SearchView searchView;

    SupportMapFragment mapFragment;
    GoogleMap mMap;
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

    private ArrayList<LatLng> coordList = new ArrayList<LatLng>();

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
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlacesListViewModel = ViewModelProviders.of(getActivity()).get(PlaceListViewModel.class);
        mQuizPlacesViewModel = ViewModelProviders.of(getActivity()).get(QuizPlaceViewModel.class);

        mapFragment.getMapAsync(this);

        }

    public void clearGoogleAPIMarkers() {
        if(googlePlaceAPIMarkers.size() == 0) {
            return;
        }

        for(Marker m : googlePlaceAPIMarkers) {
            m.remove();
        }
    }

    private void subscribeObservers() {
        mPlacesListViewModel.getGooglePlaces().observe(getActivity(), new Observer<List<GooglePlaceModel>>() {
            @Override
            public void onChanged (@Nullable List<GooglePlaceModel> places) {
                  if(!googlePlaceAPIMarkers.isEmpty()) {
                      clearGoogleAPIMarkers();
                  }

                  for(GooglePlaceModel place : places) {
                      if(!place.isUserCreated()) {
                          MarkerOptions m = new MarkerOptions().position(new LatLng(place.getLat(), place.getLng())).title(place.getName());
                          Marker marker = mMap.addMarker(m);
                          googlePlaceAPIMarkers.add(marker);
                      } else if(place.isUserCreated()) {
                          MarkerOptions m = new MarkerOptions().position(new LatLng(place.getLat(), place.getLng())).title(place.getName())
                                  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
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
                        for (int i = 0; i < quizPlaces.size(); i++) {
                            QuizPlace quizPlace = quizPlaces.get(i);
                            //    selectedPlacesList.add(quizPlace);
                                coordList.add(new LatLng(quizPlace.getLatitude(), quizPlace.getLongitude()));
                                addCustomMapMarker(quizPlace);

                        //        mMap.addMarker(new MarkerOptions().position(new LatLng(quizPlace.getGooglePlace().getLat(), quizPlace.getGooglePlace().getLng())).title(quizPlace.getGooglePlace().getName())
                        //                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        }

                            PolylineOptions polylineOptions = new PolylineOptions();
                            polylineOptions.addAll(coordList);
                            polylineOptions.width(10).color(0xE16E79FF);

                            mMap.addPolyline(polylineOptions);
                            coordList.clear();
                    }});
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        subscribeObservers();

        LatLng sthlm = new LatLng(	59.325695, 18.071869);
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

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLngTest)
            {
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

                builder1.setPositiveButton(
                        "LÄGG TILL PLATS!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                if(checkBox.isChecked()) {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngTest, 18.0f));
                                    //GooglePlaceModel gpm = new GooglePlaceModel(latLngTest, input.getText().toString());

                                    //QuizPlace qp = new QuizPlace(input.getText().toString(), latLngTest.latitude, latLngTest.longitude);
                                    mQuizPlacesViewModel.addQuizPlace(new QuizPlace(input.getText().toString(), latLngTest.latitude, latLngTest.longitude));
                                } else {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngTest, 18.0f));
                                    GooglePlaceModel gpm = new GooglePlaceModel(latLngTest, input.getText().toString());
                                    mPlacesListViewModel.addPlace(gpm);
                                    userCreatedGooglePlaces.add(gpm);
                                    Toast.makeText(getActivity(), "test mapclick", Toast.LENGTH_LONG);
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
    }

    public void searchPlaceApi(String query, String language) {
        mPlacesListViewModel.searchPlaceApi(query, language);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mapMarkersSelected.add(marker);
       // marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        marker.showInfoWindow();
        return true;
    }

    public void zoomToLocation(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

    }

    private void addCustomMapMarker(QuizPlace quizPlace){
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

                String snippet = quizPlace.getName();
                String title = quizPlace.getName();
                int icon = R.mipmap.kunskapplogga3_round; // set the default avatar

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
            mClusterManager.cluster();

       //     setCameraView();
        }


    /**
     * Determines the view boundary then sets the camera
     * Sets the view
     */
 /*   private void setCameraView() {

        // Set a boundary to start
        double bottomBoundary = mUserPosition.getGeo_point().getLatitude() - .1;
        double leftBoundary = mUserPosition.getGeo_point().getLongitude() - .1;
        double topBoundary = mUserPosition.getGeo_point().getLatitude() + .1;
        double rightBoundary = mUserPosition.getGeo_point().getLongitude() + .1;

        mMapBoundary = new LatLngBounds(
                new LatLng(bottomBoundary, leftBoundary),
                new LatLng(topBoundary, rightBoundary)
        );

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));
    }

*/


}
