package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;
import com.example.studentmanager.adapter.AcademicResultAdapter;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GradeResponse;
import com.example.studentmanager.network.services.GradeService;
import com.example.studentmanager.utils.BottomNavigation;
import com.example.studentmanager.utils.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcademicResultActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private AcademicResultAdapter adapter;

    boolean isSetGpa = false; // flag check only set gpa on first time

    ImageView backBtn;

    UserModelWithOverview currentUser = SessionManager.getInstance().getCurrentUser();

    String semester = this.currentUser.getCurrent_semester();

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_results);

        initView();

        eventsHandler();

        BottomNavigation.setup(this, 1);

        recyclerView = findViewById(R.id.recyclerView);

        // Dùng LinearLayoutManager, tắt scroll riêng của RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setInitialPrefetchItemCount(10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();

                switch (index) {
                    case 0: // Tab hiện tại
                        semester = AcademicResultActivity.this.currentUser.getCurrent_semester();
                        break;
                    case 1: // Tab trước đó
                            semester = AcademicResultActivity.this.currentUser.getPrev_semester();
                        break;
                    default:
                        semester = null;
                }

                adapter.setCurrentSemester(AcademicResultActivity.this.semester);


                fetchSubjects(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };

        adapter = new AcademicResultAdapter(10, listener, this.semester);
        recyclerView.setAdapter(adapter);

        fetchSubjects(0); // 0: Học kì I, 1: Học kì 2, 2: Tất cà học kì

    }

    public void initView() {
        backBtn = findViewById(R.id.backBtn);
    }

    public void eventsHandler(){
     backBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish(); // navigate back
         }
     });
    }

    private void fetchSubjects(int tab) {
        GradeService apiService = ApiClient.getInstance(this).create(GradeService.class);

        apiService.getGrades(this.semester).enqueue(new Callback<GradeResponse>() {
            @Override
            public void onResponse(Call<GradeResponse> call, Response<GradeResponse> response) {
                if (response.isSuccessful()) {
                    GradeResponse res = response.body();

                    assert res != null;

                    if (!isSetGpa) {
                        adapter.setGpa(res.getGpa());
                        isSetGpa = true;
                    }

                    adapter.setSelectedTab(tab);
                    adapter.setGradeResponse(res); // update list môn học
                } else {
                    try {
                        String errorJson = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.d("ERROR BODY", errorJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GradeResponse> call, Throwable t) {
                Toast.makeText(AcademicResultActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}