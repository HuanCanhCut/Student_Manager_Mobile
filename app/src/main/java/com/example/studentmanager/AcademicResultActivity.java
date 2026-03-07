package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.adapter.HomeAdapter;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GradeResponse;
import com.example.studentmanager.network.services.GradeService;
import com.example.studentmanager.utils.BottomNavigation;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcademicResultActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    boolean isSetGpa = false; // flag check only set gpa on first time
    Integer semester = 1;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_academic_results);

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
                    case 0:
                        semester = 1;
                        break;
                    case 1:
                        semester = 2;
                        break;
                    default:
                        semester = null;
                }

                fetchSubjects(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };

        adapter = new HomeAdapter(10, listener);
        recyclerView.setAdapter(adapter);

        fetchSubjects(0); // 0: Học kì I, 1: Học kì 2, 2: Tất cà học kì

    }

    private void fetchSubjects(int tab) {
        GradeService apiService = ApiClient.getInstance(this).create(GradeService.class);

        apiService.getGrades(1, 10, this.semester).enqueue(new Callback<GradeResponse>() {
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
                        String errorJson = response.errorBody() != null
                                ? response.errorBody().string() : "null";
                        Log.d("ERROR BODY", errorJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GradeResponse> call, Throwable t) {
                Toast.makeText(AcademicResultActivity.this,
                        "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}