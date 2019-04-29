package pvt19grupp1.kunskapp.com.kunskapp.models;

public class Answer {

    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer(String answer) {
        return answer;
    }

    public void setAnswer(String answer) {
        answer = answer;
    }


}
