package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.lifeisquest.databinding.ActivityLogin2Binding;
import com.example.lifeisquest.databinding.ActivityLoginBinding;
import com.example.lifeisquest.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity2 extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 가입하기 btn
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        // forgot pw
        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPW.class);
                startActivity(intent);
                finish();
            }
        });

        // login
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEdit.getText().toString();
                String pw = binding.password.getText().toString();
                if (email.isEmpty() || pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "입력 사항을 확인해보세요", Toast.LENGTH_SHORT).show();
                }else if (!isEmail(email)) {
                    Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다.\n다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    // 로그인이 성공하는 조건 분기
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }
    public static boolean isEmail(String email){
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()){
            returnValue = true;
        }
        return returnValue;
    }
}