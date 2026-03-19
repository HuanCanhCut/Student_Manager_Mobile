package com.example.studentmanager.network.services;

import androidx.annotation.Nullable;

import com.example.studentmanager.network.DTOs.response.GradeResponse;
import com.example.studentmanager.network.DTOs.response.ScheduleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleService {
    @GET("schedules/{weekId}")
    Call<ScheduleResponse> getSchedules(
            @Path("weekId") int weekId
    );

}
