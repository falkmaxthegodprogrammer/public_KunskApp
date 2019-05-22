package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.util.Testing;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;

public class PlaceListActivity extends BaseActivity implements OnPlaceListListener {

        private static final String TAG = "PlacesListActicity";
        private PlaceListViewModel mPlacesListViewModel;
        private PlaceRecyclerAdapter placeRecyclerAdapter;
        private RecyclerView recyclerView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_places_list);

            recyclerView = findViewById(R.id.places_list);
            mPlacesListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);

            initRecyclerView();
            subscribeObservers();
            testRetroFitRequest();
            initSearchView();
        }

        private void subscribeObservers() {
            mPlacesListViewModel.getGooglePlaces().observe(this, new Observer<List<GooglePlaceModel>>() {
                @Override
                public void onChanged (@Nullable List<GooglePlaceModel> places) {
                    placeRecyclerAdapter.setmGooglePlaces(places);
                }
            });
        }

        public void searchPlaceApi(String query, String language) {
            mPlacesListViewModel.searchPlaceApi(query, language);

        }

        private void initRecyclerView() {
            VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(5);
            recyclerView.addItemDecoration(itemDecorator);
            placeRecyclerAdapter = new PlaceRecyclerAdapter(this);
            recyclerView.setAdapter(placeRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        }

        private void initSearchView() {
            final SearchView searchView = findViewById(R.id.search_view);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    placeRecyclerAdapter.displayLoading();
                    mPlacesListViewModel.searchPlaceApi(s+"+points+of+interest", "se");
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

        private void testRetroFitRequest() {
            searchPlaceApi("stockholm+points+of+interest", "se");
        }

        @Override
        public void onPlaceClick(int position) {
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.putExtra("place", placeRecyclerAdapter.getSelectedPlace(position));
            startActivity(intent);
        }

        @Override
        public void onCategoryClick(String category) {

        }

    @Override
    public void onButtonClick(int position) {

    }
}


