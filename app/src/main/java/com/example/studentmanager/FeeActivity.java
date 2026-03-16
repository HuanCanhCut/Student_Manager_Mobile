package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GetWeeksResponse;
import com.example.studentmanager.network.services.SemesterService;
import com.example.studentmanager.utils.BottomNavigation;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeeActivity extends BaseActivity {
    TextView paymentTerm;


    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fee);

        BottomNavigation.setup(this, 3);

        paymentTerm = findViewById(R.id.payment_term);

        SemesterService apiService = ApiClient.getInstance(this).create(SemesterService.class);
        apiService.getWeeks().enqueue(new Callback<GetWeeksResponse>() {
            @Override
            public void onResponse(Call<GetWeeksResponse> call, Response<GetWeeksResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("GET HISTORY", new Gson().toJson(response.body()));

                    for (int i = 0; i < response.body().getData().size(); i++){
                        if(response.body().getData().get(i).getWeek_number() == 10){
                            Date birth = response.body().getData().get(i).getWeek_start();

                            if (birth != null) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                paymentTerm.setText(sdf.format(birth));
                            } else {
                                paymentTerm.setText("");
                            }

                        }
                    }
                } else {
                    Toast.makeText(FeeActivity.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetWeeksResponse> call, Throwable t) {
                Log.d("GET HISTORY",t.getMessage());
                Toast.makeText(FeeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}