package pvt19grupp1.kunskapp.com.kunskapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;

public class QuestionListViewModel extends ViewModel {

    private MutableLiveData<List<GooglePlaceModel>> mGooglePlaces = new MutableLiveData<>();

    public QuestionListViewModel() {

    }

    public LiveData<List<Question>> getQuestions() {
        return null;
    }

}