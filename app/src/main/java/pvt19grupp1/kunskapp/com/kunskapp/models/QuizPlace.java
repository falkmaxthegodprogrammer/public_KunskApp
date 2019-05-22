package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.Collection;

public class QuizPlace {

    private GooglePlaceModel googlePlace;
    private String name;
    private double rating;
    private Collection<Question> questions;

    public QuizPlace(GooglePlaceModel googlePlace) {
        this.googlePlace = googlePlace;
        this.name = googlePlace.getName();
        this.rating = googlePlace.getRating();
        this.rating = rating;
        this.questions = new ArrayList<Question>();
    }

    public QuizPlace() {

    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
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
        return googlePlace.getId().equals(((QuizPlace) o).getGooglePlace().getId());
    }

    @Override
    public String toString() {
        return "QuizPlace{" +
                "rating=" + rating +
                ", questions=" + questions +
                '}';
    }

}
