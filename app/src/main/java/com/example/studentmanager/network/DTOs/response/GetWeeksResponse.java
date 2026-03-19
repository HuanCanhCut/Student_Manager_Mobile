package com.example.studentmanager.network.DTOs.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetWeeksResponse implements Serializable {
    private List<Week> data = new ArrayList<>();

    public List<Week> getData() {
        return data;
    }

    public static class Week implements Serializable {
        private int week_number;
        private Date week_start;
        private Date week_end;
        private boolean is_current;

        public int getWeek_number() {
            return week_number;
        }

        public Date getWeek_start() {
            return week_start;
        }

        public Date getWeek_end() {
            return week_end;
        }

        public boolean isIs_current() {
            return is_current;
        }
    }
}
