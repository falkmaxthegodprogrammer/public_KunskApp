package pvt19grupp1.kunskapp.com.kunskapp;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.requests.*;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.requests.ServiceGenerator;
import pvt19grupp1.kunskapp.com.kunskapp.requests.responses.GooglePlacesResponse;
import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListActivity extends BaseActivity {

    private static final String TAG = "QuestionListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("testing retrofit request");
                testRetroFitRequest();
                if(mProgressBar.getVisibility() == View.VISIBLE) {
                    showProgressBar(false);
                } else {
                    showProgressBar(true);
                }
             }
        });
    }

    private void testRetroFitRequest() {
        PlaceApi placeApi = ServiceGenerator.getPlaceApi();

        Call<GooglePlacesResponse> responseCall = placeApi.searchPlace(
                "stockholm+points+of+interest",
                "se",
                ConstantKeys.API_GOOGLE_PLACES_KEY
                );
        responseCall.enqueue(new Callback<GooglePlacesResponse>() {
            @Override
            public void onResponse(Call<GooglePlacesResponse> call, Response<GooglePlacesResponse> response) {
                Log.d(TAG, "onResponse: server response " + response.toString());
                if(response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    List<GooglePlaceModel> places = new ArrayList<>(response.body().getGooglePlaces());
                    for(GooglePlaceModel place : places) {
                        Log.d(TAG, "onResponse: " + place.getName());
                    }
                } else {
                    try{
                        Log.d(TAG, response.errorBody().string());
                    } catch(IOException e)  {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GooglePlacesResponse> call, Throwable t) {
                Log.d(TAG, "Failed");
                t.printStackTrace();
            }
        });

    }



}
