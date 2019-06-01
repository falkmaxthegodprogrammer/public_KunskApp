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

    public static QuizWalk createQuizWalkFredhallsRundan() {
        QuizWalk fredhall = null;
        List<QuizPlace> quizPlaces = new ArrayList<>();
        List<LatLng> points = new ArrayList<>();

        QuizPlace qp1 = new QuizPlace("Kristineberg T-bana",59.33277370888012,18.002673424780365);
        QuizPlace qp2 = new QuizPlace("ICA Maxi Supermarket",59.3373465,18.009773);
        QuizPlace qp3 = new QuizPlace("Sky Hotel Apartments",59.33401691371835,18.015883304178715);
        QuizPlace qp4 = new QuizPlace("Thorildsplan",59.332433572423085,18.01586989313364);
        QuizPlace qp5 = new QuizPlace("Plaskis",59.33190258336407,18.003916628658768);

        quizPlaces.add(qp1);
        quizPlaces.add(qp2);
        quizPlaces.add(qp3);
        quizPlaces.add(qp4);
        quizPlaces.add(qp5);

        fredhall = new QuizWalk("Fredhällsrundan", "En härlig tur runt Fredhäll! ", quizPlaces);

         points.add(new LatLng(59.33277,18.00272));
         points.add(new LatLng(59.33289,18.00281));
         points.add(new LatLng(59.33296,18.00286));
         points.add(new LatLng(59.33303,18.0029));
         points.add(new LatLng(59.33309,18.00295));
         points.add(new LatLng(59.33311,18.00297));
         points.add(new LatLng(59.33323,18.00307));
         points.add(new LatLng(59.33363,18.00342));
         points.add(new LatLng(59.33375,18.00352));
         points.add(new LatLng(59.33382,18.00359));
         points.add(new LatLng(59.33389,18.00364));
         points.add(new LatLng(59.33401,18.00371));
         points.add(new LatLng(59.33406,18.00373));
         points.add(new LatLng(59.33411,18.00374));
         points.add(new LatLng(59.33413,18.00374));
         points.add(new LatLng(59.3342,18.00374));
         points.add(new LatLng(59.33426,18.00373));
         points.add(new LatLng(59.33471,18.00366));
         points.add(new LatLng(59.33561,18.00352));
         points.add(new LatLng(59.33564,18.00352));
         points.add(new LatLng(59.33574,18.00351));
         points.add(new LatLng(59.3358,18.00351));
         points.add(new LatLng(59.33591,18.00353));
         points.add(new LatLng(59.33596,18.00356));
         points.add(new LatLng(59.33599,18.00357));
         points.add(new LatLng(59.33604,18.0036));
         points.add(new LatLng(59.33606,18.00361));
         points.add(new LatLng(59.33616,18.00365));
         points.add(new LatLng(59.33629,18.00377));
         points.add(new LatLng(59.33637,18.00392));
         points.add(new LatLng(59.33643,18.00408));
         points.add(new LatLng(59.33649,18.00429));
         points.add(new LatLng(59.33654,18.00447));
         points.add(new LatLng(59.33658,18.00462));
         points.add(new LatLng(59.3366,18.00475));
         points.add(new LatLng(59.33661,18.00489));
         points.add(new LatLng(59.33662,18.0051));
         points.add(new LatLng(59.33662,18.00521));
         points.add(new LatLng(59.33663,18.00531));
         points.add(new LatLng(59.33664,18.00539));
         points.add(new LatLng(59.33666,18.0055));
         points.add(new LatLng(59.33668,18.00559));
         points.add(new LatLng(59.3367,18.00564));
         points.add(new LatLng(59.33674,18.00575));
         points.add(new LatLng(59.33684,18.00596));
         points.add(new LatLng(59.33718,18.0066));
         points.add(new LatLng(59.33734,18.00692));
         points.add(new LatLng(59.33754,18.00732));
         points.add(new LatLng(59.33777,18.00773));
         points.add(new LatLng(59.33782,18.00783));
         points.add(new LatLng(59.33786,18.00789));
         points.add(new LatLng(59.33791,18.00799));
         points.add(new LatLng(59.33791,18.00799));
         points.add(new LatLng(59.33776,18.00828));
         points.add(new LatLng(59.33768,18.00844));
         points.add(new LatLng(59.33766,18.00849));
         points.add(new LatLng(59.3376,18.00859));
         points.add(new LatLng(59.33746,18.00886));
         points.add(new LatLng(59.33737,18.00904));
         points.add(new LatLng(59.33733,18.00911));
         points.add(new LatLng(59.33717,18.00944));
         points.add(new LatLng(59.33717,18.00944));
         points.add(new LatLng(59.33709,18.00961));
         points.add(new LatLng(59.33692,18.00995));
         points.add(new LatLng(59.33688,18.01003));
         points.add(new LatLng(59.33683,18.01013));
         points.add(new LatLng(59.3366,18.0106));
         points.add(new LatLng(59.33657,18.01064));
         points.add(new LatLng(59.33648,18.01083));
         points.add(new LatLng(59.33642,18.01096));
         points.add(new LatLng(59.3364,18.01101));
         points.add(new LatLng(59.33634,18.01114));
         points.add(new LatLng(59.33627,18.01132));
         points.add(new LatLng(59.33585,18.01228));
         points.add(new LatLng(59.33541,18.01307));
         points.add(new LatLng(59.33535,18.01319));
         points.add(new LatLng(59.3353,18.01331));
         points.add(new LatLng(59.33518,18.01359));
         points.add(new LatLng(59.33513,18.01371));
         points.add(new LatLng(59.33491,18.01417));
         points.add(new LatLng(59.33486,18.01427));
         points.add(new LatLng(59.33474,18.01453));
         points.add(new LatLng(59.33455,18.01486));
         points.add(new LatLng(59.3345,18.01496));
         points.add(new LatLng(59.33438,18.0152));
         points.add(new LatLng(59.33421,18.01553));
         points.add(new LatLng(59.3341,18.01575));
         points.add(new LatLng(59.33402,18.0159));
         points.add(new LatLng(59.33402,18.0159));
         points.add(new LatLng(59.33389,18.01615));
         points.add(new LatLng(59.33385,18.01603));
         points.add(new LatLng(59.33385,18.01603));
         points.add(new LatLng(59.33382,18.01598));
         points.add(new LatLng(59.33377,18.01588));
         points.add(new LatLng(59.33371,18.016));
         points.add(new LatLng(59.33371,18.016));
         points.add(new LatLng(59.3335,18.01634));
         points.add(new LatLng(59.33298,18.01722));
         points.add(new LatLng(59.33298,18.01722));
         points.add(new LatLng(59.33291,18.01709));
         points.add(new LatLng(59.33257,18.01645));
         points.add(new LatLng(59.33252,18.01635));
         points.add(new LatLng(59.3325,18.01632));
         points.add(new LatLng(59.33245,18.01622));
         points.add(new LatLng(59.33235,18.01603));
         points.add(new LatLng(59.33233,18.01578));
         points.add(new LatLng(59.33228,18.01589));
         points.add(new LatLng(59.33223,18.016));
         points.add(new LatLng(59.33215,18.01603));
         points.add(new LatLng(59.33212,18.01603));
         points.add(new LatLng(59.33196,18.01604));
         points.add(new LatLng(59.33185,18.01606));
         points.add(new LatLng(59.33181,18.01606));
         points.add(new LatLng(59.3318,18.01606));
         points.add(new LatLng(59.33179,18.01605));
         points.add(new LatLng(59.33179,18.01604));
         points.add(new LatLng(59.33179,18.016));
         points.add(new LatLng(59.33179,18.01592));
         points.add(new LatLng(59.33179,18.01591));
         points.add(new LatLng(59.33178,18.0159));
         points.add(new LatLng(59.33177,18.01589));
         points.add(new LatLng(59.33168,18.0159));
         points.add(new LatLng(59.33163,18.0159));
         points.add(new LatLng(59.33162,18.01591));
         points.add(new LatLng(59.33159,18.01591));
         points.add(new LatLng(59.33148,18.01592));
         points.add(new LatLng(59.33145,18.01592));
         points.add(new LatLng(59.33143,18.01592));
         points.add(new LatLng(59.33133,18.01592));
         points.add(new LatLng(59.3313,18.01592));
         points.add(new LatLng(59.33127,18.01592));
         points.add(new LatLng(59.33125,18.01592));
         points.add(new LatLng(59.33109,18.01595));
         points.add(new LatLng(59.33109,18.01595));
         points.add(new LatLng(59.33109,18.01591));
         points.add(new LatLng(59.3311,18.01585));
         points.add(new LatLng(59.3311,18.01571));
         points.add(new LatLng(59.33112,18.01562));
         points.add(new LatLng(59.33112,18.01543));
         points.add(new LatLng(59.33109,18.01472));
         points.add(new LatLng(59.33108,18.01448));
         points.add(new LatLng(59.33107,18.01426));
         points.add(new LatLng(59.33105,18.01386));
         points.add(new LatLng(59.33104,18.01342));
         points.add(new LatLng(59.33103,18.01293));
         points.add(new LatLng(59.33103,18.01276));
         points.add(new LatLng(59.33098,18.01225));
         points.add(new LatLng(59.33098,18.01217));
         points.add(new LatLng(59.33097,18.01188));
         points.add(new LatLng(59.33098,18.01155));
         points.add(new LatLng(59.33099,18.01145));
         points.add(new LatLng(59.33101,18.01127));
         points.add(new LatLng(59.33101,18.01117));
         points.add(new LatLng(59.33102,18.0111));
         points.add(new LatLng(59.33104,18.01075));
         points.add(new LatLng(59.33104,18.01071));
         points.add(new LatLng(59.33105,18.0104));
         points.add(new LatLng(59.33105,18.01029));
         points.add(new LatLng(59.33106,18.01005));
         points.add(new LatLng(59.33106,18.01));
         points.add(new LatLng(59.33106,18.00997));
         points.add(new LatLng(59.33106,18.00976));
         points.add(new LatLng(59.33107,18.00943));
         points.add(new LatLng(59.33108,18.00932));
         points.add(new LatLng(59.33108,18.00925));
         points.add(new LatLng(59.33109,18.00917));
         points.add(new LatLng(59.33115,18.00895));
         points.add(new LatLng(59.3312,18.00881));
         points.add(new LatLng(59.3312,18.00881));
         points.add(new LatLng(59.33116,18.00872));
         points.add(new LatLng(59.33104,18.00837));
         points.add(new LatLng(59.33099,18.00824));
         points.add(new LatLng(59.33096,18.00817));
         points.add(new LatLng(59.33096,18.00817));
         points.add(new LatLng(59.33099,18.00795));
         points.add(new LatLng(59.33107,18.00739));
         points.add(new LatLng(59.33111,18.00716));
         points.add(new LatLng(59.33113,18.00702));
         points.add(new LatLng(59.33114,18.00692));
         points.add(new LatLng(59.33114,18.00691));
         points.add(new LatLng(59.33115,18.00678));
         points.add(new LatLng(59.33115,18.0066));
         points.add(new LatLng(59.33115,18.00641));
         points.add(new LatLng(59.33115,18.00617));
         points.add(new LatLng(59.33115,18.00601));
         points.add(new LatLng(59.33116,18.00591));
         points.add(new LatLng(59.33116,18.00582));
         points.add(new LatLng(59.33117,18.00572));
         points.add(new LatLng(59.33119,18.00553));
         points.add(new LatLng(59.3312,18.00537));
         points.add(new LatLng(59.33122,18.0052));
         points.add(new LatLng(59.33124,18.00503));
         points.add(new LatLng(59.3313,18.00494));
         points.add(new LatLng(59.33145,18.00471));
         points.add(new LatLng(59.33151,18.00461));
         points.add(new LatLng(59.33162,18.00442));
         points.add(new LatLng(59.33172,18.00423));
         points.add(new LatLng(59.33183,18.00401));
         points.add(new LatLng(59.33188,18.00389));


        fredhall.setLatLngPoints(points);
        fredhall.setTotaldistance(3127);

        return fredhall;
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

    public static void printQuizWalk(QuizWalk quizWalk) {


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
