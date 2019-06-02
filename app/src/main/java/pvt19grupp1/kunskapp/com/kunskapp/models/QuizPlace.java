package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuizPlace {

    private GooglePlaceModel googlePlace;
    private String name;
    private double rating;
    private ArrayList<Question> questions;
    private double latitude;
    private double longitude;


    public QuizPlace(GooglePlaceModel googlePlace) {
        this.googlePlace = googlePlace;
        this.name = googlePlace.getName();
        this.rating = googlePlace.getRating();
        this.rating = rating;
        this.questions = new ArrayList<Question>();
        this.latitude = googlePlace.getLat();
        this.longitude = googlePlace.getLng();
        this.questions = new ArrayList<Question>();

    }

    public QuizPlace(String name, double latitude, double longitude) {
        this.googlePlace = new GooglePlaceModel(latitude, longitude, name);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.questions = new ArrayList<>();
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public GooglePlaceModel getGooglePlace() {
        return googlePlace;
    }

    public String getName() {
        return name;
    }



    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(googlePlace.getId() != null) {
            return googlePlace.getId().equals(((QuizPlace) o).getGooglePlace().getId());
        } else {
            return name.equals(((QuizPlace) o).getName());
        }
    }

    @Override
    public String toString() {
        return "QuizPlace{" +
                "rating=" + rating +
                ", questions=" + questions +
                '}';
    }

}
