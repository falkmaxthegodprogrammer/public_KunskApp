package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.ViewPagerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;

public class CreateQuizWalkActivity extends BaseActivity  {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private AppBarLayout appBarLayoutBottom;

    private ViewPager fragmentViewPager;
    private PlaceListViewModel mPlacesListViewModel;
    private SearchView searchView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout collapseAppBarLayout;
    private BottomNavigationView bottomNavigationView;

    private PlacesClient mPlacesClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLocation;

    private QuizMapFragment quizMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizwalk);

        searchView = findViewById(R.id.search_view);

        mPlacesListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);

        tabLayout = (TabLayout) findViewById(R.id.tablayoutid);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapseAppBarLayout = findViewById(R.id.collapse_appbar_layout);
        collapseAppBarLayout.setExpanded(true);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewListener());


        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0 || tab.getPosition() == 1) {
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) collapseAppBarLayout.getLayoutParams();
                    params.height = 200; // COLLAPSED_HEIGHT
                    collapseAppBarLayout.setLayoutParams(params);
                    collapseAppBarLayout.setExpanded(true, true);

                } else if(tab.getPosition() == 2) {
                    CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams) collapseAppBarLayout.getLayoutParams();
                    params.height = 0; // EXPANDED_HEIGHT

                    collapseAppBarLayout.setLayoutParams(params);
                    collapseAppBarLayout.setExpanded(false, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        quizMapFragment = new QuizMapFragment();
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        fragmentViewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(quizMapFragment, "Kart-vy");
        adapter.AddFragment(new QuizListFragment(), "List-vy");
        adapter.AddFragment(new MyQuizPlacesFragment(), "Mina valda platser");

        fragmentViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(fragmentViewPager);

        //initSearchView();


        /* INIT GOOGLE PLACES

         */

        if(!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), ConstantKeys.API_GOOGLE_PLACES_KEY);
        }

        final AutocompleteSupportFragment autoCompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autoCompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.USER_RATINGS_TOTAL, Place.Field.VIEWPORT, Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.TYPES));
        autoCompleteSupportFragment.setCountry("se");

        autoCompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                 // GooglePlaceModel(String address, int user_ratings_total, String ID, String name, String[] types)
                //new GooglePlaceModel();
               // System.out.println(place.getTypes().get(0).toString());
                mPlacesListViewModel.addPlace(new GooglePlaceModel(place.getAddress(), place.getLatLng().latitude, place.getLatLng().longitude, place.getUserRatingsTotal(), place.getId(), place.getName(), place.getTypes().get(0).toString()));
                quizMapFragment.zoomToLocation(latLng);
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


        appBarLayoutBottom = (AppBarLayout) findViewById(R.id.appbarid2);
        //testRetroFitRequest();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void testRetroFitRequest() {
        searchPlaceApi("stockholm+points+of+interest", "se");
    }

    public void searchPlaceApi(String query, String language) {
        mPlacesListViewModel.searchPlaceApi(query, language);

    }

    private void initSearchView() {



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mPlacesListViewModel.searchPlaceApi(s+"+points+of+interest", "se");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    class BottomNavigationViewListener implements BottomNavigationView.OnNavigationItemSelectedListener {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Intent intent = null;
                switch (item.getItemId())
                {
                    case R.id.action_home:
                        intent = new Intent(getApplicationContext(), TeacherMainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_help:
                        showHelpDialog();
                        break;
                    case R.id.action_my_profile:
                        intent = new Intent(getApplicationContext(), TeacherMainActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        }

    private void showHelpDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("HJÄLP!");
        builder1.setCancelable(true);

        final LinearLayout linearLayout = new LinearLayout(this);
        final TextView helpText = new EditText(this);

        helpText.setText("Hjälp!\n\n " +
                         "1. Sök efter plats(er) i sökrutan.\n" +
                         "2. Bla bla bla.\n" +
                         "3. Lägg till platser till tipspromenad.. etc\netc\n");

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(helpText);


        builder1.setView(linearLayout);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.filters_pointsofinterest) {
            searchPlaceApi("stockholm+points+of+interest", "se");
            Toast.makeText(this, "Visar intressanta platser", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.filters_artgallery) {
            searchPlaceApi("stockholm+art_gallery", "se");
            Toast.makeText(this, "Visar museum och gallerier", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.filters_churches) {
            searchPlaceApi("stockholm+place_of_worship", "se");
            Toast.makeText(this, "Visar kyrkor", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.filters_naturalfeature) {
            searchPlaceApi("stockholm+natural_feature", "se");
            Toast.makeText(this, "Visar natur-platser", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.filters_schools) {
            searchPlaceApi("stockholm+school", "se");
            Toast.makeText(this, "Visar skolor", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.filters_nearby) {
            Toast.makeText(this, "Rensar GooglePlace-platser", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_search_filter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
