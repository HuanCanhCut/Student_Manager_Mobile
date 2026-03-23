package com.example.studentmanager.DTOs;

import java.util.Date;

public class BaseModel {
    protected int id;
    protected Date created_at;
    private Date updated_at;

    public int getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
}
