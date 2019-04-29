package pvt19grupp1.kunskapp.com.kunskapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Place;

public class PlaceResponse {

    @SerializedName("place")
    @Expose()

    private Place place;
    private List<GooglePlaceModel> places;

    public Place getPlace() {
        return place;
    }

    public List<GooglePlaceModel> getPlaces() {
        return places;
    }
}
