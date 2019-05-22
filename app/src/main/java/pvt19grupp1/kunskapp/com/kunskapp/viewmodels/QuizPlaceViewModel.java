package pvt19grupp1.kunskapp.com.kunskapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.PlaceRepository;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuizPlaceRepository;

public class QuizPlaceViewModel extends ViewModel {

    private QuizPlaceRepository mQuizPlaceRepository;

    public QuizPlaceViewModel() {
        mQuizPlaceRepository = QuizPlaceRepository.getInstance();
    }

    public LiveData<List<GooglePlaceModel>> getGooglePlaces() {
        return mQuizPlaceRepository.getQuizPlaces();
    }

}
