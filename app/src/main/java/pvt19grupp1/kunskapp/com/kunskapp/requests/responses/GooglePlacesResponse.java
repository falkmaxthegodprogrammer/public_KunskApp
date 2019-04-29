package pvt19grupp1.kunskapp.com.kunskapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;

public class GooglePlacesResponse {

    @SerializedName("results")
    @Expose()
    private List<GooglePlaceModel> googlePlaces = new ArrayList<GooglePlaceModel>();

    public List<GooglePlaceModel> getGooglePlaces() {
        return googlePlaces;
    }

    public void setResults(List<GooglePlaceModel> results) {
        this.googlePlaces = results;
    }


    @Override
    public String toString() {
        return "GooglePlacesResponse{" +
                "results=" + googlePlaces +
                '}';
    }
}
