package pvt19grupp1.kunskapp.com.kunskapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuestionListRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.Answer;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;

public class ShowAddQuestionDialog {

    public ShowAddQuestionDialog(Context context, final GooglePlaceModel googlePlaceInstance, final QuestionListRecyclerAdapter mQuestionRecyclerAdapter) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.add_question_dialog_tabbed);
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


                    mQuestionRecyclerAdapter.addQuestion(question);
                    dialog.cancel();
                }
            });


        }
    }

