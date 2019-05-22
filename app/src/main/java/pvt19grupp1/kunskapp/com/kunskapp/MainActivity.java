package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private Button btnActivityList;
    private Button btnMapActivity;
    private Button btnLoginActivity;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        btnActivityList = findViewById(R.id.btn_activity_list);
        btnMapActivity = findViewById(R.id.btn_activity_map);
        btnLoginActivity = findViewById(R.id.button2);

        final Intent intent = new Intent(this, QuestionListActivity.class);
        final Intent placeListActivityIntent = new Intent(this, PlaceListActivity.class);
        final Intent teacherMainActivityIntent = new Intent(this, TeacherMainActivity.class);
        final Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        final Intent welcomeScreenIntent = new Intent(this, WelcomeScreenActivity.class);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    startActivity(teacherMainActivityIntent);
                } else {
                    startActivity(welcomeScreenIntent);
                }
            }
        };


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btnActivityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(placeListActivityIntent);
            }
        });

        btnMapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(teacherMainActivityIntent);
            }
        });

        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginActivityIntent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null) {
           mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
