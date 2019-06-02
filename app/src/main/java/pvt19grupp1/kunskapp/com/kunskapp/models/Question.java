package pvt19grupp1.kunskapp.com.kunskapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Question implements Parcelable {

    private String questionText;
    private String author;
    private String googlePlaceId;
    private int authorId;
    private ArrayList<Answer> answers;
    private String answerID;

    public Question(String questionText, String googlePlaceId) {
        this.questionText = questionText;
        this.author = author;
        this.googlePlaceId = googlePlaceId;
        this.answerID = UUID.randomUUID().toString();
        answers = new ArrayList<Answer>();
    }

    public Question(String questionText, ArrayList<Answer> answers) {
        this.questionText = questionText;
        this.answers = answers;
    }

    public Question(String questionText) {
        this.questionText = questionText;
        this.answers = new ArrayList<>();
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Question(Parcel in) {
        this.questionText = in.readString();
        this.answers = in.readArrayList(Answer.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeList(answers);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestionText() {
        return questionText;
    }

    public String getAuthor() {
        return author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public ArrayList<Answer> getAnswers() {
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
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(answerID, googlePlaceId);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }




}
