package pvt19grupp1.kunskapp.com.kunskapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;
    private GooglePlaceModel googlePlace;

    public ClusterMarker(LatLng position, String title, String snippet, int iconPicture, GooglePlaceModel googlePlace) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconPicture = iconPicture;
        this.googlePlace = googlePlace;
    }

    public ClusterMarker() {

    }

    public int getIconPicture() {
        return iconPicture;
    }

    public GooglePlaceModel getGooglePlace() {
        return googlePlace;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setIconPicture(int iconPicture) {
        this.iconPicture = iconPicture;
    }

    public void setGooglePlace(GooglePlaceModel googlePlace) {
        this.googlePlace = googlePlace;
    }

    @Override
    public LatLng getPosition() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
