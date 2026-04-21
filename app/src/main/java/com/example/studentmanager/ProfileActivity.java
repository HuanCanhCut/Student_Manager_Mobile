package com.example.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import com.bumptech.glide.Glide;

import com.example.studentmanager.DTOs.UserModelWithOverview;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.utils.BottomNavigation;
import com.example.studentmanager.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends BaseActivity {
    private UserModelWithOverview currentUser;

    private ImageView avatar, logout_button, backBtn;
    private TextView name, code;
    private Button editProfileBtn;
    private TextView gpa, credit, semester, birthday, phoneNumber, address, email, gender, studentLastName, studentName, className, studentCode;

    @Override
    protected boolean enableImeInset (){
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            // If user session is lost, redirect to Login
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        initViews();
        setupBottomNav();
        displayUserData();
        setupEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = SessionManager.getInstance().getCurrentUser();
        displayUserData();
    }

    private void initViews() {
        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        code = findViewById(R.id.code);
        logout_button = findViewById(R.id.logout_button);
        backBtn = findViewById(R.id.backBtn);

        gpa = findViewById(R.id.gpa);
        credit = findViewById(R.id.credit);
        semester = findViewById(R.id.semester);

        birthday = findViewById(R.id.birthday);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        studentLastName = findViewById(R.id.studentLastName);
        studentName = findViewById(R.id.studentName);
        className = findViewById(R.id.className);
        studentCode = findViewById(R.id.studentCode);
        editProfileBtn = findViewById(R.id.editProfileBtn);
    }

    private void setupBottomNav() {
        BottomNavigation.setup(this, 4);
    }

    private void displayUserData() {
        Glide.with(this)
                .load("https://www.robins.vn/wp-content/uploads/2026/01/hinh-anh-con-meo-cute-1.jpg.jpg")
                .placeholder(R.color.white)
                .into(avatar);

        name.setText(currentUser.getFull_name());
        code.setText("MSSV: " + currentUser.getCode());

        gpa.setText(currentUser.getGpa() != null ? String.valueOf(currentUser.getGpa()) : "0.0");
        credit.setText(String.valueOf(currentUser.getPassed_credits()));
        semester.setText(currentUser.getCurrent_semester() != null ? currentUser.getCurrent_semester() : "");

        studentLastName.setText(currentUser.getLast_name());
        studentName.setText(currentUser.getFirst_name());
        studentCode.setText(currentUser.getCode());
        
        if (currentUser.getClassModel() != null) {
            className.setText(currentUser.getClassModel().getName());
        } else {
            className.setText("");
        }

        email.setText(currentUser.getEmail());
        Date birth = currentUser.getBirthday();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (birth != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            birthday.setText(sdf.format(birth));
        } else {
            birthday.setText("");
        }
        phoneNumber.setText(currentUser.getPhone());
        address.setText(currentUser.getAddress());

        if (String.valueOf(this.currentUser.getGender()).equals("male")) {
            gender.setText("Nam");
        } else {
            gender.setText("Nữ");
        }
    }

    private void setupEventListeners() {
        if (backBtn != null) {
            backBtn.setOnClickListener(v -> finish());
        }

        editProfileBtn.setOnClickListener(v -> navigateTo(EditProfile.class));

        logout_button.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc muốn đăng xuất không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        SessionManager.getInstance().clearSession();
                        SessionManager.getInstance().remove_token(ProfileActivity.this);

                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}
