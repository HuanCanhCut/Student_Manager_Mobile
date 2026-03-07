package com.example.studentmanager.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentmanager.AcademicResultActivity;
import com.example.studentmanager.HomeActivity;
import com.example.studentmanager.R;

public class BottomNavigation {

    public static void setup(Activity activity, int activeTab){

        LinearLayout home = activity.findViewById(R.id.homeTab);
        LinearLayout grade = activity.findViewById(R.id.gradeTab);

        ImageView homeIcon = activity.findViewById(R.id.homeIcon);
        TextView homeText = activity.findViewById(R.id.textHome);

        ImageView gradeIcon = activity.findViewById(R.id.gradeIcon);
        TextView gradeText = activity.findViewById(R.id.textGrade);

        int activeColor = Color.parseColor("#2563EB");
        int inactiveColor = Color.parseColor("#94A3B8");

        // reset
        homeIcon.setColorFilter(inactiveColor);
        homeText.setTextColor(inactiveColor);

        gradeIcon.setColorFilter(inactiveColor);
        gradeText.setTextColor(inactiveColor);

        // highlight tab
        if(activeTab == 0){
            homeIcon.setColorFilter(activeColor);
            homeText.setTextColor(activeColor);
        }

        if(activeTab == 1){
            gradeIcon.setColorFilter(activeColor);
            gradeText.setTextColor(activeColor);
        }

        // navigation
        home.setOnClickListener(v -> {
            if(!(activity instanceof HomeActivity)){
                activity.startActivity(new Intent(activity, HomeActivity.class));
            }
        });

        grade.setOnClickListener(v -> {
            if(!(activity instanceof AcademicResultActivity)){
                activity.startActivity(new Intent(activity, AcademicResultActivity.class));
            }
        });
    }
}