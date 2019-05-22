package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.ViewPagerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;

public class CreateQuizWalkActivity extends BaseActivity  {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private AppBarLayout appBarLayoutBottom;

    private ViewPager fragmentViewPager;
    private PlaceListViewModel mPlacesListViewModel;
    private Button btnFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizwalk);

        mPlacesListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);


        tabLayout = (TabLayout) findViewById(R.id.tablayoutid);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        fragmentViewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new QuizMapFragment(), "Kart-vy");
        adapter.AddFragment(new QuizListFragment(), "List-vy");
        adapter.AddFragment(new MyQuizPlacesFragment(), "Mina valda platser");

        fragmentViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(fragmentViewPager);

        initSearchView();

        appBarLayoutBottom = (AppBarLayout) findViewById(R.id.appbarid2);
        btnFilter = findViewById(R.id.btn_filter);
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
        final SearchView searchView = findViewById(R.id.search_view);
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
            System.out.println("Platser i närheten");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_search_filter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
