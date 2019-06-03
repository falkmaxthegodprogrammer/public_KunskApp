package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.Answer;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;

public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PLACE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<QuizPlace> mQuizPlaces;
    private OnQuestionListListener onQuestionListListener;

    public QuestionsRecyclerAdapter(OnQuestionListListener mOnQuestionListListener) {
        this.onQuestionListListener = mOnQuestionListListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;

        switch(i) {
            case PLACE_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_question_list_item, viewGroup, false);
                return new QuestionsViewholder(view, onQuestionListListener);
            }
            case LOADING_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_placelist_item, viewGroup, false);
                return new QuestionsViewholder(view, onQuestionListListener);
            }
            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_question_list_item, viewGroup, false);
                return new QuestionsViewholder(view, onQuestionListListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        int itemViewType = getItemViewType(i);
        if(itemViewType == PLACE_TYPE) {


            ((QuestionsViewholder) viewHolder).placeName.setText((i + 1) + ". " + mQuizPlaces.get(i).getName());
            LinearLayout mainLL =  ((QuestionsViewholder) viewHolder).questionLayout;

            for(Question q : mQuizPlaces.get(i).getQuestions()) {

                TextView questionText = new TextView(mainLL.getContext());
                questionText.setText(q.getQuestionText());
                questionText.setTextSize(18);
                questionText.setGravity(Gravity.LEFT);
                questionText.setPadding(3, 3, 3, 3);
                questionText.setTextColor(0xFFE16E79);
                questionText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView paddingText = new TextView(mainLL.getContext());
                paddingText.setTextSize(8);
                paddingText.setVisibility(View.INVISIBLE);

                mainLL.addView(questionText);
                mainLL.addView(paddingText);


                for(Answer a : q.getAnswers()) {
                    TextView answerText = new TextView(mainLL.getContext());

                    answerText.setText("    "  + ". " + a.getAnswerText());
                    answerText.setTextSize(16);
                    answerText.setGravity(Gravity.LEFT);
                    answerText.setPadding(3, 3, 3, 3);
                    answerText.setPadding(15, 0, 0, 0);
                    answerText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    if (a.isCorrect()) {
                        answerText.setTextColor(Color.GREEN);
                    }
                    mainLL.addView(answerText);
                }
                TextView paddingText2 = new TextView(mainLL.getContext());
                mainLL.addView(paddingText2);
            }
        }
    }



    @Override
    public int getItemViewType(int position) {
        if(mQuizPlaces.get(position).getName().equals("LOADING...")) {
            return LOADING_TYPE;
        } else {
            return PLACE_TYPE;
        }
    }

    public void displayLoading() {
        if(!isLoading()) {
            GooglePlaceModel googlePlaceLoading = new GooglePlaceModel();
            googlePlaceLoading.setName("LOADING...");
            List<GooglePlaceModel> loadList = new ArrayList<>();
            loadList.add(googlePlaceLoading);
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if(mQuizPlaces != null) {
            if (mQuizPlaces.size() > 0) {
                if (mQuizPlaces.get(mQuizPlaces.size() - 1).getName().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if(mQuizPlaces != null) {
            return mQuizPlaces.size();
        }
        return 0;
    }

    public void clearQuizPlaces() {
        if(mQuizPlaces != null) {
            mQuizPlaces.clear();
        }
    }

    public void setmQuizPlaces(List<QuizPlace> quizPlaces) {
        mQuizPlaces = quizPlaces;
        notifyDataSetChanged();
    }

    public QuizPlace getSelectedPlace(int position) {
        if(mQuizPlaces != null) {
            if(mQuizPlaces.size() > 0) {
                return mQuizPlaces.get(position);
            }
        }
        return null;
    }

}


