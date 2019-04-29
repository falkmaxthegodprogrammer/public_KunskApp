package pvt19grupp1.kunskapp.com.kunskapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

public class Place implements Parcelable {

    private String name;
    private String description;
    private String imageIcon;
    private String formattedAdress;

    // location data
    private double lat;
    private double lng;

    //viewport data - lat+long northeast
    private double viewportLatNE;
    private double viewportLngNE;

    //viewport data - lat+long southwest
    private double viewportLatSW;
    private double viewportLngSW;

    private String[] types;

    //google places ID
    private int googlePlaceId;

    public Place(String name, String desc, String imageIcon, double lat, double lng, String[] types, int googlePlaceId,
                 double viewportLatNE, double viewportLngNE, double viewportLatSW, double viewportLngSW) {
        this.name = name;
        this.description = desc;
        this.imageIcon = imageIcon;
        this.lat = lat;
        this.lng = lng;
        this.viewportLatNE = viewportLatNE;
        this.viewportLngNE = viewportLngNE;
        this.viewportLatSW = viewportLatSW;
        this.viewportLngSW = viewportLngSW;
        this.types = types;
        this.googlePlaceId = googlePlaceId;
    }

    protected Place(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageIcon = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageIconUrl() {
        return imageIcon;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    /* PARCELABLE IMPLEMENTATION */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageIcon);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageIcon='" + imageIcon + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", types=" + Arrays.toString(types) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return googlePlaceId == place.googlePlaceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(googlePlaceId);
    }
}
