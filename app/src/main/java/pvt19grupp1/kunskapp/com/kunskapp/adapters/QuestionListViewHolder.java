package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class QuestionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name;
    OnQuestionListListener onQuestionListListener;

    public QuestionListViewHolder(@NonNull View itemView, final OnQuestionListListener mOnQuestionListListener) {
        super(itemView);

        this.onQuestionListListener = mOnQuestionListListener;
        name = itemView.findViewById(R.id.place_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onQuestionListListener.onQuestionClick(getAdapterPosition());
    }




}

