package pvt19grupp1.kunskapp.com.kunskapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.internal.impl.net.pablo.PlaceResult;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GooglePlaceModel implements Parcelable {

    private String formatted_adress;
    private String icon;
    private String id;
    private String name;

    private double rating;
    private int user_ratings_total;
    private String[] types;

    private String lat;
    private String lng;

    private LatLng latLng;

    private Geometry geometry;

    private List<Question> questions = new ArrayList<>();

    public GooglePlaceModel(String formatted_adress, String icon, String id, String name, double rating, int user_ratings_total, String[] types, String glat, String glng, Geometry geometry) {
        this.formatted_adress = formatted_adress;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.user_ratings_total = user_ratings_total;
        this.types = types;
        this.geometry = geometry;
    }

    public GooglePlaceModel(LatLng latLng, String name) {
        this.latLng = latLng;
        this.name = name;
        user_ratings_total = 0;
        this.types = new String[3];
    }

    public GooglePlaceModel(double latitude, double longitude, String name) {
        this.latLng = new LatLng(latitude, longitude);
        this.name = name;
        user_ratings_total = 0;
        this.types = new String[3];
    }

    // Arrays.asList(Place.Field.ADDRESS, Place.Field.USER_RATINGS_TOTAL, Place.Field.VIEWPORT, Place.Field.LAT_LNG, Place.Field.ID, , Place.Field.NAME, Place.Field.TYPES));

    public GooglePlaceModel(String address, double latitude, double longitude, int user_ratings_total, String ID, String name, String category) {
        this.formatted_adress = address;
        this.latLng = new LatLng(latitude, longitude);
        this.user_ratings_total = user_ratings_total;
        this.types = new String[3];
        types[0] = category;
        this.name = name;
        this.id = ID;
    }

    public boolean isUserCreated() {
        return id == null;
    }

    public GooglePlaceModel() {

    }

    public String getCategory() {
        if(types[0] == null)
            return "USER_CREATED";
        else
            return types[0];
    }

    public void addQuestion(Question question) {
          questions.add(question);
    }

    public List<Question> getQuestions() {
            return questions;
    }

    public static final Creator<GooglePlaceModel> CREATOR = new Creator<GooglePlaceModel>() {
        @Override
        public GooglePlaceModel createFromParcel(Parcel in) {
            return new GooglePlaceModel(in);
        }

        @Override
        public GooglePlaceModel[] newArray(int size) {
            return new GooglePlaceModel[size];
        }
    };

    public GooglePlaceModel(Parcel in) {
        this.formatted_adress = in.readString();
        this.icon = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.rating = in.readDouble();
        this.user_ratings_total = in.readInt();
        this.lat = in.readString();
        this.lng = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(formatted_adress);
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeDouble(rating);
        dest.writeInt(user_ratings_total);
        dest.writeString(lat);
        dest.writeString(lng);

    }

    public String getFormatted_adress() {
        return formatted_adress;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getTypes() { return types; }


    public Geometry getGeometry() {
        return geometry;
    }

    public boolean isGeometryNull() {
        return geometry == null;
    }


    public double getLat() {
       // return Double.valueOf(lat);
        if(geometry != null) {
            return geometry.location.lat;
        } else {
            return latLng.latitude;
        }
    }

    public double getLng() {
         if(geometry != null) {
            return geometry.location.lng;
        } else {
            return latLng.longitude;
        }
    }

   // public String[] getLocation() {
      //  return location;
    //}

    public double getRating() {
        return rating;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setName(String name) { this.name = name; }


     public class Geometry {
        Rect bounds;
        LatLng location;
        String location_type;
        Rect viewport;

         public class Rect {
            LatLng northeast, southwest;
        }

         public class LatLng {
            public double lat, lng;
        }

        public Rect getBounds() { return bounds; }
        public LatLng getLocation() { return location; }
        public Rect getViewport() { return viewport; }
    }


}
