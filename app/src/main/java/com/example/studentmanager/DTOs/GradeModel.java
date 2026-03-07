package com.example.studentmanager.DTOs;

import androidx.annotation.Nullable;

import java.util.Date;

public class GradeModel extends BaseModel {
    private int student_id;
    private int subject_id;

    @Nullable
    private Float grade;

    @Nullable
    private Float component_grade;
    private int semester;

    public int getStudent_id() {
        return student_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public Float getGrade() {
        return grade;
    }

    public Float getComponent_grade() {
        return component_grade;
    }

    public int getSemester() {
        return semester;
    }
}