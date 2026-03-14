package com.example.studentmanager.DTOs;

public class UserModelWithOverview extends UserModel{
    private Float gpa;
    private int passed_credits;
    private String current_semester;
    private String prev_semester;
    private String next_semester;

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
}
