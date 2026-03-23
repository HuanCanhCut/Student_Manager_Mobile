package com.example.studentmanager.DTOs;

import com.google.gson.annotations.SerializedName;

public class UserModelWithOverview extends UserModel{
    private String email;
    private Float gpa;
    private int passed_credits;
    private String current_semester;
    private String prev_semester;
    private String next_semester;

    @SerializedName("class")
    private ClassModel classModel;

    public Float getGpa() {
        return gpa;
    }

    public int getPassed_credits() {
        return passed_credits;
    }

    public String getCurrent_semester() {
        return current_semester;
    }

    public String getPrev_semester() {
        return prev_semester;
    }

    public String getNext_semester() {
        return next_semester;
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    public String getEmail() {
        return email;
    }
}
