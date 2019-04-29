package pvt19grupp1.kunskapp.com.kunskapp.requests;

import pvt19grupp1.kunskapp.com.kunskapp.requests.responses.GooglePlacesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApi {

    //https://maps.googleapis.com/maps/api/place/textsearch/json?query=stockholm+point+of+interest&language=se&key=AIzaSyA8AavtO2TeNenIopl64aHSndgADkxJYWM
    //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJF7QkuDsDLz4R0rJ4SsxFl9w&key=AIzaSyA8AavtO2TeNenIopl64aHSndgADkxJYWM
    String BASE_URL ="https://maps.googleapis.com";

    //POLNTS OF INTEREST SEARCH
    @GET("maps/api/place/textsearch/json")
    Call<GooglePlacesResponse> searchPlace(
            @Query("query") String query,
            @Query("language") String language,
            @Query("key") String key
    );


}
