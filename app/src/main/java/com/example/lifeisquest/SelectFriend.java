package com.example.lifeisquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SelectFriend extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<User> friendlist;
    private FirebaseFirestore db;
    private User myData;
    private ArrayList<String> friendString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        mListView = (ListView) findViewById(R.id.listView);

        /* 아이템 추가 및 어댑터 등록 */
        dataSetting();
        db = FirebaseFirestore.getInstance();
    }

    private void dataSetting(){

        FriendSelectAdapter mMyAdapter = new FriendSelectAdapter();
        DocumentReference docRef = db.collection("User").document(myData.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                friendString = user.getFriend();
            }
        });
        for(String friend : friendString){
            docRef = db.collection("User").document(friend);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    friendlist.add(user);
                }
            });
        }

        mMyAdapter.addItem(friendlist);//임시로 동그라미 넣음


        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }

}