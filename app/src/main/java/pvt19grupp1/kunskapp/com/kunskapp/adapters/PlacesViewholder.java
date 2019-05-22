package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class PlacesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, category, score;
    OnPlaceListListener onPlaceListListener;
    FloatingActionButton addButton;

    public PlacesViewholder(@NonNull View itemView, final OnPlaceListListener onPlaceListListener) {
        super(itemView);

        this.onPlaceListListener = onPlaceListListener;
        name = itemView.findViewById(R.id.place_name);
        category = itemView.findViewById(R.id.place_category);
        score = itemView.findViewById(R.id.place_social_score);
        addButton = itemView.findViewById(R.id.btn_add);

        addButton.setOnClickListener(this);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onPlaceListListener.onPlaceClick(getAdapterPosition());
    }

   // @Override
    //public void onButtonClick(View v) { onPlaceListListener.onButtonClick(getAdapterPosition()); }
}
