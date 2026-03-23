package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.adapter.WeekAdapter;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GetWeeksResponse;
import com.example.studentmanager.network.services.SemesterService;
import com.example.studentmanager.utils.BottomNavigation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekActivity extends BaseActivity {

    RecyclerView recyclerView;
    private WeekAdapter adapter;

    ImageView backBtn;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_week);

        BottomNavigation.setup(WeekActivity.this, 2);

        recyclerView = findViewById(R.id.recyclerView);
        backBtn = findViewById(R.id.backBtn);

        // Dùng LinearLayoutManager, tắt scroll riêng của RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setInitialPrefetchItemCount(10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        adapter = new WeekAdapter();
        recyclerView.setAdapter(adapter);

        SemesterService apiService = ApiClient.getInstance(this).create(SemesterService.class);
        apiService.getWeeks().enqueue(new Callback<GetWeeksResponse>() {
            @Override
            public void onResponse(Call<GetWeeksResponse> call, Response<GetWeeksResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setWeeksResponse(response.body());
                } else {
                    Toast.makeText(WeekActivity.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetWeeksResponse> call, Throwable t) {
                Log.d("GET HISTORY", t.getMessage());
                Toast.makeText(WeekActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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