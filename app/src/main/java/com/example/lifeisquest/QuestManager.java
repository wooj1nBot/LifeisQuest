package com.example.lifeisquest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private FirebaseFirestore db;
    private FirebaseUser User;
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
    public ArrayList<Quest> quest_reader(){
        ArrayList<Quest> quest_array=new ArrayList<>();
        db.collection("Quest").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> Documents=queryDocumentSnapshots.getDocuments();
                int a=Documents.size(); // document 갯수만큼 a가져오기
                for(int i=0;i<a;i++){
                    Quest quest=new Quest();
                    quest.setID_RECEIVER((String) Documents.get(i).get("ID_RECIVER"));
                    quest.setID_GIVER((String) Documents.get(i).get("ID_GIVER"));
                    quest.setMESSAGE((String) Documents.get(i).get("MESSAGE"));
                    quest.setQUEST_TITLE((String) Documents.get(i).get("QUEST_TABLE"));
                    quest.setQUEST_DIFFICULTY(Integer.parseInt((String) Documents.get(i).get("QUEST_DIFFICULTY")));
                    quest.setQUEST_RECEIVED_TIME(Integer.parseInt((String) Documents.get(i).get("QUEST_RECIVED_TIME")));
                    quest.setQUEST_SEND_TIME(Integer.parseInt((String) Documents.get(i).get("QUEST_SPENTED_TIME")));
                    quest.setQUEST_SUCCESS(Boolean.valueOf((String) Documents.get(i).get("QUEST_SUCCESS")));
                    quest.setQUEST_ACCEPT(Boolean.valueOf((String) Documents.get(i).get("QUEST_ACCEPT")));
                    quest_array.add(quest);
                }
            }
        });
        return quest_array;
    }
}
