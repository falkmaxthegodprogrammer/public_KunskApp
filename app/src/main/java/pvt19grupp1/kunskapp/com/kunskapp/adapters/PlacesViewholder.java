package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;

public class PlacesViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, category, score;
    OnPlaceListListener onPlaceListListener;
    FloatingActionButton addButton;
    View iView;
    CardView cView;

    public PlacesViewholder(@NonNull View itemView, final OnPlaceListListener onPlaceListListener) {
        super(itemView);

        this.onPlaceListListener = onPlaceListListener;
        name = itemView.findViewById(R.id.place_name);
        category = itemView.findViewById(R.id.place_category);
        score = itemView.findViewById(R.id.place_social_score);
        addButton = itemView.findViewById(R.id.btn_add);
        itemView.setBackgroundColor(0x000000);
        cView = itemView.findViewById(R.id.card_view);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceListListener.onButtonClick(getAdapterPosition());
            }
        });

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onPlaceListListener.onPlaceClick(getAdapterPosition());
    }



}
