package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lifeisquest.databinding.ActivitySignUp2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity2 extends AppCompatActivity {
    ActivitySignUp2Binding binding;
    FirebaseFirestore db;
    String pw1, pw2;
    String nickname;
    String email;
    String name;
    FirebaseAuth mAuth; // 계정 등록을 위한 FireBase 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // back btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        // sign up
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw1 = binding.pw1.getText().toString();
                pw2 = binding.pw2.getText().toString();
                nickname = binding.nickname.getText().toString();
                email = binding.idEmail.getText().toString();
                Map<String, String> user = new HashMap<>();
                user.put("pw1", pw1);
                user.put("pw2", pw2);
                user.put("nickname", nickname);
                user.put("email", email);
                user.put("name", name);
                if (Objects.equals(pw1, "")
                        || Objects.equals(pw2, "")
                        || Objects.equals(nickname, "")
                        || Objects.equals(email, "")
                        || Objects.equals(name, "")) {
                    Toast.makeText(getApplicationContext(), "빈 칸이 있습니다.\n 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pw1.equals(pw2)) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.\n비밀번호를 다시 입력해주세요", Toast.LENGTH_LONG).show();
                    } else if (!isEmail(email)) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 다릅니다.\n다시 입력해주세요", Toast.LENGTH_LONG).show();
                    } else {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(email, pw1)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            //아이디 생성이 완료 되었을 때
                                            Toast.makeText(getApplicationContext(), "정상적으로 계정 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "비정상ㄴ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                        db = FirebaseFirestore.getInstance();
                        db.collection("User").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.\n로그인해 주세요.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
            }
        });
    }


    public static boolean isEmail(String email) {
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            returnValue = true;
        }
        return returnValue;
    }
}