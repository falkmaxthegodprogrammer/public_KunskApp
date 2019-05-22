package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;


public class PlaceListFragment extends Fragment implements OnPlaceListListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final String TAG = "PlaceListFragment";
    private PlaceListViewModel mPlacesListViewModel;
    private PlaceRecyclerAdapter placeRecyclerAdapter;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public PlaceListFragment() {
        // Required empty public constructor
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
        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
    }

    private void initSearchView() {
        final SearchView searchView = getView().findViewById(R.id.search_view);
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
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        intent.putExtra("place", placeRecyclerAdapter.getSelectedPlace(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    @Override
    public void onButtonClick(int position) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        intent.putExtra("place", placeRecyclerAdapter.getSelectedPlace(position));
        startActivity(intent);
    }

    public static PlaceListFragment newInstance(String param1, String param2) {
        PlaceListFragment fragment = new PlaceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_places_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.places_list);

        mPlacesListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        testRetroFitRequest();
        initSearchView();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
