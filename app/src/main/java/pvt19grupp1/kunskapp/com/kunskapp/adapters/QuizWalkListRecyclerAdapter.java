package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;

public class QuizWalkListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<QuizWalk> mQuizWalksList;
    private OnQuizWalkListListener onQuizWalkListListener;

    public QuizWalkListRecyclerAdapter(List<QuizWalk> quizWalks, OnQuizWalkListListener mOnQuizWalkListListener) {

        this.onQuizWalkListListener = mOnQuizWalkListListener;
        this.mQuizWalksList = quizWalks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_quizwalk_item, viewGroup, false);
        return new QuizWalkListViewHolder(view, onQuizWalkListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
      //  String questionText = mQuizWalkList.get(i).getQuestionText();
      //  ((QuestionListViewHolder) viewHolder).name.setText(questionText);
        ((QuizWalkListViewHolder) viewHolder).iView.setBackgroundColor(0x00FF00);
        ((QuizWalkListViewHolder) viewHolder).textView.setText(mQuizWalksList.get(i).getName());

    }

    @Override
    public int getItemCount() {
        if(mQuizWalksList != null) {
            return mQuizWalksList.size();
        }
        return 0;
    }

    public void setmQuizWalksList(List<QuizWalk> quizWalks) {
        mQuizWalksList = quizWalks;
        notifyDataSetChanged();
    }

    public void addQuestion(QuizWalk quizWalk) {
        mQuizWalksList.add(quizWalk);
        notifyDataSetChanged();
    }

    public QuizWalk getSelectedQuestion(int position) {
        if(mQuizWalksList != null) {
            if(mQuizWalksList.size() > 0) {
                return mQuizWalksList.get(position);
            }
        }
        return null;
    }


}
