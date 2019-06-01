package pvt19grupp1.kunskapp.com.kunskapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.adapters.OnQuizWalkListListener;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuizWalkListRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.ViewPagerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.util.QuizWalksHardcodedUtil;

import pvt19grupp1.kunskapp.com.kunskapp.models.User;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuizWalkRepositoryTemp;

public class MyQuizWalksActivity extends BaseActivity implements OnQuizWalkListListener {

    private TextView txtViewQuizWalkName;
    private TextView textViewQuizWalkInfo;
    private TextView textViewBottom;
    private User user;
    private List<QuizWalk> localQuizWalks = new ArrayList<>();
    private List<QuizWalk> userQuizWalks = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuizWalkListRecyclerAdapter mQuizWalksRecyclerAdapter;
    private TabLayout tabLayout;

    private QuizWalkMapFragment quizWalkMapFragment;
    private ViewPager fragmentViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quizwalks);

        user = ((UserClient)getApplicationContext()).getUser();

        textViewQuizWalkInfo = findViewById(R.id.text_view_quiz_walk_info);
        textViewBottom = findViewById(R.id.bottom_navigation);
        userQuizWalks = user.getMyQuizzes();

        //QuizWalk handels = QuizWalksHardcodedUtil.createQuizWalkHandels();

        userQuizWalks.add(QuizWalksHardcodedUtil.createQuizWalkHandels());


       // QuizWalksHardcodedUtil.printQuizPlaces(user);

        //QuizWalksHardcodedUtil.printQuizPlaces(user);

        recyclerView = findViewById(R.id.quizplaces_list_horizontal);
        initRecyclerView();

        tabLayout = (TabLayout) findViewById(R.id.tablayout_quizwalks_id);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        quizWalkMapFragment = new QuizWalkMapFragment();
        fragmentViewPager = (ViewPager) findViewById(R.id.viewpager_myquizwalk_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(quizWalkMapFragment, "Översikt");
        adapter.AddFragment(new quizWalkQuestionListFragment(), "Frågor");

        fragmentViewPager.setAdapter(adapter);
        fragmentViewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(fragmentViewPager);

        int colorFrom = ContextCompat.getColor(this, R.color.colorAccent);
        int colorTo = ContextCompat.getColor(this, R.color.colorAccentLighter);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),colorFrom, colorTo);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setDuration(1200); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textViewBottom.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

    }

    private void initRecyclerView() {
      //  VerticalSpacingDecorator itemDecorator = new VerticalSpacingDecorator(5);
     //   recyclerView.addItemDecoration(itemDecorator);
        mQuizWalksRecyclerAdapter = new QuizWalkListRecyclerAdapter(userQuizWalks, this);
        recyclerView.setAdapter(mQuizWalksRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }


    @Override
    public void onQuizWalkClick(int position) {
        quizWalkMapFragment.clearMap();
        QuizWalk qw = mQuizWalksRecyclerAdapter.getSelectedQuestion(position);
        textViewQuizWalkInfo.setText(qw.getName());

        List<QuizPlace> temp = qw.getQuizPlaces();
        List<LatLng> bounds = new ArrayList<>();

        for(QuizPlace qp : temp) {
            bounds.add(new LatLng(qp.getLatitude(), qp.getLongitude()));
        }
        quizWalkMapFragment.addMarkersFromQuizWalkList(qw.getQuizPlaces());
        quizWalkMapFragment.drawPolylines(qw.getLatLngPoints());
        quizWalkMapFragment.zoomToRouteBounds(bounds);

        String noPlaces, totalDistance, estimatedTime;
        noPlaces = String.valueOf((int) qw.getQuizPlaces().size());
        totalDistance = String.valueOf((int) qw.getTotaldistance());
        estimatedTime = String.valueOf((int) qw.getTotaldistance() / 90);

        textViewBottom.setText("Platser: " + noPlaces + " Sträcka: " + totalDistance + " meter." + " Ca-tid: " + estimatedTime + " minuter.");

    }
}


