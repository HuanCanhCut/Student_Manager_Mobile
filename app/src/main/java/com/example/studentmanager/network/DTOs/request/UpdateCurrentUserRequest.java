package com.example.studentmanager.network.DTOs.request;

public class UpdateCurrentUserRequest {
    private String phone_number;
    private String address;

    public UpdateCurrentUserRequest(String phone_number, String address) {
        this.phone_number = phone_number;
        this.address = address;
    };

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }
}
