package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class QuizPlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, category, score, textViewAddedQuestions;
    OnPlaceListListener onPlaceListListener;
    FloatingActionButton addButton;

    public QuizPlacesViewHolder(@NonNull View itemView, final OnPlaceListListener onPlaceListListener) {
        super(itemView);

        this.onPlaceListListener = onPlaceListListener;
        name = itemView.findViewById(R.id.place_name);
        itemView.setOnClickListener(this);
        textViewAddedQuestions = itemView.findViewById(R.id.textView_chosen_questions);

    }

    @Override
    public void onClick(View v) {
        onPlaceListListener.onPlaceClick(getAdapterPosition());
    }

}

