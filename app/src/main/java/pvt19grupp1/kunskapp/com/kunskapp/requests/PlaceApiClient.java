package pvt19grupp1.kunskapp.com.kunskapp.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pvt19grupp1.kunskapp.com.kunskapp.AppExecutors;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.requests.responses.GooglePlacesResponse;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import retrofit2.Call;
import retrofit2.Response;

public class PlaceApiClient {

    private static PlaceApiClient instance;
    private MutableLiveData<List<GooglePlaceModel>> mGooglePlaces;
    private RetrievePlacesRunnable mRetrievePlacesRunnable;

    public static PlaceApiClient getInstance() {
        if(instance == null) {
            instance = new PlaceApiClient();
        }
        return instance;
    }

    private PlaceApiClient() {
        mGooglePlaces = new MutableLiveData<>();
    }

    public LiveData<List<GooglePlaceModel>> getGooglePlaces() {
        return mGooglePlaces;
    }

    public void searchGooglePlacesApi(String query, String language) {
        if(mRetrievePlacesRunnable != null) {
            mRetrievePlacesRunnable = null;
        }
        mRetrievePlacesRunnable = new RetrievePlacesRunnable(query, language);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrievePlacesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, ConstantKeys.NETWORK_TIME_OUT_LIMIT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePlacesRunnable implements Runnable {

        private static final String TAG = "RetrievePlacesRunnable";
        private String query;
        private String language;
        private boolean cancelRequest;

        public RetrievePlacesRunnable(String query, String language) {
            this.query = query;
            this.language = language;
        }

        @Override
        public void run() {

            try {
                Response response = getPlaces(query, language).execute();
                if(cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<GooglePlaceModel> list = new ArrayList<>(((GooglePlacesResponse) response.body()).getGooglePlaces());
                    mGooglePlaces.postValue(list);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mGooglePlaces.postValue(null);
                }
                } catch (IOException e) {
                e.printStackTrace();
                mGooglePlaces.postValue(null);
            }
        }

        private Call<GooglePlacesResponse> getPlaces(String query, String language) {
            return ServiceGenerator.getPlaceApi().searchPlace(
                    query,
                    language,
                    ConstantKeys.API_GOOGLE_PLACES_KEY
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: aborting request");
            cancelRequest = true;
        }

    }

}
