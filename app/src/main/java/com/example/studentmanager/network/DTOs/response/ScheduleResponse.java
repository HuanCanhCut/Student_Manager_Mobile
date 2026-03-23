package com.example.studentmanager.network.DTOs.response;

import com.example.studentmanager.DTOs.BaseModel;
import com.example.studentmanager.DTOs.ClassSubjectModel;
import com.example.studentmanager.DTOs.ScheduleModel;
import com.example.studentmanager.DTOs.SubjectModel;
import com.example.studentmanager.DTOs.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleResponse {


    private List<Data> data = new ArrayList<>();

    public List<Data> getData() {
        return data;
    }

    public static class Data extends ScheduleModel {
        private ClassSubjectModelWithSubject class_subject;

        public ClassSubjectModelWithSubject getClass_subject() {
            return class_subject;
        }

        public static class ClassSubjectModelWithSubject extends ClassSubjectModel {
            private SubjectModel subject;
            private UserModel teacher;

            public SubjectModel getSubject() {
                return subject;
            }

            public UserModel getTeacher() {
                return teacher;
            }
        }
    }
}
