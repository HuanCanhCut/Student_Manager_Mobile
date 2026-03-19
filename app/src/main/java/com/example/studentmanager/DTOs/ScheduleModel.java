package com.example.studentmanager.DTOs;

import java.util.Date;

public class ScheduleModel extends BaseModel {
    private Integer class_subject_id;
    private String date;
    private String start_time;
    private String end_time;
    private String room;

    public Integer getClass_subject_id() {
        return class_subject_id;
    }

    public String getDate() {
        return date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getRoom() {
        return room;
    }
}
