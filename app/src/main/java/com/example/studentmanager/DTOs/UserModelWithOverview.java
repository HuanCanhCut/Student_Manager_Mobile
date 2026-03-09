package com.example.studentmanager.DTOs;

public class UserModelWithOverview extends UserModel{
    private int gpa;
    private int passed_credits;
    private int current_semester;

    public int getGpa() {
        return gpa;
    }

    public int getPassed_credits() {
        return passed_credits;
    }

    public int getCurrent_semester() {
        return current_semester;
    }
}
