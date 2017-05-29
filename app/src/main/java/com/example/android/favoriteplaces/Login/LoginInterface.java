package com.example.android.favoriteplaces.Login;

import android.content.Context;

/**
 * Created by arturs.amirovs on 26/04/17.
 */

public interface LoginInterface {

    void authentication(Context context);
    void onRegister(String name, String email, String registrationUsername, String registrationPassword);
    boolean onLogin(String username);

}
