package com.example.lifeisquest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private int cnt = 0;

    public void quest_reigist(Quest q){
       db.collection("Quest").document().set(q).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               Log.d("DATA_INSERT","quest 전송 되었음 ㅇㅇ");

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.d("DATA_INSERT","양식에 맞지않는 quest데이터");
           }
       });
    }
    public boolean quest_legality(Quest q){
        return true;
        // 나중에 퀘스트 빼먹은거 확인용 메서드
    }
    public void quest_Reader(ArrayList<String> quests){ //퀘스트 id를 받으면 퀘스트 세부정보를 받는것으로 수정
        ArrayList<Quest> quest_array=new ArrayList<>();
        cnt = 0;

        for (String id : quests){
            db.collection("Quest").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    cnt++;
                    if(task.isSuccessful()){
                        Quest quest = task.getResult().toObject(Quest.class);
                        quest_array.add(quest);
                    }else {
                        //실패
                    }
                    if(cnt == quests.size()){
                        //작업이 모두 완료
                    }
                }
            });
        }

        //리스너 형태라 바로 리턴값을 돌려주진 않음(리턴 보이드로 하고 모든 퀘스트에 대해서 작업이 완료될때 다음 작업으로 넘어가야함)
    }
}
