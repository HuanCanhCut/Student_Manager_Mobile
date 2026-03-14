package com.example.studentmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.request.LoginRequest;
import com.example.studentmanager.network.DTOs.response.LoginResponse;
import com.example.studentmanager.network.services.AuthService;
import com.example.studentmanager.utils.EmailValidator;
import com.example.studentmanager.utils.HandleApiError;
import com.example.studentmanager.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    TextView forgotPassword;
    MaterialButton submitButton;
    TextInputEditText emailInput;
    TextInputEditText passwordInput;

    TextView errorMessage;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        eventsHandler();
    }

    private void initView () {
        forgotPassword = findViewById(R.id.forgotPassword);
        submitButton = findViewById(R.id.submitBtn);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        errorMessage = findViewById(R.id.errorMessage);
    }

    private void eventsHandler () {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(ForgotPasswordActivity.class);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue = Objects.requireNonNull(emailInput.getText()).toString();
                String passwordValue = Objects.requireNonNull(passwordInput.getText()).toString();

                if (emailValue.trim().isEmpty() || passwordValue.trim().isEmpty()) {
                    errorMessage.setText("Vui lòng nhập đầy đủ các trường");

                    return;
                }

                boolean isEmailValid = EmailValidator.validate(emailValue);

                if (!isEmailValid) {
                    errorMessage.setText("Địa chỉ email không đúng định dạng, vui lòng nhập lại!");
                }

                LoginRequest request = new LoginRequest(emailInput.getText().toString(), passwordInput.getText().toString());

                AuthService apiService = ApiClient.getInstance(LoginActivity.this).create(AuthService.class);

                apiService.login(request).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            String json = new Gson().toJson(response.body());
                            String accessToken = response.body() != null ? response.body().getMeta().getAccess_token() : null;

                            SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);

                            prefs.edit().putString("access_token", accessToken).apply();

                            SessionManager.getInstance().setAccessToken(accessToken);
                            assert response.body() != null;
                            SessionManager.getInstance().setCurrentUser(response.body().getData());

                            navigateTo(AcademicResultActivity.class);
                        } else {
                            String errorMessage = HandleApiError.parse(response);

                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        submitButton.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.d("LOGIN", Objects.requireNonNull(t.getMessage()));
                    }
                });
            }
        });
    }

}