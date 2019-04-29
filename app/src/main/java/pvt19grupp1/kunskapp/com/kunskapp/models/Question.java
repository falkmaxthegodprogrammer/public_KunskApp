package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.Collection;

public class Question {

    private String questionText;
    private String author;
    private int authorId;
    private Collection<Answer> answers;

    public Question(String questionText, String author) {
        this.questionText = questionText;
        this.author = author;
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


}
