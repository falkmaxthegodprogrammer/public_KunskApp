package pvt19grupp1.kunskapp.com.kunskapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlaceModel implements Parcelable {

    private String formatted_adress;
    private String icon;
    private String id;
    private String name;
    private String place_id;
    private double lat;
    private double lng;
    private double rating;
    private int user_ratings_total;
    private String[] types;

    public GooglePlaceModel(String formatted_adress, String icon, String id, String name, String place_id, double rating, int user_ratings_total, String[] types) {
        this.formatted_adress = formatted_adress;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.user_ratings_total = user_ratings_total;
        this.types = types;
    }

    public GooglePlaceModel() {

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
        this.place_id = in.readString();
        this.rating = in.readDouble();
        this.user_ratings_total = in.readInt();
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
        dest.writeString(place_id);
        dest.writeDouble(rating);
        dest.writeInt(user_ratings_total);
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

    public String getPlace_id() {
        return place_id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getRating() {
        return rating;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setName(String name) { this.name = name; }


}
