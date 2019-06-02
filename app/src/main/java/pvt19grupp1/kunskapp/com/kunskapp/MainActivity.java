package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Constants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pvt19grupp1.kunskapp.com.kunskapp.models.QuizPlace;
import pvt19grupp1.kunskapp.com.kunskapp.models.QuizWalk;
import pvt19grupp1.kunskapp.com.kunskapp.models.User;
import pvt19grupp1.kunskapp.com.kunskapp.repositories.QuizWalkRepositoryTemp;
import pvt19grupp1.kunskapp.com.kunskapp.util.QuizWalksHardcodedUtil;

import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.ERROR_DIALOG_REQUEST;
import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static pvt19grupp1.kunskapp.com.kunskapp.util.ConstantKeys.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private Button btnActivityList;
    private Button btnMapActivity;
    private Button btnLoginActivity;

    private Intent teacherMainActivityIntent;
    private Intent loginActivityIntent;
    private Intent welcomeScreenIntent;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        teacherMainActivityIntent = new Intent(this, TeacherMainActivity.class);
        loginActivityIntent = new Intent(this, LoginActivity.class);
        welcomeScreenIntent = new Intent(this, WelcomeScreenActivity.class);


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        QuizWalk qw1 = QuizWalksHardcodedUtil.createQuizWalkHandels();
        QuizWalk qw2 = QuizWalksHardcodedUtil.createQuizWalkFredhallsRundan();
        QuizWalkRepositoryTemp.globalTempAllQuizWalks.add(qw1);
        QuizWalkRepositoryTemp.globalTempAllQuizWalks.add(qw2);

        System.out.println(QuizWalkRepositoryTemp.globalTempAllQuizWalks.size() + " - MainActivity: ADDED quizwalks");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    User masterUser = new User(user.getUid());
                    ((UserClient)(getApplicationContext())).setUser(masterUser);
                    startActivity(teacherMainActivityIntent);
                } else {
                    startActivity(welcomeScreenIntent);
                }
            }
        };
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted) {

                } else {
                    getLocationPermission();
                }
            }
        }

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

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);

        if(checkMapServices()) {
            if(mLocationPermissionGranted) {

            }
        } else {
            getLocationPermission();
        }
    }

}
