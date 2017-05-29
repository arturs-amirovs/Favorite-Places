package com.example.android.favoriteplaces.Login.User;

/**
 * Created by arturs.amirovs on 12/04/17.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private String name;

    public User(String username, String password, String email, String name){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
