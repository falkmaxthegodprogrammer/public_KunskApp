package pvt19grupp1.kunskapp.com.kunskapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

        private LatLng position; // required field
        private String title; // required field
        private String snippet; // required field
        private int iconPicture;


    private QuizPlace mQuizPlace;
   //     private User user;

        public ClusterMarker(LatLng position, String title, String snippet, int iconPicture, QuizPlace qp) {
            this.position = position;
            this.title = title;
            this.snippet = snippet;
            this.iconPicture = iconPicture;
            this.mQuizPlace = qp;
        }

        public int getIconPicture() {
            return iconPicture;
        }

        public void setIconPicture(int iconPicture) {
            this.iconPicture = iconPicture;
        }

    public QuizPlace getmQuizPlace() {
        return mQuizPlace;
    }

    public void setmQuizPlace(QuizPlace mQuizPlace) {
        this.mQuizPlace = mQuizPlace;
    }

    /*     public User getUser() {
   ///         return user;
   //     }

        public void setUser(User user) {
   //         this.user = user;
        }
*/
        public void setPosition(LatLng position) {
            this.position = position;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public LatLng getPosition() {
            return position;
        }

        public String getTitle() {
            return title;
        }

        public String getSnippet() {
            return snippet;
        }
    }
