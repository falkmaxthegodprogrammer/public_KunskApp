package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;

public class QuizListFragment extends Fragment implements OnPlaceListListener {

    View view;
    private PlaceRecyclerAdapter placeRecyclerAdapter;
    private PlaceListViewModel mPlacesListViewModel;
    private RecyclerView recyclerView;

    public QuizListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.places_list);
        initRecyclerView();
        //subscribeObservers();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlacesListViewModel = ViewModelProviders.of(getActivity()).get(PlaceListViewModel.class);
        subscribeObservers();
    }

    private void initRecyclerView() {
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(8);
        recyclerView.addItemDecoration(itemDecorator);
        placeRecyclerAdapter = new PlaceRecyclerAdapter(this);
        recyclerView.setAdapter(placeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
    }

    private void subscribeObservers() {
        mPlacesListViewModel.getGooglePlaces().observe(getActivity(), new Observer<List<GooglePlaceModel>>() {
            @Override
            public void onChanged (@Nullable List<GooglePlaceModel> places) {
                placeRecyclerAdapter.setmGooglePlaces(places);
            }
        });
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
        GooglePlaceModel place = placeRecyclerAdapter.getSelectedPlace(position);
        System.out.println(place.getName());
    }
}
