package com.example.studentmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.example.studentmanager.base.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity {

    TextView backToLogin;

    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        eventsHandler();
    }

    private void initViews() {
        backToLogin = findViewById(R.id.backToLogin);
    }

    private void eventsHandler() {
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}