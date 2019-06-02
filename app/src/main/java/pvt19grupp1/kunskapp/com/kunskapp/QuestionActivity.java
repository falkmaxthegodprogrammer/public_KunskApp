package pvt19grupp1.kunskapp.com.kunskapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import pvt19grupp1.kunskapp.com.kunskapp.BaseActivity;
import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.Answer;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;

public class QuestionActivity extends BaseActivity {

    ProgressBar mProgressBar;
    TextView txtViewQuestionText, txtViewInfoBar;

    Button btnAnswer1;
    Button btnAnswer2;
    Button btnAnswer3;
    Button btnAnswer4;

    private ArrayList<Question> questions;
    private int questionCount;
    private int currentQuestion = 0;

    int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questions = new ArrayList<>();
        setContentView(R.layout.activity_question);

        CountDownTimer mCountDownTimer;

        txtViewInfoBar = findViewById(R.id.text_view_active_question_infobar);
        txtViewQuestionText = findViewById(R.id.text_view_question);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);

        btnAnswer1 = findViewById(R.id.btn_answer_1);
        btnAnswer2 = findViewById(R.id.btn_answer_2);
        btnAnswer3 = findViewById(R.id.btn_answer_3);
        btnAnswer4 = findViewById(R.id.btn_answer_4);

        int colorFrom = ContextCompat.getColor(this, R.color.colorAccent);
        int colorTo = ContextCompat.getColor(this, R.color.colorAccentLighter);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setDuration(1200); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                txtViewInfoBar.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();


        mProgressBar.setProgress(100);

        mCountDownTimer = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(0);
            }
        };
        mCountDownTimer.start();

        getIncomingIntent();

        questionCount = questions.size();

        txtViewInfoBar.setText("Fråga 1" + "/" + questions.size());
        txtViewQuestionText.setText(questions.get(currentQuestion).getQuestionText());

        btnAnswer1.setText(questions.get(currentQuestion).getAnswers().get(0).getAnswerText());
        btnAnswer2.setText(questions.get(currentQuestion).getAnswers().get(1).getAnswerText());
        btnAnswer3.setText(questions.get(currentQuestion).getAnswers().get(2).getAnswerText());
        btnAnswer4.setText(questions.get(currentQuestion).getAnswers().get(3).getAnswerText());

        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questions.get(currentQuestion).getAnswers().get(0).isCorrect()) {
                    System.out.println("Svar: rätt!");
                } else {
                    System.out.println("Svar: fel!");
                }
                checkQuestion();
            }
        });
        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questions.get(currentQuestion).getAnswers().get(1).isCorrect()) {
                    System.out.println("Svar: rätt!");
                } else {
                    System.out.println("Svar: fel!");
                }
                checkQuestion();
            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questions.get(currentQuestion).getAnswers().get(2).isCorrect()) {
                    System.out.println("Svar: rätt!");
                } else {
                    System.out.println("Svar: fel!");
                }
                checkQuestion();
            }
        });
        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questions.get(currentQuestion).getAnswers().get(3).isCorrect()) {
                    System.out.println("Svar: rätt!");
                } else {
                    System.out.println("Svar: fel!");
                }
                checkQuestion();
            }
        });

    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("questions")) {
            ArrayList<Question> test = getIntent().getParcelableArrayListExtra(("questions"));
            questions.addAll(test);
        }
    }

    private void checkQuestion() {

        CountDownTimer timer = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(0);
            }
        };
        timer.start();
        currentQuestion++;

        if(currentQuestion == questionCount) {
            finish();
        } else {
            txtViewInfoBar.setText("Fråga " + (currentQuestion + 1) + "/" + questionCount);
            txtViewQuestionText.setText(questions.get(currentQuestion).getQuestionText());
            btnAnswer1.setText(questions.get(currentQuestion).getAnswers().get(0).getAnswerText());
            btnAnswer2.setText(questions.get(currentQuestion).getAnswers().get(1).getAnswerText());
            btnAnswer3.setText(questions.get(currentQuestion).getAnswers().get(2).getAnswerText());
            btnAnswer4.setText(questions.get(currentQuestion).getAnswers().get(3).getAnswerText());
        }

        if(currentQuestion == questionCount) {
            finish();
        }

    }


}



