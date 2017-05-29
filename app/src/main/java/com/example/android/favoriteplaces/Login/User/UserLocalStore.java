package com.example.android.favoriteplaces.Login.User;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by arturs.amirovs on 12/04/17.
 */

public class UserLocalStore {

    public static final String SP_NAME = "UserDetails";
    SharedPreferences UserLocalDatabase;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String NAME = "NAME";


    public UserLocalStore (Context context){
        UserLocalDatabase = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }


    public void storeUserData(User user){                                                           //Add current user into local database
        SharedPreferences.Editor spEditor = UserLocalDatabase.edit();
        spEditor.putString(USERNAME, user.getUsername());
        spEditor.putString(PASSWORD, user.getPassword());
        spEditor.putString(EMAIL, user.getEmail());
        spEditor.putString(NAME, user.getName());
        spEditor.commit();
    }

    public User getStoredUserData(){

        String username = UserLocalDatabase.getString(USERNAME, null);
        String password = UserLocalDatabase.getString(PASSWORD, "");
        String email = UserLocalDatabase.getString(EMAIL, "");
        String name = UserLocalDatabase.getString(NAME, "");

        User storedUser = new User(username, password, email, name);

        return storedUser;
    }

    public boolean isEmpty(){
        String username = UserLocalDatabase.getString(USERNAME, "");
        String password = UserLocalDatabase.getString(PASSWORD, "");

        if(username.equals(""))
            return true;
        else
            return false;
    }

    public void clearLocalDatabase(){                                                               //Clear local database
        SharedPreferences.Editor spEditor = UserLocalDatabase.edit();
        spEditor.clear();
    }




}
