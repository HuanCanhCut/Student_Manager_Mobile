package com.example.studentmanager.network.services;

import com.example.studentmanager.network.DTOs.response.GradeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GradeService {

    @GET("students/grades")

    Call<GradeResponse> getGrades(
            @Query("page") int page,
            @Query("per_page") int perPage
    );
}
