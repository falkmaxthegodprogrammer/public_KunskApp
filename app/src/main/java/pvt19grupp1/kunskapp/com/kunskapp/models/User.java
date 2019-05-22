package pvt19grupp1.kunskapp.com.kunskapp.models;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class User {

    private String userId;

    private List<QuizSession> myQuizzes;

    public User(String userId) {
        this.userId = userId;

    }

    public User() {
    }


    public List<QuizSession> getMyQuizzes() {
        return myQuizzes;
    }

    public void setMyQuizzes(List<QuizSession> myQuizzes) {
        this.myQuizzes = myQuizzes;
    }
}
