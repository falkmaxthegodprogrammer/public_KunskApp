package pvt19grupp1.kunskapp.com.kunskapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuestionRepository;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

public class QuizListFragment extends Fragment implements OnPlaceListListener {

    View view;
    private PlaceRecyclerAdapter placeRecyclerAdapter;
    private PlaceListViewModel mPlacesListViewModel;
    private RecyclerView recyclerView;
    private QuizPlaceViewModel mQuizPlacesListViewModel;
    private SearchView searchView;
    private Toolbar toolbar;

    private Question question1;
    private Question question2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.places_list);
        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlacesListViewModel = ViewModelProviders.of(getActivity()).get(PlaceListViewModel.class);
        mQuizPlacesListViewModel = ViewModelProviders.of(getActivity()).get(QuizPlaceViewModel.class);
            if(mQuizPlacesListViewModel.getQuizPlaces().getValue() == null) {
                List<QuizPlace> tempList = new ArrayList<>();
                mQuizPlacesListViewModel.setmQuizPlaces(tempList);
            }
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

    @Override
    public void onPlaceClick(int position) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        intent.putExtra("place", placeRecyclerAdapter.getSelectedPlace(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        Toast.makeText(getActivity(), "Clicked on category", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClick(int position) {
        int initialSize = mQuizPlacesListViewModel.getQuizPlaces().getValue().size();
        QuizPlace qp = new QuizPlace(placeRecyclerAdapter.getSelectedPlace(position));
        mQuizPlacesListViewModel.addQuizPlace(qp);
        int currentSize = mQuizPlacesListViewModel.getQuizPlaces().getValue().size();
        displayToastIfQuizPlaceAdded(initialSize, currentSize, qp.getName());
    }

    public void displayToastIfQuizPlaceAdded(int initialSize, int currentSize, String name) {
        if(currentSize > initialSize || initialSize == 0) {
            Toast.makeText(getActivity(), "Lade till " + name + " till tipspromenaden!", Toast.LENGTH_SHORT).show();
        } else if(currentSize <= initialSize) {
            Toast.makeText(getActivity(), "Platsen " + name + " redan tillagd till tipspromenaden!", Toast.LENGTH_LONG).show();
        }
    }

}
