package com.example.lifeisquest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeisquest.databinding.ActivityLogin2Binding;
import com.example.lifeisquest.databinding.ActivityMainBinding;

public class LoginActivity2 extends AppCompatActivity {
    private ActivityLogin2Binding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "클릭되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        binding.emailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEdit = binding.emailEdit;
                if(emailEdit.getText().toString().equals("E-mail")) {
                    Log.d("email_address", emailEdit.getText().toString());
                    emailEdit.setText("");
                }
            }
        });
    }
}