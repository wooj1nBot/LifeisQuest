package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    FriendFragment friendFragment;
    QuestFragment questFragment;
    MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigationView);
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        friendFragment = new FriendFragment();
        questFragment = new QuestFragment();
        myPageFragment = new MyPageFragment();
        initFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.page_home:
                        transaction.replace(R.id.mother, homeFragment).commitAllowingStateLoss();;
                        break;
                    case R.id.page_friend:
                        transaction.replace(R.id.mother, friendFragment).commitAllowingStateLoss();
                        break;
                    case R.id.page_add_quest:

                        break;
                    case R.id.page_quest:
                        transaction.replace(R.id.mother, questFragment).commitAllowingStateLoss();
                        break;
                    case R.id.page_mypage:
                        transaction.replace(R.id.mother, myPageFragment).commitAllowingStateLoss();
                        break;

                }

                return true;
            }
        });


    }

    public void initFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mother, homeFragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}