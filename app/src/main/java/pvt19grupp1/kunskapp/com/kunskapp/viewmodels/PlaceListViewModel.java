package pvt19grupp1.kunskapp.com.kunskapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.PlaceRepository;
import pvt19grupp1.kunskapp.com.kunskapp.requests.PlaceApiClient;

public class PlaceListViewModel extends ViewModel {

    private PlaceRepository mPlaceRepository;

    public PlaceListViewModel() {
        mPlaceRepository = PlaceRepository.getInstance();
    }
    public LiveData<List<GooglePlaceModel>> getGooglePlaces() {
        return mPlaceRepository.getGooglePlaces();
    }

    public void searchPlaceApi(String query, String language) {
        mPlaceRepository.searchPlaceApi(query, language);
    }


}
