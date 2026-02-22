package com.example.studentmanager.utils.DTOs;

import com.google.gson.Gson;

import retrofit2.Response;

public class HandleApiError {
    public static String parse(Response<?> response) {

        if (response.errorBody() == null) {
            return "Unknown error";
        }

        try {
            Gson gson = new Gson();
            ApiError apiError = gson.fromJson(
                    response.errorBody().charStream(),
                    ApiError.class
            );

            return apiError.getMessage();

        } catch (Exception e) {
            return "Parse error";
        }
    }
}
