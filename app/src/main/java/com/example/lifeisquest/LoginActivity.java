package com.example.lifeisquest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    EditText ed_email;
    EditText ed_password;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser User;
    RelativeLayout join_btn;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        mAuth = FirebaseAuth.getInstance();
        join_btn = findViewById(R.id.join_btn);
        ed_email = findViewById(R.id.email_edit);
        ed_password = findViewById(R.id.password);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        CardView btn_login = findViewById(R.id.login_btn);
        btn_login.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();
                if(email.length() > 0){
                    if(!email.contains("@")){
                        ed_email.setError("????????? ????????? ????????? ???????????????.");
                    }else {
                        if (password.length() > 0) {
                            if(password.length() < 6){
                                ed_password.setError("6?????? ????????? ??????????????? ??????????????????.");
                            }else {
                                LoadingView loadingView = new LoadingView(LoginActivity.this);
                                loadingView.show("loading...");
                                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            start(loadingView);
                                        }else {
                                            loadingView.stop();
                                            Toast.makeText(LoginActivity.this, "???????????? ?????? ????????? ?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                            ed_email.setError("???????????? ?????? ????????? ?????? ?????????????????????.");
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "login failed :" + e.toString() , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            ed_password.setError("??????????????? ??????????????????.");
                        }
                    }
                }else {
                    ed_email.setError("???????????? ??????????????????.");
                }
            }
        });
    }

    public void start(LoadingView loadingView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String description = "??? ????????? ????????? ???????????????";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CH_NOTICE", "??? ????????? ??????", importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
        User = mAuth.getCurrentUser();
        if (User.isEmailVerified()) {
            User = mAuth.getCurrentUser();
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {

                    if (!task.isSuccessful()) {
                        loadingView.stop();
                        Toast.makeText(LoginActivity.this, "Token registration failed 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String token = task.getResult();

                    db.collection("users").document(User.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful() && task.getResult().exists()){
                                db.collection("users").document(User.getUid()).update("token",token).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingView.stop();
                                        if(task.isSuccessful()){
                                            SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("uid",User.getUid());
                                            editor.apply();
                                            Toast.makeText(LoginActivity.this, "Login done!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(LoginActivity.this, "Failed to save data", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else {
                                loadingView.stop();
                                User.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(LoginActivity.this, "Please re-register as a member with that email.", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingView.stop();
                    Toast.makeText(LoginActivity.this, "Token registration failed 1", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            loadingView.stop();
            Toast.makeText(LoginActivity.this, "????????? ????????? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(LoginActivity.this, "?????? ??? ????????? ???????????????." , Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(LoginActivity.this, "?????? ??? ????????? ???????????????." , Toast.LENGTH_SHORT).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();

                finish(); // app ?????? ?????????
            }
        }
    }
}