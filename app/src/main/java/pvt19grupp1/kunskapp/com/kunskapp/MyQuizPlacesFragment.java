package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuizPlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
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
    private TextView textViewInstructions;

    private String instructionString = "Klicka på en plats för att välja/lägga till/redigera frågor." +
                                       "\n\nHar du inte valt fråga läggs den mest populära frågan till. " +
                                       "\n Spara quiz för att få tillgång till det senare.";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myquizplaces_fragment, container, false);
        recyclerView = view.findViewById(R.id.quizplaces_list);

        btnClearMyQuizPlaces = view.findViewById(R.id.btn_clear_myquizplaces);
        btnClearMyQuizPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmClearPlacesDialog();
            }
        });

        textViewInstructions = view.findViewById(R.id.text_view_instruction);

        btnSaveMyQuizPlaces = view.findViewById(R.id.btn_save_myquizplaces);
        btnSaveMyQuizPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveQuizPlacesDialog();
            }
        });




        initRecyclerView();
        //subscribeObservers();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQuizPlacesViewModel = ViewModelProviders.of(getActivity()).get(QuizPlaceViewModel.class);
        subscribeObservers();
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
                quizPlaceRecyclerAdapter.setmQuizPlaces(places);
                textViewInstructions.setText("Du har för tillfället " + noPlacesSelected() + " plats(er) tillagda. \n" + instructionString);
            }
        });
    }
    private int noPlacesSelected() {
        int noPlaces;
        if(mQuizPlacesViewModel.getQuizPlaces().getValue() != null) {
            noPlaces = mQuizPlacesViewModel.getQuizPlaces().getValue().size();
        } else {
            noPlaces = 0;
        }
        return noPlaces;
    }

    public void showConfirmClearPlacesDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Rensa platser?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mQuizPlacesViewModel.clearQuizPlaces();
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
                        mQuizPlacesViewModel.clearQuizPlaces();
                        dialog.cancel();
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

    }

    @Override
    public void onCategoryClick(String category) {

    }

    @Override
    public void onButtonClick(int position) {

    }


}
