package com.example.studentmanager.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentmanager.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    // navigation dùng chung
    protected void navigateTo(Class<?> target) {
        startActivity(new Intent(this, target));
    }

    // hook: mặc định KHÔNG xử lý IME
    protected boolean enableImeInset() {
        return false;
    }

    protected int getRootViewId() {
        return R.id.main;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (enableImeInset()) {
            View root = findViewById(getRootViewId());
            applyImeInset(root);
        }
    }

    private void applyImeInset(View root) {
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
            v.setPadding(0, 0, 0, ime.bottom);
            return insets;
        });
    }
}
