package com.example.studentmanager.network.DTOs.response;

import com.example.studentmanager.DTOs.GradeModel;
import com.example.studentmanager.DTOs.SubjectModel;

import java.util.ArrayList;
import java.util.List;

public class GradeResponse {
    private final List<Data> data = new ArrayList<>();
    private final Meta meta = new Meta();

    public List<Data> getData () {
        return this.data;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public float getGpa () {
        return this.meta.gpa;
    }

    public static class Data extends GradeModel {
        private SubjectModel subject;
    }

    public static class Meta {
        private MetaPagination pagination;
        private MetaLink links;
        private float gpa;
    }
}
