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

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);

        // Dùng LinearLayoutManager, tắt scroll riêng của RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setInitialPrefetchItemCount(10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        GradeService apiService = ApiClient.getInstance(this).create(GradeService.class);

        apiService.getGrades( 1, 10).enqueue(new Callback<GradeResponse>() {
            @Override
            public void onResponse(Call<GradeResponse> call, Response<GradeResponse> response) {
                Log.d("IS SUCCESS", String.valueOf(response.isSuccessful()));

                if (response.isSuccessful()) {
                    GradeResponse res = response.body();

                    adapter = new HomeAdapter(res);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Log toàn bộ JSON trả về từ server
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

                Toast.makeText(HomeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.d("GET STUDENT GRADES", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}