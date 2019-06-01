package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuizPlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.Answer;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.models.User;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuizWalkRepositoryTemp;
import pvt19grupp1.kunskapp.com.kunskapp.util.TabbedDialog;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.PlaceListViewModel;
import pvt19grupp1.kunskapp.com.kunskapp.viewmodels.QuizPlaceViewModel;

public class MyQuizPlacesFragment extends Fragment implements OnPlaceListListener {

    View view;
    private QuizPlaceViewModel mQuizPlacesViewModel;
    private QuizPlaceRecyclerAdapter quizPlaceRecyclerAdapter;
    private RecyclerView recyclerView;
    private ImageButton btnClearMyQuizPlaces;
    private ImageButton btnSaveMyQuizPlaces;
    private Button btnInfoText;

    private String instructionString = "";
    private TextView textViewInstructions;

    private QuizMapFragment mQuizMapFragmentReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuizPlacesViewModel = ViewModelProviders.of(getActivity()).get(QuizPlaceViewModel.class);
        subscribeObservers();

        // setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myquizplaces_fragment, container, false);
        recyclerView = view.findViewById(R.id.quizplaces_list);

        textViewInstructions = view.findViewById(R.id.text_view_instruction);

        btnClearMyQuizPlaces = view.findViewById(R.id.btn_clear_myquizplaces);
        btnClearMyQuizPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmClearPlacesDialog();
            }
        });

        btnSaveMyQuizPlaces = view.findViewById(R.id.btn_save_myquizplaces);
        btnSaveMyQuizPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveQuizPlacesDialog();
            }
        });

        btnInfoText = (Button) view.findViewById(R.id.btn_infotext);
        btnInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instructionString.length() >= 1) {
                    instructionString = "";
                    textViewInstructions.setText("");
                    textViewInstructions.setVisibility(View.INVISIBLE);
                } else {
                    textViewInstructions.setVisibility(View.VISIBLE);
                    setInstructionTextString();

                }
            }
        });

        initRecyclerView();

        mQuizMapFragmentReference = ((CreateQuizWalkActivity)getActivity()).getQuizMapFragment();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initRecyclerView() {
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(5);
        recyclerView.addItemDecoration(itemDecorator);
        quizPlaceRecyclerAdapter = new QuizPlaceRecyclerAdapter(this);
        recyclerView.setAdapter(quizPlaceRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
    }

    private void subscribeObservers() {
            mQuizPlacesViewModel.getQuizPlaces().observe(getActivity(), new Observer<List<QuizPlace>>() {
                @Override
                public void onChanged(@Nullable List<QuizPlace> places) {
                    if(places.size() == 0) return;
                    if(places.size() != 0) {
                        try{
                            quizPlaceRecyclerAdapter.setmQuizPlaces(places);
                            setInstructionTextString();
                        } catch(NullPointerException e) {

                        }

                    }
                }
            });
        }

    private void setInstructionTextString() {
        int noPlaces;
        if(mQuizPlacesViewModel.getQuizPlaces().getValue() != null) {
            noPlaces = mQuizPlacesViewModel.getQuizPlaces().getValue().size();
            instructionString = "Klicka på en plats för att välja/lägga till/redigera frågor." +
            "\n\nHar du inte valt fråga läggs den mest populära frågan till. " +
                    "\n Spara quiz för att få tillgång till det senare.";
            textViewInstructions.setText("Du har för tillfället " + noPlaces + " plats(er) tillagda. \n" + instructionString);

        } else {
            noPlaces = 0;
            instructionString = "Inga platser tillagda!";
            textViewInstructions.setText(instructionString);
        }
        if(mQuizPlacesViewModel.getQuizPlaces().getValue().size() == 0) {
            instructionString = "Inga platser tillagda!";
            textViewInstructions.setText(instructionString);

        }
    }

    public void showConfirmClearPlacesDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Rensa platser?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((CreateQuizWalkActivity)(getActivity())).getmPlacesListViewModel().clearGooglePlaces();
                        mQuizPlacesViewModel.clearQuizPlaces();
                        quizPlaceRecyclerAdapter.setmQuizPlaces(new ArrayList<QuizPlace>());
                        mQuizMapFragmentReference.clearLocalDataStructures();
                        Toast.makeText(getActivity(), "Alla platser rensade.", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Nej",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showSaveQuizPlacesDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Spara quiz");
        builder1.setCancelable(true);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder1.setView(input);

        builder1.setPositiveButton(
                "Spara",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((CreateQuizWalkActivity)(getActivity())).setQuizWalkInstance(
                                new QuizWalk(input.getText().toString(), "Empty description", mQuizPlacesViewModel.getQuizPlaces().getValue())
                        );

                        QuizWalk qw = ((CreateQuizWalkActivity)(getActivity())).getQuizWalkInstance();

                        qw.setLatLngPoints(mQuizMapFragmentReference.getTotalLatLngPoints());
                        qw.setTotaldistance(mQuizMapFragmentReference.getTotalDistance());

                        dialog.cancel();

                        User user = ((CreateQuizWalkActivity)(getActivity())).getMasterUser();
                        user.addQuizWalk(qw);

                        ((CreateQuizWalkActivity)(getActivity())).getmPlacesListViewModel().clearGooglePlaces();
                        ((CreateQuizWalkActivity)(getActivity())).navigateToTab(0);
                        ((CreateQuizWalkActivity)(getActivity())).getQuizMapFragment().zoomToRouteBounds();
                        ((CreateQuizWalkActivity)(getActivity())).getQuizMapFragment().takeScreenshot();

                        Toast.makeText(getActivity(), "Tipspromenaden " + qw.getName() + " skapad!", Toast.LENGTH_LONG).show();

                    }
                });

        builder1.setNegativeButton(
                "Avbryt",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onPlaceClick(int position) {
       // showAddQuestionDialog(quizPlaceRecyclerAdapter.getSelectedPlace(position));
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            TabbedDialog dialogFragment = new TabbedDialog(quizPlaceRecyclerAdapter.getSelectedPlace(position), quizPlaceRecyclerAdapter);
            dialogFragment.show(ft,"dialog");
    }

    @Override
    public void onCategoryClick(String category) {

    }

    @Override
    public void onButtonClick(int position) {

    }

    /*  private void showAddQuestionDialog(final QuizPlace qPlace) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

                Question question = new Question(questionText.getText().toString(), qPlace.getGooglePlace().getId());

                question.addAnswer(new Answer(questionText1.getText().toString(), rb0.isChecked()));
                question.addAnswer(new Answer(questionText2.getText().toString(), rb1.isChecked()));
                question.addAnswer(new Answer(questionText3.getText().toString(), rb2.isChecked()));
                question.addAnswer(new Answer(questionText4.getText().toString(), rb3.isChecked()));

                qPlace.addQuestion(question);
                quizPlaceRecyclerAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
    } */



}
