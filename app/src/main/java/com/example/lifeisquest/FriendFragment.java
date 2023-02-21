package com.example.lifeisquest;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FriendFragment extends Fragment {

    FirebaseFirestore db;
    User userData;
    FirebaseUser user;
    RecyclerView rc;
    LottieAnimationView lottieAnimationView;
    ImageView btn_add;
    Map<String, User> userMap;
    ArrayList<String> friends;
    ArrayList<String> add_send_friends;
    ArrayList<String> add_receive_friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friend, container, false);
        rc = view.findViewById(R.id.rc);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        setSnapShotUserData(user.getUid());
        btn_add = view.findViewById(R.id.add_friend);


        return view;
    }

    public void setSnapShotUserData(String uid){
        friends = new ArrayList<>();
        add_send_friends = new ArrayList<>();
        add_receive_friends = new ArrayList<>();

        db.collection("users").document(uid).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                userData = snapshot.toObject(User.class);
                if (userData != null){
                    friends = userData.friend;
                    add_receive_friends = userData.add_receive_friends;
                    add_send_friends = userData.add_send_friends;
                    getFriendData();
                }
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAddFriendDialog();
                    }
                });
            }
        });
    }

    public void getData(String uid){
        friends = new ArrayList<>();
        add_send_friends = new ArrayList<>();
        add_receive_friends = new ArrayList<>();

        db.collection("users").document(uid).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                userData = snapshot.toObject(User.class);
                if (userData != null){
                    friends = userData.friend;
                    add_receive_friends = userData.add_receive_friends;
                    add_send_friends = userData.add_send_friends;
                    getFriendData();
                }
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAddFriendDialog();
                    }
                });
            }
        });
    }

    public void getFriendData(){
        userMap = new HashMap<>();
        if (friends == null) friends = new ArrayList<>();
        if (add_send_friends == null) add_send_friends = new ArrayList<>();
        if (add_receive_friends == null) add_receive_friends = new ArrayList<>();

        for (int i = 0; i < friends.size(); i++) {
            String id = friends.get(i);
            db.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                     if (task.isSuccessful()){
                         User user = task.getResult().toObject(User.class);
                         userMap.put(user.getUid(), user);
                         if (userMap.size() == friends.size() + add_receive_friends.size() + add_send_friends.size()){
                             showFriends();
                         }
                     }
                }
            });
        }
        for (int i = 0; i < add_send_friends.size(); i++) {
            String id = add_send_friends.get(i);
            db.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        User user = task.getResult().toObject(User.class);
                        userMap.put(user.getUid(), user);
                        if (userMap.size() == friends.size() + add_receive_friends.size() + add_send_friends.size()){
                            showFriends();
                        }
                    }
                }
            });
        }
        for (int i = 0; i < add_receive_friends.size(); i++) {
            String id = add_receive_friends.get(i);
            db.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        User user = task.getResult().toObject(User.class);
                        userMap.put(user.getUid(), user);
                        if (userMap.size() == friends.size() + add_receive_friends.size() + add_send_friends.size()){
                            showFriends();
                        }
                    }
                }
            });
        }
    }

    public void showFriends(){
        Collections.sort(friends, Comparator.comparing(s -> userMap.get(s).nickname));
        Log.d("TAG", String.valueOf(userMap.size()));
        FriendAdopter friendAdopter = new FriendAdopter(userMap, friends, add_send_friends, add_receive_friends, false, this);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        rc.setAdapter(friendAdopter);
    }


    public void showAddFriendDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.addfriends_dialog, null);
        EditText ed_name = view.findViewById(R.id.ed_name);
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(String.format("내 사용자 명: %s", userData.name));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("추가", null);
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        String targetName = ed_name.getText().toString();
                        if(targetName.length() > 0){
                            if(targetName.equals(userData.getName())){
                                ed_name.setError("스스로 친구가 될 수는 없습니다.");
                            }else {
                                if(!targetName.contains("#")){
                                    ed_name.setError("친구 코드를 포함한 사용자명을 입력해주세요.");
                                }else {
                                    addFriend(targetName, ed_name, dialog);
                                }
                            }
                        }else {
                            ed_name.setError("사용자 명을 입력해주세요.");
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void addFriend(String targetName, EditText ed_email, DialogInterface dialog){
        LoadingView loadingView = new LoadingView(getContext());
        loadingView.show("친구 추가 중...");

        db.collection("users").whereEqualTo("name", targetName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> list = task.getResult().getDocuments();
                    if(list.size() > 0){
                        User target = list.get(0).toObject(User.class);
                        if (friends.contains(target.getUid()) || add_receive_friends.contains(target.getUid()) || add_send_friends.contains(target.getUid())) {
                            loadingView.stop();
                            ed_email.setError("이미 친구 추가가 완료된 사용자명입니다.");
                            return;
                        }
                        db.collection("users").document(user.getUid()).update("add_send_friends", FieldValue.arrayUnion(target.getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    db.collection("users").document(target.getUid()).update("add_receive_friends", FieldValue.arrayUnion(user.getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getContext(), "친구 추가가 완료되었습니다.", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                            loadingView.stop();
                                        }
                                    });
                                }else {
                                    loadingView.stop();
                                }
                            }
                        });
                    }else {
                        loadingView.stop();
                        ed_email.setError("해당 사용자명을 가진 사용자가 없습니다.");
                    }
                }else {
                    loadingView.stop();
                    ed_email.setError("사용자 검색에 실패했습니다. 다시 시도해주세요.");
                }
            }
        });






    }



}