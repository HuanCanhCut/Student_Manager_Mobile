package com.example.studentmanager.network.DTOs.response;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;

import java.util.Date;

public class LoginResponse {
    private UserModelWithOverview data;
    private Meta meta;

    public UserModelWithOverview getData() { return data; }
    public Meta getMeta() { return meta; }

    public static class Meta {
        private String access_token;
        private String refresh_token;

        public String getAccess_token() { return access_token; }
        public String getRefresh_token() { return refresh_token; }
    }
}