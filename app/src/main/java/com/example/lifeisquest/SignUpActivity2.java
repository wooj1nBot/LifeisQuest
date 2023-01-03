package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lifeisquest.databinding.ActivitySignUp2Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity2 extends AppCompatActivity {
    ActivitySignUp2Binding binding;
    FirebaseFirestore db;
    String id;
    String pw1, pw2;
    String nickname;
    String email;
    String name;

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
                id = binding.idText.getText().toString();
                pw1 = binding.pw1.getText().toString();
                pw2 = binding.pw2.getText().toString();
                nickname = binding.nickname.getText().toString();
                email = binding.email.getText().toString();
                name = binding.name.getText().toString();
                Log.d("mode1", id);
                Map<String, String> user = new HashMap<>();
                user.put("id", id);
                user.put("pw1", pw1);
                user.put("pw2", pw2);
                user.put("nickname", nickname);
                user.put("email", email);
                user.put("name", name);
                if (!pw1.equals(pw2)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.\n비밀번호를 다시 입력해주세요", Toast.LENGTH_LONG).show();
                } else if (!isEmail(email)) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 다릅니다.\n다시 입력해주세요", Toast.LENGTH_LONG).show();
                }else {
                    db = FirebaseFirestore.getInstance();
                    db.collection("User")
                            .document(id)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "suc", Toast.LENGTH_LONG).show();
                                    finish();
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