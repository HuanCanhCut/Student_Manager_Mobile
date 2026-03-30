package com.example.studentmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;

public class SessionManager {

    private static SessionManager instance;

    private UserModelWithOverview currentUser;
    private String accessToken;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public UserModelWithOverview getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserModelWithOverview user) {
        currentUser = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String token) {
        accessToken = token;
    }

    public void clearSession() {
        currentUser = null;
        accessToken = null;
    }

    public void remove_token (Context context) {
        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        prefs.edit().remove("access_token").apply();
    }
}