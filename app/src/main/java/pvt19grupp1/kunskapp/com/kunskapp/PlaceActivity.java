package pvt19grupp1.kunskapp.com.kunskapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;

public class PlaceActivity extends BaseActivity {

    private TextView placeName;
    private Button firebaseButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase myDb;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;

    private GooglePlaceModel googlePlaceInstance;

    private static final String TAG = "PlaceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        placeName = findViewById(R.id.place_name);
        getIncomingIntent();
        Toast.makeText(this, "Detta är ett TOAST message", Toast.LENGTH_LONG);


        firebaseButton = (Button) findViewById(R.id.btn_firebase_test);

        myDb = FirebaseDatabase.getInstance();
        myRef = myDb.getReference("singleplace");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                   // user is logged in
                } else {
                   //user is not logged in
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue("Hello world!");
                myRef.child("name").setValue("hej");

            }
        });

    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("place")) {
            GooglePlaceModel googlePlace = getIntent().getParcelableExtra("place");
            Log.d(TAG, "getIncomingIntent: " + googlePlace.getName());
            placeName.setText(googlePlace.getName() + " " + googlePlace.getRating() + " " + " " + googlePlace.getUser_ratings_total());
            googlePlaceInstance = getIntent().getParcelableExtra("place");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



}
