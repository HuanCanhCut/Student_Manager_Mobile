package com.example.studentmanager.network.DTOs.response;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;

public class GetCurrentUserResponse {
    private UserModelWithOverview data;

    public UserModelWithOverview getData() {
        return data;
    }
}
