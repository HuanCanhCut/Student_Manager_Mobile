package com.example.studentmanager.utils;

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
}