package pvt19grupp1.kunskapp.com.kunskapp;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuizWalkRepositoryTemp;

public class MyQuizWalksActivity extends BaseActivity {

    private TextView txtViewQuizWalkName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quizwalks);

        txtViewQuizWalkName = findViewById(R.id.text_view_quiz_walk_name);

        QuizWalk quizWalk = null;
        String quizWalkName = " ";
        if(QuizWalkRepositoryTemp.globalTempAllQuizWalks.get(0) != null) {
            quizWalk = QuizWalkRepositoryTemp.globalTempAllQuizWalks.get(0);
            List<QuizPlace> quizPlaces = quizWalk.getQuizPlaces();

            System.out.println("---------- TIPSPROMENADENS NAMN -------------\n");
            System.out.println(quizWalk.getName() + "\n");
            System.out.println("---------- ANTAL POLYLINES OBJEKT -------------\n");
            System.out.println(quizWalk.getLatLngPoints().size());
            System.out.println("---------- TIPSPROMENADENS LÄNGD -------------\n");
            System.out.println(quizWalk.getTotaldistance());

            System.out.println("---------- INDIVIDUELL PLATSINFO -------------\n");

                for (QuizPlace qp : quizPlaces) {
                    System.out.println(
                            "Platsens namn: " + qp.getName() + "\n" +
                                    "Antal frågor " + qp.getQuestions().size() + "\n" +
                                    "Longitude: " + qp.getLongitude() + "\n" +
                                    "Latitude: " + qp.getLatitude() + "\n"

                    );


                }
            }
        } // for(int i)


}


