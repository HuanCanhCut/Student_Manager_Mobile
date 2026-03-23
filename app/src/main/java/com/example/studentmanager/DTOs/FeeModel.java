package com.example.studentmanager.DTOs;

public class FeeModel extends BaseModel{
    private int faculty_id;
    private int semester_id;

    private double money;


    public int getFaculty_id() {
        return faculty_id;
    }

    public int getSemester_id() {
        return semester_id;
    }

    public double getMoney() {
        return money;
    }
}
