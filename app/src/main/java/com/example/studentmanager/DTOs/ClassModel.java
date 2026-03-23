package com.example.studentmanager.DTOs;

public class ClassModel extends BaseModel{
    private String name;
    private String code;
    private Integer home_teacher_id;
    private Integer faculty_id;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Integer getHome_teacher_id() {
        return home_teacher_id;
    }

    public Integer getFaculty_id() {
        return faculty_id;
    }
}
