package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class Question {

    private String questionText;
    private String author;
    private String googlePlaceId;
    private int authorId;
    private Collection<Answer> answers;
    private String answerID;

    public Question(String questionText, String googlePlaceId) {
        this.questionText = questionText;
        this.author = author;
        this.googlePlaceId = googlePlaceId;
        this.answerID = UUID.randomUUID().toString();
        answers = new ArrayList<Answer>();
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAuthor() {
        return author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Collection getAnswers() {
        return answers;
    }

    public String getGooglePlaceId() { return googlePlaceId; }

    public void setGooglePlaceId(String googlePlaceId) { this.googlePlaceId = googlePlaceId; }

    public String getAnswerID() { return answerID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionText, question.questionText) &&
                Objects.equals(answerID, question.answerID) &&
                Objects.equals(googlePlaceId, question.googlePlaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerID, googlePlaceId);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
