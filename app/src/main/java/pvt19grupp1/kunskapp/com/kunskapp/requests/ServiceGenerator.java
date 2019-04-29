package pvt19grupp1.kunskapp.com.kunskapp.requests;

import android.content.pm.PackageManager;

import pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(ConstantKeys.API_GOOGLE_PLACES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PlaceApi placeApi = retrofit.create(PlaceApi.class);

    public static PlaceApi getPlaceApi()  {
        return placeApi;
    }

}
