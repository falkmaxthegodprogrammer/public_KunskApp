package pvt19grupp1.kunskapp.com.kunskapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;

public class PlaceActivity extends BaseActivity {

    private TextView placeName;

    private static final String TAG = "PlaceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        placeName = findViewById(R.id.place_name);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("place")) {
            GooglePlaceModel googlePlace = getIntent().getParcelableExtra("place");
            Log.d(TAG, "getIncomingIntent: " + googlePlace.getName());
            placeName.setText(googlePlace.getName());
        }
    }

}
