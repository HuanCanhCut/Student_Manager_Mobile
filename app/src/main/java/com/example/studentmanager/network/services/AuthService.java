package com.example.studentmanager.network.services;

import com.example.studentmanager.network.DTOs.request.LoginRequest;
import com.example.studentmanager.network.DTOs.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
