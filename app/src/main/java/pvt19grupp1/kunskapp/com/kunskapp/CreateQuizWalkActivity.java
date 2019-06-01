package pvt19grupp1.kunskapp.com.kunskapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
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
import com.google.api.LogDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.ViewPagerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.models.User;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

public class CreateQuizWalkActivity extends BaseActivity  {

    private static final String TAG = "CreateQuizWalkActivity";

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private AppBarLayout appBarLayoutBottom;

    private ViewPager fragmentViewPager;
    private PlaceListViewModel mPlacesListViewModel;

    public PlaceListViewModel getmPlacesListViewModel() {
        return mPlacesListViewModel;
    }

    private QuizPlaceViewModel mQuizPlacesViewModel;
    private SearchView searchView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout collapseAppBarLayout;
    private BottomNavigationView bottomNavigationView;

    private PlacesClient mPlacesClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLocation;
    private TextView mTextViewLocation;
    private TextView mMessage;
    private TextView textViewQuizInfo;

    private QuizMapFragment quizMapFragment;
    private User masterUser;

    private QuizWalk quizWalkInstance;

    private static final int[] ACTION_BAR_SIZE = new int[] {
            android.R.attr.actionBarSize
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quizWalkInstance = null;
        masterUser = ((UserClient)(getApplicationContext())).getUser();

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_create_quizwalk, null);
        setContentView(view);

        mPlacesListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);
        mQuizPlacesViewModel = ViewModelProviders.of(this).get(QuizPlaceViewModel.class);

        tabLayout = (TabLayout) findViewById(R.id.tablayoutid);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapseAppBarLayout = findViewById(R.id.collapse_appbar_layout);
        collapseAppBarLayout.setExpanded(true);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewListener());

        textViewQuizInfo = findViewById(R.id.text_view_quizinfo);
        textViewQuizInfo.setText("Inga tillagda platser!");
        textViewQuizInfo.setVisibility(View.INVISIBLE);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0 || tab.getPosition() == 1) {
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) collapseAppBarLayout.getLayoutParams();
                    params.height = 200; // COLLAPSED_HEIGHT
                    collapseAppBarLayout.setLayoutParams(params);
                    collapseAppBarLayout.setExpanded(true, true);
                    textViewQuizInfo.setVisibility(View.VISIBLE);

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
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);

        quizMapFragment = new QuizMapFragment();
        fragmentViewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(quizMapFragment, "Kart-vy");
        adapter.AddFragment(new QuizListFragment(), "List-vy");
        adapter.AddFragment(new MyQuizPlacesFragment(), "Mina valda platser");


        fragmentViewPager.setAdapter(adapter);
        fragmentViewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(fragmentViewPager);

        /**
         *
         * INIT GOOGLE PLACES AUTO-COMPLETE
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
                mPlacesListViewModel.addPlace(new GooglePlaceModel(place.getAddress(), place.getLatLng().latitude, place.getLatLng().longitude, place.getUserRatingsTotal(), place.getId(), place.getName(), place.getTypes().get(0).toString()));
                quizMapFragment.zoomToLocation(latLng);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: Google Auto Complete search unable to complete: " + status.getStatusMessage());
            }

        });

        appBarLayoutBottom = (AppBarLayout) findViewById(R.id.appbarid2);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        int colorFrom = ContextCompat.getColor(this, R.color.colorAccent);
        int colorTo = ContextCompat.getColor(this, R.color.colorAccentLighter);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),colorFrom, colorTo);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setDuration(1200); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textViewQuizInfo.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    public void searchPlaceApi(String query, String language) {
        mPlacesListViewModel.searchPlaceApi(query, language);

    }

    public void updateQuizInfoTextDefaultMsg() {
        textViewQuizInfo.setText("Inga platser tillagda!");
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
            Toast.makeText(this, "Visar intressanta platser", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.filters_artgallery) {
            searchPlaceApi("stockholm+art_gallery", "se");
            Toast.makeText(this, "Visar museum och gallerier", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.filters_churches) {
            searchPlaceApi("stockholm+place_of_worship", "se");
            Toast.makeText(this, "Visar kyrkor", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.filters_naturalfeature) {
            searchPlaceApi("stockholm+natural_feature", "se");
            Toast.makeText(this, "Visar natur-platser", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.filters_schools) {
            searchPlaceApi("stockholm+school", "se");
            Toast.makeText(this, "Visar skolor", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.filters_nearby) {
            mPlacesListViewModel.clearGooglePlaces();
            Toast.makeText(this, "Rensar GooglePlace-platser", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_search_filter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showLoadingMessage(String text) {
        // Remove any previous notifications
        removeLoadingMessage();

        // Initialize the layout
        if (mMessage == null) {
            final LayoutInflater inflater = getLayoutInflater();
            mMessage = (TextView) inflater.inflate(R.layout.text_view_quizinfo, null);
            mMessage.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            mMessage.setText(text);
        }

        // Add the View to the Window
        getWindowManager().addView(mMessage, getActionBarLayoutParams());
    }

    private void removeLoadingMessage() {
        if (mMessage != null && mMessage.getWindowToken() != null) {
            getWindowManager().removeViewImmediate(mMessage);
            mMessage = null;
        }
    }

    public void updateQuizInfoText(int noPlaces, int metersLength, int evaluedTime) {
        textViewQuizInfo.setVisibility(View.VISIBLE);
        textViewQuizInfo.setText("Platser: " + noPlaces + "  Sträcka: " + metersLength + " m  Uppskattad tid: " + evaluedTime + " minuter");
    }

    private WindowManager.LayoutParams getActionBarLayoutParams() {
        // Retrieve the height of the status bar
        final Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        final int statusBarHeight = rect.top;

        // Retrieve the height of the ActionBar
        final TypedArray actionBarSize = obtainStyledAttributes(ACTION_BAR_SIZE);
        final int actionBarHeight = actionBarSize.getDimensionPixelSize(0, 0);
        actionBarSize.recycle();

        // Create the LayoutParams for the View
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, actionBarHeight,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP;
        params.x = 0;
        params.y = statusBarHeight;
        return params;
    }

    public void loadQuizWalk(QuizWalk qw) {
        mQuizPlacesViewModel.clearQuizPlaces();
        mQuizPlacesViewModel.setmQuizPlaces(qw.getQuizPlaces());
    }

    public QuizMapFragment getQuizMapFragment() {
        return quizMapFragment;
    }

    public User getMasterUser() { return masterUser; }

    @Override
    public void onResume() {
        super.onResume();

        if(masterUser == null) {
            masterUser = ((UserClient)(getApplicationContext())).getUser();
        }


    /*    mPlacesListViewModel.clearGooglePlaces();
        mQuizPlacesViewModel.clearQuizPlaces();
        quizMapFragment.clearLocalDataStructures(); */

        /* List<QuizPlace> tempList = new ArrayList<>();
        if(mQuizPlacesViewModel.getQuizPlaces().getValue() != null && mQuizPlacesViewModel.getQuizPlaces().getValue().size() > 0) {
            Log.d(TAG, "onResume: ATTEMPTING TO RE-DRAW SAVED MAP STATE......");
            quizMapFragment.reDrawSavedMapState(mQuizPlacesViewModel.getQuizPlaces().getValue());
        } */


      /*  for(QuizPlace qp : tempList) {
            mQuizPlacesViewModel.addQuizPlace(qp);
        } */

        }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPlacesListViewModel.clearGooglePlaces();
        mQuizPlacesViewModel.clearQuizPlaces();
        quizMapFragment.clearLocalDataStructures();

    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void navigateToTab(int i) {
        tabLayout.getTabAt(i).select();
    }

    public QuizWalk getQuizWalkInstance() {
        return quizWalkInstance;
    }

    public void setQuizWalkInstance(QuizWalk quizWalk) {
        this.quizWalkInstance = quizWalk;
    }

}



