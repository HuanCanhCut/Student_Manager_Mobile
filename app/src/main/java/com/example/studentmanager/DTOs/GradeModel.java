package com.example.studentmanager.DTOs;

import androidx.annotation.Nullable;

import java.util.Date;

public class GradeModel extends BaseModel {
    private int student_id;
    private int subject_id;

    @Nullable
    private Float grade;

    @Nullable
    private Float first_component_grade;

    @Nullable
    private Float second_component_grade;


    public int getStudent_id() {
        return student_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public Float getGrade() {
        return grade;
    }

    @Nullable
    public Float getFirst_component_grade() {
        return first_component_grade;
    }

    @Nullable
    public Float getSecond_component_grade() {
        return second_component_grade;
    }
}