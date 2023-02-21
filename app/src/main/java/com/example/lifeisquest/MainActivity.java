package com.example.lifeisquest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    QuestFragment questFragment;
    FriendFragment friendFragment;
    MyPageFragment myPageFragment;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        if(currentUser != null && currentUser.isEmailVerified()) {

            bottomNavigationView = findViewById(R.id.navigationView);
            fragmentManager = getSupportFragmentManager();
            homeFragment = new HomeFragment();
            questFragment = new QuestFragment();
            friendFragment = new FriendFragment();
            myPageFragment = new MyPageFragment();

            initFragment();

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()){
                        case R.id.page_home:
                            transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                            break;
                        case R.id.page_friend:
                            transaction.replace(R.id.frameLayout, friendFragment).commitAllowingStateLoss();
                            break;
                        case R.id.page_add_quest:
                            Intent intent = new Intent(MainActivity.this, Quest_Making_Activity.class);
                            startActivity(intent);
                            break;
                        case R.id.page_quest:
                            transaction.replace(R.id.frameLayout, questFragment).commitAllowingStateLoss();
                            break;
                        case R.id.page_mypage:
                            transaction.replace(R.id.frameLayout, myPageFragment).commitAllowingStateLoss();
                            break;
                    }

                    return true;
                }
            });
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    public void initFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameLayout, homeFragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}