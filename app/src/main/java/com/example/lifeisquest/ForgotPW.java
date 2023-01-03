package com.example.lifeisquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lifeisquest.databinding.ActivityForgotPwBinding;

public class ForgotPW extends AppCompatActivity {
    ActivityForgotPwBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // backBtn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}