package pvt19grupp1.kunskapp.com.kunskapp.models;

import java.util.ArrayList;
import java.util.List;

public class QuizSession {

    private List<User> participants;
    private List<QuizPlace> quizLocations;

    private String quizWalkName;
    private int QUIZ_PIN;

    private boolean isQuizActive;

    public QuizSession(String name, int quizPin) {
        this.quizWalkName = name;
        this.QUIZ_PIN = quizPin;

        participants = new ArrayList<User>();
        quizLocations = new ArrayList<QuizPlace>();
    }

    public boolean isQuizActive() {
        return isQuizActive;
    }

    public void setQuizActive(boolean quizActive) {
        isQuizActive = quizActive;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<QuizPlace> getQuizLocations() {
        return quizLocations;
    }

    public void setQuizLocations(List<QuizPlace> quizLocations) {
        this.quizLocations = quizLocations;
    }

    public String getQuizWalkName() {
        return quizWalkName;
    }

    public void setQuizWalkName(String quizWalkName) {
        this.quizWalkName = quizWalkName;
    }

    public int getQUIZ_PIN() {
        return QUIZ_PIN;
    }

    public void setQUIZ_PIN(int QUIZ_PIN) {
        this.QUIZ_PIN = QUIZ_PIN;
    }


}
