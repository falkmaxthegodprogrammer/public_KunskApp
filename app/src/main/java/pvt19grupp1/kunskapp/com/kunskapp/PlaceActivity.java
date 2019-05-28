package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnQuestionListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuestionListRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.Answer;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuestionRepository;
import pvt19grupp1.kunskapp.com.kunskapp.util.ShowAddQuestionDialog;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;

public class PlaceActivity extends BaseActivity implements OnQuestionListListener {

    private TextView placeName;
    private Button firebaseButton;
    private RecyclerView recyclerView;
    private QuestionListRecyclerAdapter mQuestionRecyclerAdapter;

    private GooglePlaceModel googlePlaceInstance;

    private Button addQuestionBtn;

    private static final String TAG = "PlaceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        placeName = findViewById(R.id.place_name);
        getIncomingIntent();


        addQuestionBtn = (Button) findViewById(R.id.add_question);

        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddQuestionDialog();
            }
        });

        recyclerView = findViewById(R.id.my_questions_list);

        initRecyclerView();

        mQuestionRecyclerAdapter.addQuestion(new Question("Hur många år tog det för blabla..", googlePlaceInstance.getId()));


    }


    private void getIncomingIntent() {
        if(getIntent().hasExtra("place")) {
            GooglePlaceModel googlePlace = getIntent().getParcelableExtra("place");
            placeName.setText(googlePlace.getName());
            googlePlaceInstance = getIntent().getParcelableExtra("place");

        }
    }

    private void initRecyclerView() {
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(8);
        recyclerView.addItemDecoration(itemDecorator);
        mQuestionRecyclerAdapter = new QuestionListRecyclerAdapter(this, googlePlaceInstance);
        recyclerView.setAdapter(mQuestionRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }

    private void showAddQuestionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.add_question_dialog);
        builder.create();
        final AlertDialog dialog = builder.show();

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radiobuttons);
        Button btnCancel = (Button) dialog.findViewById(R.id.dialogBtn_cancel);
        Button btnAddQuestion = (Button) dialog.findViewById(R.id.dialogBtn_addQuestion);

        final EditText questionText = (EditText) dialog.findViewById(R.id.edit_text_question);

        final RadioButton rb0 = (RadioButton) dialog.findViewById(R.id.radio0);
        final RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.radio0);
        final RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.radio0);
        final RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.radio0);

        final EditText questionText1 = (EditText) dialog.findViewById(R.id.edit_text_question1);
        final EditText questionText2 = (EditText) dialog.findViewById(R.id.edit_text_question2);
        final EditText questionText3 = (EditText) dialog.findViewById(R.id.edit_text_question3);
        final EditText questionText4 = (EditText) dialog.findViewById(R.id.edit_text_question4);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Question question = new Question(questionText.getText().toString(), googlePlaceInstance.getId());

                question.addAnswer(new Answer(questionText1.getText().toString(), rb0.isChecked()));
                question.addAnswer(new Answer(questionText2.getText().toString(), rb1.isChecked()));
                question.addAnswer(new Answer(questionText3.getText().toString(), rb2.isChecked()));
                question.addAnswer(new Answer(questionText4.getText().toString(), rb3.isChecked()));


                Toast.makeText(PlaceActivity.this, "Question added! ", Toast.LENGTH_SHORT).show();

                mQuestionRecyclerAdapter.addQuestion(question);
                dialog.cancel();
            }
        });
    }

    @Override
    public void onQuestionClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }

    @Override
    public void onButtonClick(int position) {

    }
}
