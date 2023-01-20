package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    FriendFragment friendFragment;
    QuestFragment questFragment;
    MyPageFragment myPageFragment;
    CalendarView calendarView;
    Boolean changed=false;
    ArrayList<Quest> quest_array=new ArrayList<>();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String uid="3";
    ArrayList<String> Quest_list=new ArrayList<>();
    User user;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intent=new Intent();
        //uid=intent.getStringExtra("Text");
        setting();
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
    public void setting() {
        final int[] cnt = {0};
        db.collection("User").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    try {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            Quest_list = user.getQuest();
                        } else {
                            Quest_list = null;
                        }
                    }
                    catch(Exception e){
                        Log.d("Exception",e.getLocalizedMessage());
                    }
                }
                else{
                    // 튜토각???
                }
            }
        });
        for (String id : Quest_list){
            db.collection("Quest").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    cnt[0] = cnt[0] + 1;
                    if(task.isSuccessful()){
                        Quest quest = task.getResult().toObject(Quest.class);
                        quest_array.add(quest);
                    }else {
                        //실패
                    }
                    if(cnt[0] == Quest_list.size()){
                        //작업이 모두 완료
                    }
                }
            });
        }

    }
}