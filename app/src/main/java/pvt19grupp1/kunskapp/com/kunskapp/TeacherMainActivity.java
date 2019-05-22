package pvt19grupp1.kunskapp.com.kunskapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TeacherMainActivity extends BaseActivity {

    private Button btnStartQuiz;
    private Button btnPreviousQuizzes;
    private Button btnNewQuiz;
    private Button btnSeeResults;
    private Button btnSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        btnStartQuiz = (Button) findViewById(R.id.btn_start_quiz);
        btnPreviousQuizzes = (Button) findViewById(R.id.btn_my_quizwalks);
        btnNewQuiz = (Button) findViewById(R.id.btn_create_quizwalk);
        btnSeeResults = (Button) findViewById(R.id.btn_see_results);
        btnSettings = (Button) findViewById(R.id.btn_settings);

        btnPreviousQuizzes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, MyQuizWalksActivity.class);
                startActivity(intent);
            }
        });


        btnNewQuiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, CreateQuizWalkActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnSeeResults.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, FirestoreTestActivity.class);
                startActivity(intent);
            }
        });


    }

}
