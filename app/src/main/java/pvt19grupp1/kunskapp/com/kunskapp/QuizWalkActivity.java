package pvt19grupp1.kunskapp.com.kunskapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.util.QuizWalksHardcodedUtil;


public class QuizWalkActivity extends FragmentActivity {

    private TextView textViewQuizWalkName;
    private TextView textViewQuizWalkInfoBar;
    private MapActiveQuizWalkFragment mQuizWalkMapFragment;



    private QuizWalk qwTestObject = QuizWalksHardcodedUtil.createQuizWalkFredhallsRundan();
    private Button btnStartQuizWalk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizwalk);

        textViewQuizWalkName = findViewById(R.id.text_view_active_quizwalk_name);
        textViewQuizWalkInfoBar = findViewById(R.id.text_view_active_quizwalk_infobar);
        mQuizWalkMapFragment = new MapActiveQuizWalkFragment();
        mQuizWalkMapFragment.setQuizWalkTest(qwTestObject);

        textViewQuizWalkName.setText(qwTestObject.getName());
        textViewQuizWalkInfoBar.setText("Platser: " + qwTestObject.getQuizPlaces().size() + " Sträcka: " + ((int) qwTestObject.getTotaldistance() / 1000) + "km. Ca-Tid: "  + (int) qwTestObject.getTotaldistance() / 85 + " minuter. Antal frågor: 11" );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.map_active_quizwalk_fragment, mQuizWalkMapFragment);
        fragmentTransaction.commit();

        int colorFrom = ContextCompat.getColor(this, R.color.colorAccent);
        int colorTo = ContextCompat.getColor(this, R.color.colorAccentLighter);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),colorFrom, colorTo);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setDuration(1200); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textViewQuizWalkInfoBar.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        btnStartQuizWalk = findViewById(R.id.btn_start_quizwalk_active);
        btnStartQuizWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(QuizWalkActivity.this);
                builder1.setMessage("Starta tipspromenad?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "STARTA!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mQuizWalkMapFragment.zoomToMyLocation();

                                dialog.cancel();
                                btnStartQuizWalk.setText("ÖVERSIKT");
                                double distance = SphericalUtil.computeDistanceBetween(new LatLng(59.3311,18.0019), new LatLng(qwTestObject.getQuizPlaces().get(0).getLatitude(), qwTestObject.getQuizPlaces().get(0).getLongitude()));
                                textViewQuizWalkInfoBar.setText(qwTestObject.getName() + "\n" + "0/11 frågor besvarade.");
                                textViewQuizWalkInfoBar.setText("Gå " + (int) distance + " meter till " + qwTestObject.getQuizPlaces().get(0).getName() + " för att få första frågan! ");
                            }
                        });

                builder1.setNegativeButton(
                        "Avbryt",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    public QuizWalk getQwTestObject() {
        return qwTestObject;
    }

    public void setQwTestObject(QuizWalk qwTestObject) {
        this.qwTestObject = qwTestObject;
    }


}


