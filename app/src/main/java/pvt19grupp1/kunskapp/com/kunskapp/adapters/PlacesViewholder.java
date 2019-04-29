package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class PlacesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, category, score;
    OnPlaceListListener onPlaceListListener;

    public PlacesViewholder(@NonNull View itemView, OnPlaceListListener onPlaceListListener) {
        super(itemView);

        this.onPlaceListListener = onPlaceListListener;
        name = itemView.findViewById(R.id.place_name);
        category = itemView.findViewById(R.id.place_category);
        score = itemView.findViewById(R.id.place_social_score);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onPlaceListListener.onPlaceClick(getAdapterPosition());
    }
}
