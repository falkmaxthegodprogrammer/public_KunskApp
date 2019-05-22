package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.CreateQuizWalkActivity;
import pvt19grupp1.kunskapp.com.kunskapp.MainActivity;
import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;

public class PlaceRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PLACE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<GooglePlaceModel> mGooglePlaces;
    private OnPlaceListListener onPlaceListListener;

    public PlaceRecyclerAdapter(OnPlaceListListener mOnPlaceListListener) {
        this.onPlaceListListener = mOnPlaceListListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;

        switch(i) {
            case PLACE_TYPE:{
              view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_place_list_item, viewGroup, false);
                return new PlacesViewholder(view, onPlaceListListener);
            }
            case LOADING_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_placelist_item, viewGroup, false);
                return new PlacesViewholder(view, onPlaceListListener);
            }
            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_place_list_item, viewGroup, false);
                return new PlacesViewholder(view, onPlaceListListener);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

     /*   RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(viewHolder.itemView.getContext())
            .setDefaultRequestOptions(requestOptions)
                .load(mGooglePlaces.get(i))
                .into(((PlacesViewholder)viewHolder).image); */
        int itemViewType = getItemViewType(i);
        if(itemViewType == PLACE_TYPE) {
            ((PlacesViewholder) viewHolder).name.setText(mGooglePlaces.get(i).getName());
            ((PlacesViewholder) viewHolder).category.setText(mGooglePlaces.get(i).getTypes()[0]);
            ((PlacesViewholder) viewHolder).score.setText(String.valueOf(mGooglePlaces.get(i).getUser_ratings_total()));

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mGooglePlaces.get(position).getName().equals("LOADING...")) {
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
        if(mGooglePlaces != null) {
            if (mGooglePlaces.size() > 0) {
                if (mGooglePlaces.get(mGooglePlaces.size() - 1).getName().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if(mGooglePlaces != null) {
            return mGooglePlaces.size();
        }
        return 0;
    }

    public void setmGooglePlaces(List<GooglePlaceModel> googlePlaces) {
        mGooglePlaces = googlePlaces;
        notifyDataSetChanged();
    }

    public GooglePlaceModel getSelectedPlace(int position) {
        if(mGooglePlaces != null) {
            if(mGooglePlaces.size() > 0) {
                return mGooglePlaces.get(position);
            }
        }
        return null;
    }

}
