package com.example.studentmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentmanager.DTOs.UserModel;
import com.example.studentmanager.DTOs.UserModelWithOverview;
import com.example.studentmanager.base.BaseActivity;
import com.example.studentmanager.network.ApiClient;
import com.example.studentmanager.network.DTOs.request.UpdateCurrentUserRequest;
import com.example.studentmanager.network.DTOs.response.UpdateCurrentUserResponse;
import com.example.studentmanager.network.services.MeService;
import com.example.studentmanager.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends BaseActivity {

    TextInputEditText phoneNumberInput, addressInput;
    TextView saveButton;
    ImageView backButton;

    TextView studentCode, studentName, email, gender, birthday, className;


    @Override
    protected boolean enableImeInset() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        phoneNumberInput = findViewById(R.id.phoneNumber);
        addressInput = findViewById(R.id.address);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backBtn);
        studentCode = findViewById(R.id.studentCode);
        studentName = findViewById(R.id.studentName);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        className = findViewById(R.id.classLame);

        UserModelWithOverview currentUser = SessionManager.getInstance().getCurrentUser();

        phoneNumberInput.setText(currentUser.getPhone());
        addressInput.setText(currentUser.getAddress());

        studentCode.setText(currentUser.getCode());
        studentName.setText(currentUser.getFull_name());
        email.setText(currentUser.getEmail());

        Date birth = currentUser.getBirthday();

        if (birth != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            birthday.setText(sdf.format(birth));
        } else {
            birthday.setText("");
        }

        if (String.valueOf(currentUser.getGender()).equals("male")) {
            gender.setText("Nam");
        } else {
            gender.setText("Nữ");
        }

        backButton.setOnClickListener(v -> {
            finish();
        });

        saveButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberInput.getText().toString();
            String address = addressInput.getText().toString();

            boolean isValidPhoneNumber = phoneNumber.matches("(03|05|07|08|09|01[2689])([0-9]{8})\\b");

            if (!isValidPhoneNumber) {
                Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phoneNumber.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

            UpdateCurrentUserRequest request = new UpdateCurrentUserRequest(phoneNumber, address);

            MeService service = ApiClient.getInstance(this).create(MeService.class);

            service.updateCurrentUser(request).enqueue(new Callback<UpdateCurrentUserResponse>() {
                @Override
                public void onResponse(Call<UpdateCurrentUserResponse> call, Response<UpdateCurrentUserResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditProfile.this, "Cập nhật hồ sơ thành công", Toast.LENGTH_SHORT).show();

                        UpdateCurrentUserResponse res = response.body();
                        if (res == null || res.getData() == null) return;

                        UserModel updatedData = res.getData();

                        try {
                            Class<?> updateClass = updatedData.getClass();

                            for (Field field : updateClass.getDeclaredFields()) {
                                field.setAccessible(true);
                                Object newValue = field.get(updatedData);

                                if (newValue != null) {
                                    try {
                                        Class<?> targetClass = currentUser.getClass();
                                        Field targetField = null;

                                        while (targetClass != null) {
                                            try {
                                                targetField = targetClass.getDeclaredField(field.getName());
                                                break;
                                            } catch (NoSuchFieldException e) {
                                                targetClass = targetClass.getSuperclass();
                                            }
                                        }

                                        if (targetField != null) {
                                            targetField.setAccessible(true);
                                            targetField.set(currentUser, newValue);
                                        }
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }


                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateCurrentUserResponse> call, Throwable t) {
                    Toast.makeText(EditProfile.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", Objects.requireNonNull(t.getMessage()));
                }
            });
        });

    }
}