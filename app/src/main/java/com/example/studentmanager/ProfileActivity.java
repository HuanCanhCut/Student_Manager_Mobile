package com.example.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.utils.BottomNavigation;
import com.example.studentmanager.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends BaseActivity {
    UserModelWithOverview currentUser = SessionManager.getInstance().getCurrentUser();

    ImageView avatar, logout_button;
    TextView name, code;

    Button editProfileBtn;

    TextView gpa, credit, semester, birthday, phoneNumber, address, email, gender, studentLastName, studentName, className, studentCode;


    @Override
    protected boolean enableImeInset (){
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigation.setup(this, 4);

        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        code = findViewById(R.id.code);
        logout_button = findViewById(R.id.logout_button);


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

        Glide.with(this)
                .load("https://www.robins.vn/wp-content/uploads/2026/01/hinh-anh-con-meo-cute-1.jpg.jpg")
                .placeholder(R.color.white)
                .into(avatar);

        name.setText(currentUser.getFull_name());
        code.setText("MSSV: " + currentUser.getCode());

        gpa.setText(String.valueOf(this.currentUser.getGpa()));
        credit.setText(String.valueOf(this.currentUser.getPassed_credits()));
        semester.setText(String.valueOf(this.currentUser.getCurrent_semester()));

        studentLastName.setText(this.currentUser.getLast_name());
        studentName.setText(this.currentUser.getFirst_name());
        studentCode.setText(this.currentUser.getCode());
        className.setText(this.currentUser.getClassModel().getName());
        email.setText(this.currentUser.getEmail());
        Date birth = this.currentUser.getBirthday();

        if (birth != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            birthday.setText(sdf.format(birth));
        } else {
            birthday.setText("");
        }
        phoneNumber.setText(this.currentUser.getPhone());
        address.setText(this.currentUser.getAddress());

        if (String.valueOf(this.currentUser.getGender()).equals("male")) {
            gender.setText("Nam");
        } else {
            gender.setText("Nữ");
        }

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(EditProfile.class);
            }
        });

        logout_button.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc muốn đăng xuất không?")
                    .setPositiveButton("Có", (dialog, which) -> {

                        // remove token fron pref
                        SessionManager.getInstance().clearSession();
                        SessionManager.getInstance().remove_token(ProfileActivity.this);


                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
    }
}