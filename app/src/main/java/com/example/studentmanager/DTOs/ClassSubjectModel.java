package com.example.studentmanager.DTOs;

public class ClassSubjectModel extends BaseModel{
    private Integer class_id;
    private Integer subject_id;
    private Integer semester_id;
    private Integer teacher_id;

    public Integer getClass_id() {
        return class_id;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public Integer getSemester_id() {
        return semester_id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }
}
