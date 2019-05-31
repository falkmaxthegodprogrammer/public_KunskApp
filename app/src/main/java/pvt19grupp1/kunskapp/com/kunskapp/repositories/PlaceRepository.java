package pvt19grupp1.kunskapp.com.kunskapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.requests.PlaceApiClient;

public class PlaceRepository {

    private static PlaceRepository instance;
    private PlaceApiClient mPlaceApiClient;

    public static PlaceRepository getInstance() {
        if(instance == null) {
            instance = new PlaceRepository();
        }
        return instance;
    }

    private PlaceRepository() {
        mPlaceApiClient = PlaceApiClient.getInstance();
    }

    public LiveData<List<GooglePlaceModel>> getGooglePlaces() {
        return mPlaceApiClient.getGooglePlaces();
    }

    public void searchPlaceApi(String query, String language) {
        mPlaceApiClient.searchGooglePlacesApi(query, language);
    }

    public void addPlace(GooglePlaceModel gpm) {
        mPlaceApiClient.addPlace(gpm);
    }

    public void clearGooglePlaces() {
        mPlaceApiClient.clearGooglePlaces();
    }

}

