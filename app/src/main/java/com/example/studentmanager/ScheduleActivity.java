package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.adapter.DayAdapter;
import com.example.studentmanager.adapter.ScheduleAdapter;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GetWeeksResponse;
import com.example.studentmanager.network.DTOs.response.ScheduleResponse;
import com.example.studentmanager.network.services.ScheduleService;
import com.example.studentmanager.network.services.SemesterService;
import com.example.studentmanager.utils.BottomNavigation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends BaseActivity {

    RecyclerView scheduleRecyclerView, dayRecyclerView;
    ImageView backBtn;

    ScheduleAdapter scheduleAdapter;
    DayAdapter dayAdapter;
    GetWeeksResponse.Week week;

    ScheduleResponse scheduleResponse = new ScheduleResponse();

    String selectedDate = null;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);

        this.week = (GetWeeksResponse.Week) getIntent().getSerializableExtra("week");

        scheduleRecyclerView = findViewById(R.id.recyclerScheduleView);
        dayRecyclerView = findViewById(R.id.dayRecyclerView);

        BottomNavigation.setup(ScheduleActivity.this, 2);

        // Setup Schedule RecyclerView
        LinearLayoutManager scheduleLayoutManager = new LinearLayoutManager(this);
        scheduleLayoutManager.setInitialPrefetchItemCount(10);
        scheduleRecyclerView.setLayoutManager(scheduleLayoutManager);
        scheduleRecyclerView.setHasFixedSize(false);

        scheduleAdapter = new ScheduleAdapter();
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        // Setup Day RecyclerView
        LinearLayoutManager dayLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dayLayoutManager.setInitialPrefetchItemCount(10);
        dayRecyclerView.setLayoutManager(dayLayoutManager);
        dayRecyclerView.setHasFixedSize(false);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dayAdapter = new DayAdapter();
        dayRecyclerView.setAdapter(dayAdapter);

        dayAdapter.setOnDayClickListener(((position, date) -> {
            this.selectedDate = date;
            scheduleAdapter.setSchedules(filterScheduleByDay());
        }));

        dayAdapter.setWeek(week);


        // Fetch api get schedule in week
        ScheduleService apiService = ApiClient.getInstance(this).create(ScheduleService.class);

        apiService.getSchedules(this.week.getWeek_number()).enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (response.isSuccessful()) {
                    ScheduleActivity.this.scheduleResponse = response.body();
                    scheduleAdapter.setSchedules(filterScheduleByDay());
                } else {
                    Toast.makeText(ScheduleActivity.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.d("GET SCHEDULES", t.getMessage());
                Toast.makeText(ScheduleActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<ScheduleResponse.Data> filterScheduleByDay () {
        List<ScheduleResponse.Data> filtered = new ArrayList<>();

        this.scheduleResponse.getData().forEach(data -> {
            if (Objects.equals(data.getDate(), this.selectedDate)) {
                filtered.add(data);
            }
        });

        return filtered;
    }
}
