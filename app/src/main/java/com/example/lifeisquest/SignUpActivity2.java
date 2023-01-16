package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.lifeisquest.databinding.ActivitySignUp2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.ibrahimsn.lib.CirclesLoadingView;

public class SignUpActivity2 extends AppCompatActivity {
    ActivitySignUp2Binding binding;
    FirebaseFirestore db;
    String pw;
    String nickname;
    String email;
    String name;
    FirebaseAuth mAuth; // 계정 등록을 위한 FireBase 객체
    User user;
    Boolean isver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // email-certification
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw = binding.passwordEdit.getText().toString().replaceAll(" ", "");
                name = binding.nameEdit.getText().toString().replaceAll(" ", "");
                email = binding.emailEdit.getText().toString().replaceAll(" ", "");

                mAuth = FirebaseAuth.getInstance();
                if (email.isEmpty() || pw.isEmpty()) {
                    Toast.makeText(SignUpActivity2.this, "이메일 주소 또는 비밀번호를 입력해주세요 !", Toast.LENGTH_SHORT).show();
                } else {
                    if (!email.contains("@")) {
                        binding.emailEdit.setError("올바른 이메일 주소를 적어주세요");
                    } else {
                        if (pw.length() < 6) {
                            binding.passwordEdit.setError("6자리 이상의 비밀번호를 입력해주세요.");
                        } else {
                            sendVerifyEmail(email, pw, user);
                        }
                    }

                }
            }
        });

        // sign up
        binding.signUpEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw = binding.passwordEdit.getText().toString();
                name = binding.nameEdit.getText().toString();
                email = binding.emailEdit.getText().toString();
                if (!isEmail(email)) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 다릅니다.\n다시 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //아이디 생성이 완료 되었을 때
                                        Toast.makeText(getApplicationContext(), "정상적으로 계정 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "비정상적인 게정생성이 감지되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    User user = new User();
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
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "뒤로가기버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
            finish();
            return true;
        }
        return false;
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

    public void sendVerifyEmail(String email, String password, User user) {
        final LoadingView loadingView = new LoadingView(SignUpActivity2.this);
        loadingView.show("Loading...");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 사용자 인증메일 보내기
                        FirebaseUser User = mAuth.getCurrentUser();
                        User.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity2.this, "인증용 이메일을 전송했습니다.", Toast.LENGTH_LONG).show();
                                    isver = true;
                                    binding.emailEdit.setEnabled(false);
                                    binding.passwordEdit.setEnabled(false);
                                    user.uid = User.getUid();

                                    saveData(user, loadingView);
                                } else {
                                    loadingView.stop();
                                    Toast.makeText(getApplicationContext(), "이메일 인증 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingView.stop();
                        binding.emailEdit.setError("이미 가입된 이메일 주소입니다.");
                    }
                });
    }

    public void saveData(User user, LoadingView loadingView) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    loadingView.stop();
                    Toast.makeText(SignUpActivity2.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.token = task.getResult();
                db.collection("User").document(user.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity2.this, "이메일 인증을 완료해주세요 !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity2.this, "데이터 저장에 실패했습니다. \n다시 시도해주세요!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}