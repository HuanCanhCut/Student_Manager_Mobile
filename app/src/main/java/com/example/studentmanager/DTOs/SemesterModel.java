package com.example.studentmanager.DTOs;

public class SemesterModel extends BaseModel {
    private String name;
    private String start_date;
    private String end_date;

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
