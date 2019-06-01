package pvt19grupp1.kunskapp.com.kunskapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import pvt19grupp1.kunskapp.com.kunskapp.models.User;

public class UserClient extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}