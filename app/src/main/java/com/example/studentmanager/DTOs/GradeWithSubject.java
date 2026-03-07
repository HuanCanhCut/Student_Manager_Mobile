package com.example.studentmanager.DTOs;

import com.example.studentmanager.network.DTOs.response.GradeResponse;

public class GradeWithSubject extends GradeModel {
    private SubjectModel subject;

    public SubjectModel getSubject() {
        return subject;
    }
}
