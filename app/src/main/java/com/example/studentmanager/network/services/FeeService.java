package com.example.studentmanager.network.services;

import com.example.studentmanager.network.DTOs.response.GetFeeResponse;
import com.example.studentmanager.network.DTOs.response.TotalDeptResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeeService {
    @GET("fees/dept")
    Call<TotalDeptResponse> getTotalDept();

    @GET("fees")
    Call<GetFeeResponse> getFee();
}
