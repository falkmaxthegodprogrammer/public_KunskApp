package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class QuestionsViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnQuestionListListener onQuestionListListener;
    LinearLayout questionLayout;
    TextView placeName, questionText, answer1, answer2, answer3, answer4;

    public QuestionsViewholder(@NonNull View itemView, final OnQuestionListListener mOnQuestionListListener) {
        super(itemView);
        this.onQuestionListListener = mOnQuestionListListener;
        itemView.setOnClickListener(this);

        questionLayout = itemView.findViewById(R.id.linearlayout_chosenquestions);
        placeName = itemView.findViewById(R.id.place_name_recycler_questions);
    }

    @Override
    public void onClick(View v) {
        onQuestionListListener.onQuestionClick(getAdapterPosition());
    }

}

