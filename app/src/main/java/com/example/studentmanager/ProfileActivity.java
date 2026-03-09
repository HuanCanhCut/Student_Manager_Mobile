package com.example.studentmanager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

public class ProfileActivity extends BaseActivity {
    UserModelWithOverview currentUser = SessionManager.getInstance().getCurrentUser();

    ImageView avatar;
    TextView name, code;

    TextView gpa, credit, semester, birthday, phoneNumber, address, email, gender, studentLastName, studentName, className, studentCode;


    @Override
    protected boolean enableImeInset (){
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        BottomNavigation.setup(this, 4);

        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        code = findViewById(R.id.code);

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
//        className.setText(this.currentUser.getClass_name());
//        email.setText(this.currentUser.getEmail());
        birthday.setText((CharSequence) this.currentUser.getBirthday());
//        phoneNumber.setText(this.currentUser.getPhone_number());
        address.setText(this.currentUser.getAddress());
//        gender.setText(this.currentUser.getGender().ordinal());


    }
}