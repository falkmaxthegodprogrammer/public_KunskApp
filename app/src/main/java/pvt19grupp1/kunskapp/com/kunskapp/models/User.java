package pvt19grupp1.kunskapp.com.kunskapp.models;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userId;

    private List<QuizWalk> myQuizzes;
    private String email;

    public User(String userId) {
        this.userId = userId;
        myQuizzes = new ArrayList<>();
    }


    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public List<QuizWalk> getMyQuizzes() {
        return myQuizzes;
    }

    public void setMyQuizzes(List<QuizWalk> myQuizzes) {
        this.myQuizzes = myQuizzes;
    }

    public void addQuizWalk(QuizWalk qw) {
        myQuizzes.add(qw);
    }
}