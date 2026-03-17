package com.example.studentmanager.network.DTOs.response;

public class TotalDeptResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data{
        private int total;

        public int getTotal() {
            return total;
        }
    }
}
