package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.request.LoginRequest;
import com.example.studentmanager.network.DTOs.response.LoginResponse;
import com.example.studentmanager.network.services.AuthService;
import com.example.studentmanager.utils.DTOs.HandleApiError;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

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
        EdgeToEdge.enable(this);
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
                String emailValue = emailInput.getText().toString();
                String passwordValue = passwordInput.getText().toString();

                if (emailValue.trim().isEmpty() || passwordValue.trim().isEmpty()) {
                    errorMessage.setText("Vui lòng nhập đầy đủ các trường");

                    return;
                }

                LoginRequest request = new LoginRequest(emailInput.getText().toString(), passwordInput.getText().toString());

                AuthService apiService = ApiClient.getInstance().create(AuthService.class);

                apiService.login(request).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = HandleApiError.parse(response);

                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        submitButton.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.d("LOGIN", t.getMessage());
                    }
                });
            }
        });
    }

}