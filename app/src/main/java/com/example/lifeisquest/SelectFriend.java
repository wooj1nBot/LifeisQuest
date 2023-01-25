package com.example.lifeisquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectFriend extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<User> friendlist = new ArrayList<>();
    //private FirebaseFirestore db;
    private User myData;
    private ArrayList<String> friendString;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        mListView = (ListView) findViewById(R.id.listView);
        listView = findViewById(R.id.listView);



        /* 아이템 추가 및 어댑터 등록 */
        FriendSelectAdapter mMyAdapter = dataSetting2();
        //db = FirebaseFirestore.getInstance();
        mListView.setAdapter(mMyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("result", friendlist.get(i).getUid());
                setResult(RESULT_OK, intent);
                Log.d("dkdk", "kk");
                finish();
            }
        });
    }

//    private void dataSetting(){
//
//        FriendSelectAdapter mMyAdapter = new FriendSelectAdapter();
//        DocumentReference docRef = db.collection("User").document(myData.getUid());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                User user = documentSnapshot.toObject(User.class);
//                friendString = user.getFriend();
//            }
//        });
//        for(String friend : friendString){
//            docRef = db.collection("User").document(friend);
//            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    User user = documentSnapshot.toObject(User.class);
//                    friendlist.add(user);
//                }
//            });
//        }
//
//        mMyAdapter.addItem(friendlist);//임시로 동그라미 넣음
//
//
//        /* 리스트뷰에 어댑터 등록 */
//        mListView.setAdapter(mMyAdapter);
//    }
    private FriendSelectAdapter dataSetting2(){
        FriendSelectAdapter mMyAdapter = new FriendSelectAdapter();
        for(int i = 0; i<5;++i){
            User user = new User("name", "email", "uid", "token", "pw");
            friendlist.add(user);
        }
        mMyAdapter.addItem(friendlist);
        return mMyAdapter;
    }

}