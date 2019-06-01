package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class QuizWalkListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textView;
    OnQuizWalkListListener onQuizWalkListListener;
    FloatingActionButton addButton;
    View iView;
    CardView cView;

    public QuizWalkListViewHolder(@NonNull View itemView, final OnQuizWalkListListener onQuizWalkListListener) {
        super(itemView);

        this.onQuizWalkListListener = onQuizWalkListListener;
        iView = itemView.findViewById(R.id.colorView);
        textView = itemView.findViewById(R.id.txtview_quizwalk_name_viewholder);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onQuizWalkListListener.onQuizWalkClick(getAdapterPosition());
    }



}