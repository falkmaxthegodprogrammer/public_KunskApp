package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuestionRepository;

public class QuestionListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private GooglePlaceModel mGooglePlace;
    private OnQuestionListListener onQuestionListListener;
    private List<Question> mQuestionList;

    public QuestionListRecyclerAdapter(OnQuestionListListener mOnQuestionListListener, GooglePlaceModel googlePlace) {

        this.onQuestionListListener = mOnQuestionListListener;
        this.mGooglePlace = googlePlace;
        this.mQuestionList = googlePlace.getQuestions();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_question_list_item, viewGroup, false);
        return new QuestionListViewHolder(view, onQuestionListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        String questionText = mQuestionList.get(i).getQuestionText();
            ((QuestionListViewHolder) viewHolder).name.setText(questionText);
    }

    @Override
    public int getItemCount() {
        if(mQuestionList != null) {
            return mQuestionList.size();
        }
        return 0;
    }

    public void setmQuestionList(List<Question> questions) {
        mQuestionList = questions;
        notifyDataSetChanged();
    }

    public void addQuestion(Question question) {
        mQuestionList.add(question);
        notifyDataSetChanged();
    }

    public Question getSelectedQuestion(int position) {
        if(mQuestionList != null) {
            if(mQuestionList.size() > 0) {
                return mQuestionList.get(position);
            }
        }
        return null;
    }

}


