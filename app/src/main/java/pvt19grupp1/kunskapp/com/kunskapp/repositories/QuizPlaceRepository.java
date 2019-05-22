package pvt19grupp1.kunskapp.com.kunskapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.requests.PlaceApiClient;

public class QuizPlaceRepository {

    private static QuizPlaceRepository instance;
    private MutableLiveData<List<QuizPlace>> quizPlaces;

    public static QuizPlaceRepository getInstance() {
        if(instance == null) {
            instance = new QuizPlaceRepository();
        }
        return instance;
    }

    private QuizPlaceRepository() { quizPlaces = new MutableLiveData<>();  }

    public LiveData<List<QuizPlace>> getQuizPlaces() {
        return quizPlaces;
    }

    public void addQuizPlace(QuizPlace qp) {

        List<QuizPlace> tempList = quizPlaces.getValue();

        if(tempList == null) {
            tempList = new ArrayList<>();
            quizPlaces.setValue(tempList);
        }

        if(tempList != null) {
            for (QuizPlace qpTemp : tempList) {
                if (qpTemp.equals(qp))
                    return;
            }
        }

        tempList.add(qp);
        quizPlaces.setValue(tempList);
    }

    public void setmQuizPlaces(List<QuizPlace> qp) {
        quizPlaces.setValue(qp);
    }

}
