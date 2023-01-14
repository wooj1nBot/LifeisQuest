package com.example.lifeisquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectFriend extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<User> friendlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        mListView = (ListView) findViewById(R.id.listView);

        /* 아이템 추가 및 어댑터 등록 */
        dataSetting();
    }

    private void dataSetting(){

        FriendSelectAdapter mMyAdapter = new FriendSelectAdapter();

        mMyAdapter.addItem(friendlist);//임시로 동그라미 넣음


        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }

}