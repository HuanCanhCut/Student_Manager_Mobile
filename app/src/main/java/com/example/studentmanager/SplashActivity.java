package com.example.studentmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.response.GetCurrentUserResponse;
import com.example.studentmanager.network.services.AuthService;
import com.example.studentmanager.utils.HandleApiError;
import com.example.studentmanager.utils.SessionManager;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);

        String token = prefs.getString("access_token", null);

        if (Objects.equals(token, "") || token == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            AuthService apiService = ApiClient.getInstance(this).create(AuthService.class);

            apiService.getCurrentUser().enqueue(new Callback<GetCurrentUserResponse>() {
                @Override
                public void onResponse(Call<GetCurrentUserResponse> call, Response<GetCurrentUserResponse> response) {
                    if (response.isSuccessful()) {
                        UserModelWithOverview user = response.body().getData();

                        SessionManager.getInstance().setCurrentUser(user);

                        startActivity( new Intent(SplashActivity.this, AcademicResultActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<GetCurrentUserResponse> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d("LOGIN", Objects.requireNonNull(t.getMessage()));

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            });
        }
    }
}