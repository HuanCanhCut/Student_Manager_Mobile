package com.example.studentmanager.DTOs;

import androidx.annotation.Nullable;

import java.util.Date;

public class GradeModel extends BaseModel {
    private int student_id;
    private int subject_id;
    private float grade;

    @Nullable
    private float component_grade;
    private int semester;
}