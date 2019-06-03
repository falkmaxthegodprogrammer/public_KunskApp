package pvt19grupp1.kunskapp.com.kunskapp.util;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.ChosenQuestionsFragment;
import pvt19grupp1.kunskapp.com.kunskapp.ChosenQuestionsFragmentDialog;
import pvt19grupp1.kunskapp.com.kunskapp.R;
import pvt19grupp1.kunskapp.com.kunskapp.adapters.QuizPlaceRecyclerAdapter;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;


@SuppressLint("ValidFragment")
public class TabbedDialog extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    QuizPlace qPlace;
    QuizPlaceRecyclerAdapter mQuizPlaceRecyclerAdapter;
    Button cancelBtn;

    @SuppressLint("ValidFragment")
    public TabbedDialog(QuizPlace qPlace, QuizPlaceRecyclerAdapter quizPlaceRecyclerAdapter) {
        this.qPlace = qPlace;
        this.mQuizPlaceRecyclerAdapter = quizPlaceRecyclerAdapter;
    }

    public void dismissFragment() {
        dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.dialog_sample, container, false);

        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);

        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        adapter.addFragment("VALDA FRÅGOR", new ChosenQuestionsFragmentDialog(qPlace));
        adapter.addFragment("BLÄDDRA BLAND FÄRDIGA FRÅGOR", CustomFragment.createInstance("Bläddra"));
        adapter.addFragment("SKRIV NY FRÅGA", new CreateNewQuestionFragment(qPlace, mQuizPlaceRecyclerAdapter, this));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootview;
    }




}