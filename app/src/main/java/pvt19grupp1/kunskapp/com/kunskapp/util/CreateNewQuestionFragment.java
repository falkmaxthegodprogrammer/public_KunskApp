package pvt19grupp1.kunskapp.com.kunskapp.util;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuizPlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.Answer;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;


@SuppressLint("ValidFragment")
public class CreateNewQuestionFragment extends Fragment {

    private QuizPlace qPlace;
    private QuizPlaceRecyclerAdapter mQuizPlaceRecyclerAdapter;
    private DialogFragment mDialogFragmentParent;

    @SuppressLint("ValidFragment")
    public CreateNewQuestionFragment(QuizPlace quizPlace, QuizPlaceRecyclerAdapter quizPlaceRecyclerAdapter, DialogFragment dialogFragmentParent) {
        this.qPlace = quizPlace;
        this.mQuizPlaceRecyclerAdapter = quizPlaceRecyclerAdapter;
        this.mDialogFragmentParent = dialogFragmentParent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_question_dialog,container,false);

            RadioGroup rg = (RadioGroup) v.findViewById(R.id.radiobuttons);
            Button btnCancel = (Button) v.findViewById(R.id.dialogBtn_cancel);
            Button btnAddQuestion = (Button) v.findViewById(R.id.dialogBtn_addQuestion);

            final EditText questionText = (EditText) v.findViewById(R.id.edit_text_question);

            final RadioButton rb0 = (RadioButton) v.findViewById(R.id.radio0);
            final RadioButton rb1 = (RadioButton) v.findViewById(R.id.radio0);
            final RadioButton rb2 = (RadioButton) v.findViewById(R.id.radio0);
            final RadioButton rb3 = (RadioButton) v.findViewById(R.id.radio0);

            final EditText questionText1 = (EditText) v.findViewById(R.id.edit_text_question1);
            final EditText questionText2 = (EditText) v.findViewById(R.id.edit_text_question2);
            final EditText questionText3 = (EditText) v.findViewById(R.id.edit_text_question3);
            final EditText questionText4 = (EditText) v.findViewById(R.id.edit_text_question4);


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogFragmentParent.dismiss();
                }
            });

            btnAddQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Question question = new Question(questionText.getText().toString(), qPlace.getGooglePlace().getId());

                    question.addAnswer(new Answer(questionText1.getText().toString(), rb0.isChecked()));
                    question.addAnswer(new Answer(questionText2.getText().toString(), rb1.isChecked()));
                    question.addAnswer(new Answer(questionText3.getText().toString(), rb2.isChecked()));
                    question.addAnswer(new Answer(questionText4.getText().toString(), rb3.isChecked()));

                    qPlace.addQuestion(question);
                    mQuizPlaceRecyclerAdapter.notifyDataSetChanged();

                    // Reset Forms
                    questionText.setText("");
                    questionText1.setText("");
                    questionText2.setText("");
                    questionText3.setText("");
                    questionText4.setText("");

                    questionText.setHint("Ny fråga");
                    questionText1.setHint("Svarsalternativ 1");
                    questionText2.setHint("Svarsalternativ 2");
                    questionText3.setHint("Svarsalternativ 3");
                    questionText4.setHint("Svarsalternativ 4");

                    rb0.setSelected(true);
                    rb1.setSelected(false);
                    rb2.setSelected(false);
                    rb3.setSelected(false);

                    Toast.makeText(getActivity(), "Din fråga har blivit tillagd till " + qPlace.getName() + " .", Toast.LENGTH_SHORT).show();
                }
            });

        return v;
    }



}