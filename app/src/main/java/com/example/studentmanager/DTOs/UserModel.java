package com.example.studentmanager.DTOs;

import androidx.annotation.Nullable;

import com.example.studentmanager.network.DTOs.response.LoginResponse;

import java.util.Date;

public class UserModel extends BaseModel{
    @Nullable
    public String getYear() {
        return year;
    }

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

    private String first_name;
    private String last_name;
    private String full_name;
    private Role role;
    private Date birthday;
    private Gender gender;
    private String phone;
    private String address;
    private String code;
    private int class_id;

    @Nullable
    private String year;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public Role getRole() {
        return role;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public int getClass_id() {
        return class_id;
    }
}
