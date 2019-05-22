package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

public class MyQuizPlacesFragment extends Fragment {

    View view;
    private QuizPlaceViewModel mQuizPlacesViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.myquizplaces_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQuizPlacesViewModel = ViewModelProviders.of(getActivity()).get(QuizPlaceViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers() {
        mQuizPlacesViewModel.getQuizPlaces().observe(getActivity(), new Observer<List<QuizPlace>>() {
            @Override
            public void onChanged (@Nullable List<QuizPlace> places) {
                System.out.println("Some place added! Data change being observed in MyQuizPlacesFragment in QuizPlaceViewModel ");
            }
        });
    }




}
