package com.example.studentmanager.network.services;

import androidx.annotation.Nullable;

import com.example.studentmanager.network.DTOs.response.GetWeeksResponse;
import com.example.studentmanager.network.DTOs.response.GradeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  SemesterService {
    @GET("semesters/weeks")
    Call<GetWeeksResponse> getWeeks();
}
