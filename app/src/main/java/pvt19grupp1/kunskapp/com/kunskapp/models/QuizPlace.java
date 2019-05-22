package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.Collection;

public class QuizPlace {

    private GooglePlaceModel googlePlace;
    private double rating;
    private Collection<Question> questions;

    public QuizPlace(GooglePlaceModel googlePlace, double rating, Collection<Question> questions) {
        this.googlePlace = googlePlace;
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

    @Override
    public String toString() {
        return "QuizPlace{" +
                "rating=" + rating +
                ", questions=" + questions +
                '}';
    }

}
