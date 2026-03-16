package com.example.studentmanager.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentmanager.AcademicResultActivity;
import com.example.studentmanager.FeeActivity;
import com.example.studentmanager.HomeActivity;
import com.example.studentmanager.ProfileActivity;
import com.example.studentmanager.R;

public class BottomNavigation {

    public static void setup(Activity activity, int activeTab) {

        LinearLayout home = activity.findViewById(R.id.homeTab);
        LinearLayout grade = activity.findViewById(R.id.gradeTab);
        LinearLayout profile = activity.findViewById(R.id.tabProfile);

        LinearLayout tuitionFee = activity.findViewById(R.id.tuitionFeeTab);
        LinearLayout calendar = activity.findViewById(R.id.calendarTab);


        ImageView homeIcon = activity.findViewById(R.id.homeIcon);
        TextView homeText = activity.findViewById(R.id.textHome);

        ImageView profileIcon = activity.findViewById(R.id.profileIcon);
        TextView profileText = activity.findViewById(R.id.textProfile);

        ImageView gradeIcon = activity.findViewById(R.id.gradeIcon);
        TextView gradeText = activity.findViewById(R.id.textGrade);

        ImageView tuitionFeeIcon = activity.findViewById(R.id.tuitionFeeIcon);
        TextView tuitionFeeText = activity.findViewById(R.id.textTuitionFee);


        int activeColor = Color.parseColor("#2563EB");
        int inactiveColor = Color.parseColor("#94A3B8");

        // reset
        homeIcon.setColorFilter(inactiveColor);
        homeText.setTextColor(inactiveColor);

        gradeIcon.setColorFilter(inactiveColor);
        gradeText.setTextColor(inactiveColor);

        profileIcon.setColorFilter(inactiveColor);
        profileText.setTextColor(inactiveColor);

        tuitionFeeIcon.setColorFilter(inactiveColor);
        tuitionFeeText.setTextColor(inactiveColor);


        // highlight tab
        switch (activeTab) {
            case 0:
                homeIcon.setColorFilter(activeColor);
                homeText.setTextColor(activeColor);
                break;
            case 1:
                gradeIcon.setColorFilter(activeColor);
                gradeText.setTextColor(activeColor);
                break;
            case 3:
                tuitionFeeIcon.setColorFilter(activeColor);
                tuitionFeeText.setTextColor(activeColor);
                break;
            case 4:
                profileIcon.setColorFilter(activeColor);
                profileText.setTextColor(activeColor);
                break;
        }

        // navigation
        home.setOnClickListener(v -> {
            if (!(activity instanceof HomeActivity)) {
                activity.startActivity(new Intent(activity, HomeActivity.class));
            }
        });

        grade.setOnClickListener(v -> {
            if (!(activity instanceof AcademicResultActivity)) {
                activity.startActivity(new Intent(activity, AcademicResultActivity.class));
            }
        });

        profile.setOnClickListener(v -> {
            if (!(activity instanceof ProfileActivity)) {
                activity.startActivity(new Intent(activity, ProfileActivity.class));
            }
        });

        tuitionFee.setOnClickListener(v -> {
            if (!(activity instanceof FeeActivity)) {
                activity.startActivity(new Intent(activity, FeeActivity.class));
            }
        });


    }
}