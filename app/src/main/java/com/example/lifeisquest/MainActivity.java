package com.example.lifeisquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private String TAG = "kimjindata";
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btn = findViewById(R.id.button);

        btn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),QuestActivity.class);
            startActivity(intent);
        });
    }
}