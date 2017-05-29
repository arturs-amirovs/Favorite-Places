package com.example.android.favoriteplaces.Login;

import android.content.Context;
import android.content.Intent;

import com.example.android.favoriteplaces.Login.User.User;
import com.example.android.favoriteplaces.Login.User.UserLocalStore;
import com.example.android.favoriteplaces.MyApplication;
import com.example.android.favoriteplaces.Registration.RegistrationActivity;

import static com.example.android.favoriteplaces.MyApplication.dataSource;


/**
 * Created by arturs.amirovs on 27/04/17.
 */

public class LoginCommunicator implements LoginInterface {

    private UserLocalStore userLocalStore;
    private User user;

    public LoginCommunicator(){
        userLocalStore = new UserLocalStore(MyApplication.getContext());
        user = userLocalStore.getStoredUserData();
    }

    @Override
    public void authentication(Context context) {
        if(user.getUsername()!=null && user.getUsername().equals("") ){
            context.startActivity(new Intent(context, RegistrationActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            context.startActivity(new Intent(context, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void onRegister(String name, String email, String registrationUsername, String registrationPassword) {
        dataSource.formatDataBase();
        user = new User(registrationUsername, registrationPassword, email, name);
        userLocalStore.storeUserData(user);
    }

    @Override
    public boolean onLogin(String username) {
        if(user.getUsername()!=null && user.getUsername().equals(username)){
            return true;
        } else {
            return false;
        }
    }
}
