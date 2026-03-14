package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.adapter.AcademicResultAdapter;
import com.example.studentmanager.adapter.GpaHistoryAdapter;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GpaHistoryResponse;
import com.example.studentmanager.network.services.GradeService;
import com.example.studentmanager.utils.BottomNavigation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GPAHistoryActivity extends BaseActivity {
    GpaHistoryAdapter adapter;
    RecyclerView recyclerView;

    ImageView backBtn;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_history);

        BottomNavigation.setup(GPAHistoryActivity.this, 1);

        recyclerView = findViewById(R.id.recyclerView);
        backBtn = findViewById(R.id.backBtn);

        // Dùng LinearLayoutManager, tắt scroll riêng của RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setInitialPrefetchItemCount(10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        adapter = new GpaHistoryAdapter();
        recyclerView.setAdapter(adapter);

        GradeService apiService = ApiClient.getInstance(this).create(GradeService.class);

        apiService.getGpaHistory().enqueue(new Callback<GpaHistoryResponse>() {
            @Override
            public void onResponse(Call<GpaHistoryResponse> call, Response<GpaHistoryResponse> response) {
                if (response.isSuccessful()) {
                    GpaHistoryResponse res = response.body();

                    adapter.setGpaHistory(res);
                } else {
                    Toast.makeText(GPAHistoryActivity.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GpaHistoryResponse> call, Throwable t) {
                Log.d("GET HISTORY",t.getMessage());
                Toast.makeText(GPAHistoryActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}