package com.example.android.favoriteplaces;

import android.app.Application;
import android.content.Context;

import com.example.android.favoriteplaces.Login.LoginCommunicator;
import com.example.android.favoriteplaces.data.DataBaseInterface;
import com.example.android.favoriteplaces.data.DataBaseManager;

/**
 * Created by arturs.amirovs on 26/04/17.
 */

public class MyApplication extends Application {



    private static Context context;
    public static LoginCommunicator loginCommunicator;
    public static DataBaseInterface dataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        dataSource = new DataBaseManager();
        loginCommunicator = new LoginCommunicator();
        loginCommunicator.authentication(context);

    }

    public static Context getContext(){
        return context;
    }





}
