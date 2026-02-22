package com.example.studentmanager.utils.DTOs;

import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private Object error;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getError() {
        return error;
    }
}
