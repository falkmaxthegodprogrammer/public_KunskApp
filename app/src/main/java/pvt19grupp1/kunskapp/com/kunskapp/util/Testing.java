package pvt19grupp1.kunskapp.com.kunskapp.util;

import android.util.Log;

import java.util.List;

import pvt19grupp1.kunskapp.com.kunskapp.models.GooglePlaceModel;

public class Testing {

    public static void printPlaces(List<GooglePlaceModel> list, String tag) {
        if(list != null) {
            for (GooglePlaceModel googlePlace : list) {
                Log.d(tag, googlePlace.getName() + " " + googlePlace.getId() + " " +googlePlace.getIcon());
                String[] types = googlePlace.getTypes();
                for(int i = 0; i < types.length; i++) {
                    System.out.println(types[i]);
                }
            }
        }
    }

}
