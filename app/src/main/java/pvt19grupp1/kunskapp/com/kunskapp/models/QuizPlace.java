package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.Collection;

public class QuizPlace extends Place {

    private double rating;
    private Collection<Question> questions;

    public QuizPlace(String name, String desc, String imageIcon, double lat, double lng, String[] types, int googlePlaceId, double viewportLatNE,
                     double viewportLngNE, double viewportLatSW, double viewportLngSW) {

        super(name, desc, imageIcon, lat, lng, types, googlePlaceId, viewportLatNE, viewportLngNE, viewportLatSW, viewportLngSW);

        questions = new ArrayList<Question>();
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
