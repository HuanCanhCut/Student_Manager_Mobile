package com.example.studentmanager.network.DTOs.response;

import java.util.Date;

public class LoginResponse {
    private Data data;
    private Meta meta;

    public Data getData() { return data; }
    public Meta getMeta() { return meta; }

    enum Role {
        student,
        teacher,
        admin
    }

    enum Gender {
        male,
        female,
        other
    }

    public static class Data {
        private int id;
        private String first_name;
        private String last_name;
        private String full_name;
        private String avatar;
        private Role role;
        private Date birthday;
        private Gender gender;
        private String phone;
        private String address;
        private String code;
        private int class_id;
        private Date created_at;
        private Date updated_at;
    }

    public static class Meta {
        private String access_token;
        private String refresh_token;

        public String getAccess_token() { return access_token; }
        public String getRefresh_token() { return refresh_token; }
    }
}