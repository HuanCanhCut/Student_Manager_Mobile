package com.example.studentmanager.network.DTOs.response;

import java.util.ArrayList;
import java.util.List;

public class GpaHistoryResponse {
    private List<Data> data = new ArrayList<>();

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        private String gpa;
        private String semester;
        private String gpa_level;
        private String school_year;


        public String getGpa() {
            return gpa;
        }

        public String getSemester() {
            return semester;
        }

        public String getGpa_level() {
            return gpa_level;
        }

        public String getSchool_year() {
            return school_year;
        }
    }
}
