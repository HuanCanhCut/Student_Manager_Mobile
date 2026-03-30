package com.example.studentmanager.network.services;

import androidx.annotation.Nullable;

import com.example.studentmanager.network.DTOs.request.UpdateCurrentUserRequest;
import com.example.studentmanager.network.DTOs.response.GradeResponse;
import com.example.studentmanager.network.DTOs.response.UpdateCurrentUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MeService {

    @PATCH("me/update")
    Call<UpdateCurrentUserResponse> updateCurrentUser(
            @Body UpdateCurrentUserRequest request
    );
}
