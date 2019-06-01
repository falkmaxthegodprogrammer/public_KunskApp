package pvt19grupp1.kunskapp.com.kunskapp;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;


public class WelcomeScreenActivity extends BaseActivity {

    private Button btnStudent;
    private Button btnTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_screen);

        btnStudent = (Button) findViewById(R.id.btn_student);
        btnTeacher = (Button) findViewById(R.id.btn_teacher);

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreenActivity.this, QuizWalkActivity.class);
                startActivity(intent);
            }
        });

        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
