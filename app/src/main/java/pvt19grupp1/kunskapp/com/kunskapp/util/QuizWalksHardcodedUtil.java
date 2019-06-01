package pvt19grupp1.kunskapp.com.kunskapp.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.models.User;

public class QuizWalksHardcodedUtil {

    public QuizWalksHardcodedUtil() {

    }

    public static QuizWalk createQuizWalkHandels() {

        QuizWalk handels = null;
        List<QuizPlace> quizPlaces = new ArrayList<>();
        List<LatLng> points = new ArrayList<>();

        QuizPlace qp = new QuizPlace("Stockholm International School", 59.339103, 18.066004);
        QuizPlace qp2 = new QuizPlace("Stockholm School of Entrepreneurship", 59.33979600000001, 18.0581215);
        QuizPlace qp3 = new QuizPlace("Stockholm School of Economics", 59.34161820000001, 18.0567906);

        quizPlaces.add(qp);
        quizPlaces.add(qp2);
        quizPlaces.add(qp3);

        handels = new QuizWalk("Handelshögskolan quiz", "Kort beskrivning, men något längre text iofs.. ", quizPlaces);

        points.add(new LatLng(59.33908, 18.06586));
        points.add(new LatLng(59.33915, 18.06584));
        points.add(new LatLng(59.33919, 18.0658));
        points.add(new LatLng(59.33926, 18.06573));
        points.add(new LatLng(59.33982, 18.06515));
        points.add(new LatLng(59.33984, 18.06512));
        points.add(new LatLng(59.33986, 18.06509));
        points.add(new LatLng(59.33989, 18.065));
        points.add(new LatLng(59.33989, 18.065));
        points.add(new LatLng(59.33988, 18.06489));
        points.add(new LatLng(59.33987, 18.06481));
        points.add(new LatLng(59.33974, 18.06431));
        points.add(new LatLng(59.33957, 18.06367));
        points.add(new LatLng(59.3394, 18.06303));
        points.add(new LatLng(59.3394, 18.06303));
        points.add(new LatLng(59.33979, 18.06282));
        points.add(new LatLng(59.33989, 18.06275));
        points.add(new LatLng(59.34021, 18.06255));
        points.add(new LatLng(59.34027, 18.0625));
        points.add(new LatLng(59.34024, 18.06237));
        points.add(new LatLng(59.34024, 18.06237));
        points.add(new LatLng(59.33989, 18.06104));
        points.add(new LatLng(59.33986, 18.06094));
        points.add(new LatLng(59.33964, 18.06008));
        points.add(new LatLng(59.33956, 18.05982));
        points.add(new LatLng(59.33952, 18.05967));
        points.add(new LatLng(59.33946, 18.05943));
        points.add(new LatLng(59.3394, 18.05921));
        points.add(new LatLng(59.33938, 18.05911));
        points.add(new LatLng(59.33935, 18.05899));
        points.add(new LatLng(59.33932, 18.05892));
        points.add(new LatLng(59.33929, 18.05883));
        points.add(new LatLng(59.33929, 18.05882));
        points.add(new LatLng(59.33925, 18.0587));
        points.add(new LatLng(59.33925, 18.0587));
        points.add(new LatLng(59.33967, 18.05826));
        points.add(new LatLng(59.3398, 18.05813));
        points.add(new LatLng(59.3398, 18.05813));
        points.add(new LatLng(59.34015, 18.05777));
        points.add(new LatLng(59.3411, 18.05683));
        points.add(new LatLng(59.34114, 18.05686));
        points.add(new LatLng(59.34114, 18.05686));
        points.add(new LatLng(59.34154, 18.05648));

        handels.setLatLngPoints(points);
        handels.setTotaldistance(859.2395004347331);

        return handels;
    }

    public static void printQuizPlaces(User user) {
        if(user.getMyQuizzes() != null && user.getMyQuizzes().size() > 0) {

            for (QuizWalk quizWalk : user.getMyQuizzes()) {

                List<QuizPlace> quizPlaces = quizWalk.getQuizPlaces();

                System.out.println("---------- BEGIN QUIZPLACE PRINT -------------\n");
                System.out.println("---------- TIPSPROMENADENS NAMN -------------\n");
                System.out.println(quizWalk.getName() + "\n");
                System.out.println("---------- ANTAL POLYLINES OBJEKT -------------\n");
                System.out.println(quizWalk.getLatLngPoints().size());
                System.out.println("---------- TIPSPROMENADENS LÄNGD -------------\n");
                System.out.println(quizWalk.getTotaldistance());

                System.out.println("---------- INDIVIDUELL PLATSINFO -------------\n");

                for (QuizPlace qp : quizPlaces) {
                    System.out.println("QuizPlace qp = new QuizPlace(" + qp.getName() + "," + qp.getLatitude() + "," + qp.getLongitude() + ")");
                }

                System.out.println("MARKER POINTS: \n-----------------------------------------------");

                for (int i = 0; i < quizWalk.getLatLngPoints().size(); i++) {
                    System.out.println("points.add(new LatLng(" + String.valueOf(quizWalk.getLatLngPoints().get(i).latitude)
                            + "," + String.valueOf(quizWalk.getLatLngPoints().get(i).longitude) + "));");
                }
                System.out.println("MARKER POINTS END: \n-----------------------------------------------");
            }
        }
    }


}
