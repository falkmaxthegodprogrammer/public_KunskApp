package pvt19grupp1.kunskapp.com.kunskapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnPlaceListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnQuestionListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.PlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuestionsRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.util.VerticalSpacingDecorator;

public class ChosenQuestionsFragment extends Fragment implements OnQuestionListListener {

    private RecyclerView recyclerView;
    private View view;
    private QuestionsRecyclerAdapter questionsRecyclerAdapter;
    private QuizWalk activeQuizWalk = null;

    public ChosenQuestionsFragment() {

    }

    public void setAciveQuizWalk(QuizWalk currentQuizwalk) {
        activeQuizWalk = currentQuizwalk;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chosenquestions, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_chosenquestions_quizplaces);
        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initRecyclerView() {
        VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(8);
        recyclerView.addItemDecoration(itemDecorator);
        questionsRecyclerAdapter = new QuestionsRecyclerAdapter(this);
        recyclerView.setAdapter(questionsRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((getActivity()))));
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


    public QuestionsRecyclerAdapter getAdapter() {
        return questionsRecyclerAdapter;
    }
}
