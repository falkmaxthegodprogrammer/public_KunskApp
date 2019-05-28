package pvt19grupp1.kunskapp.com.kunskapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.Question;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;

public class QuizPlaceRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int PLACE_TYPE = 1;
        private static final int LOADING_TYPE = 2;

        private List<QuizPlace> mQuizPlaces;
        private OnPlaceListListener onPlaceListListener;

        public QuizPlaceRecyclerAdapter(OnPlaceListListener mOnPlaceListListener) {
            this.onPlaceListListener = mOnPlaceListListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = null;

            switch(i) {
                case PLACE_TYPE:{
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_quiz_place_list_item, viewGroup, false);
                    return new QuizPlacesViewHolder(view, onPlaceListListener);
                }
                case LOADING_TYPE:{
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_placelist_item, viewGroup, false);
                    return new QuizPlacesViewHolder(view, onPlaceListListener);
                }
                default: {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_quiz_place_list_item, viewGroup, false);
                    return new QuizPlacesViewHolder(view, onPlaceListListener);
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

            int itemViewType = getItemViewType(i);
            if(itemViewType == PLACE_TYPE) {

                String addedQuestionsText = "";
                String tempString = "";
                if(mQuizPlaces.get(i).getQuestions().size() == 0) {
                    addedQuestionsText = "Inga valda frågor..";
                } else {
                    for(Question q : mQuizPlaces.get(i).getQuestions()) {
                        addedQuestionsText = addedQuestionsText + q.getQuestionText() + " \n";
                    }
                }

                ((QuizPlacesViewHolder) viewHolder).name.setText(mQuizPlaces.get(i).getName());
                ((QuizPlacesViewHolder) viewHolder).textViewAddedQuestions.setText(addedQuestionsText);

            }
        }



        @Override
        public int getItemViewType(int position) {
            if(mQuizPlaces.get(position).getName().equals("LOADING...")) {
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
            if(mQuizPlaces != null) {
                if (mQuizPlaces.size() > 0) {
                    if (mQuizPlaces.get(mQuizPlaces.size() - 1).getName().equals("LOADING...")) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public int getItemCount() {
            if(mQuizPlaces != null) {
                return mQuizPlaces.size();
            }
            return 0;
        }

        public void setmQuizPlaces(List<QuizPlace> quizPlaces) {
            mQuizPlaces = quizPlaces;
            notifyDataSetChanged();
        }

        public QuizPlace getSelectedPlace(int position) {
            if(mQuizPlaces != null) {
                if(mQuizPlaces.size() > 0) {
                    return mQuizPlaces.get(position);
                }
            }
            return null;
        }

    }


