package pvt19grupp1.kunskapp.com.kunskapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.requests.PlaceApiClient;

public class QuizPlaceRepository
{

    private static QuizPlaceRepository instance;
    private MutableLiveData<List<GooglePlaceModel>> mQuizPlaces;
    private LiveData<GooglePlaceModel> quizPlaces;

    public static QuizPlaceRepository getInstance() {
        if(instance == null) {
            instance = new QuizPlaceRepository();
        }
        return instance;
    }

    private QuizPlaceRepository() {
    }

    public LiveData<List<GooglePlaceModel>> getQuizPlaces() {
        return mQuizPlaces;
    }

    public void add() {

    }


}
