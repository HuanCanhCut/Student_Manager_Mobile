package com.example.studentmanager.network.services;

import androidx.annotation.Nullable;

import com.example.studentmanager.network.DTOs.response.GpaHistoryResponse;
import com.example.studentmanager.network.DTOs.response.GradeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GradeService {

    @GET("students/grades")

    Call<GradeResponse> getGrades(
            @Query("semester") @Nullable String semester
    );

    @GET("students/gpa/history")
    Call<GpaHistoryResponse> getGpaHistory();
}
