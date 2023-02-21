package com.example.lifeisquest;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdopter extends RecyclerView.Adapter<FriendAdopter.ViewHolder> {

    Map<String, User> users;
    ArrayList<String> friends;
    ArrayList<String> add_send_friends;
    ArrayList<String> add_receive_friends;
    boolean isCheck;
    FirebaseUser user;
    FirebaseFirestore db;
    Context context;
    FriendFragment fragment;


    FriendAdopter(Map<String, User> users, ArrayList<String> friends, ArrayList<String> add_send_friends, ArrayList<String> add_receive_friends, boolean isCheck, FriendFragment fragment){
        this.users = users;
        this.friends = friends;
        this.add_send_friends = add_send_friends;
        this.add_receive_friends = add_receive_friends;
        this.isCheck = isCheck;
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public FriendAdopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.friend_list_item, parent, false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdopter.ViewHolder holder, int position) {
           User user;
           if (position < add_send_friends.size()){
                user = users.get(add_send_friends.get(position));
                if (position == 0){
                    holder.tv_header.setVisibility(View.VISIBLE);
                    holder.tv_header.setText("친구 수락 대기");
                }else {
                    holder.tv_header.setVisibility(View.GONE);
                }
                holder.fb_quest.setVisibility(View.GONE);
                holder.fb_chat.setVisibility(View.GONE);
                holder.tv_allow.setVisibility(View.VISIBLE);
           }
           else if (position - add_send_friends.size() < add_receive_friends.size()){
               int pos = position - add_send_friends.size();
               user = users.get(add_receive_friends.get(pos));
               holder.fb_chat.setImageResource(R.drawable.done_48px);
               holder.fb_quest.setImageResource(R.drawable.close_48px);
               holder.tv_allow.setVisibility(View.GONE);
               if (pos == 0){
                   holder.tv_header.setVisibility(View.VISIBLE);
                   holder.tv_header.setText("친구 수락 요청");
               }else {
                   holder.tv_header.setVisibility(View.GONE);
               }
           }
           else {
               int pos = position - add_send_friends.size() - add_receive_friends.size();
               user = users.get(friends.get(pos));
               holder.fb_chat.setImageResource(R.drawable.chat_bubble_48px);
               holder.fb_quest.setImageResource(R.drawable.history_edu_48px);
               if (pos == 0){
                   holder.tv_header.setVisibility(View.VISIBLE);
                   holder.tv_header.setText("친구");
               }else {
                   holder.tv_header.setVisibility(View.GONE);
               }
               holder.tv_allow.setVisibility(View.GONE);
           }
           holder.fb_chat.setTag(position);
           holder.fb_quest.setTag(position);
           holder.tv_name.setText(user.name);
           holder.tv_nickname.setText(user.nickname);

           if (user.profile != null){
               Glide.with(context).load(Uri.parse(user.profile)).into(holder.profile);
           }else {
               holder.profile.setImageResource(R.drawable.profile);
           }

           holder.fb_chat.setOnClickListener(new OnSingleClickListener() {
               @Override
               public void onSingleClick(View v) {
                   int position = (int) v.getTag();
                   if (position - add_send_friends.size() < add_receive_friends.size()){
                       int pos = position - add_send_friends.size();
                       addFriend(users.get(add_receive_friends.get(pos)));
                   }
                   else {
                       int pos = position - add_send_friends.size() - add_receive_friends.size();

                   }
               }
           });
            holder.fb_quest.setOnClickListener(new OnSingleClickListener() {
             @Override
                public void onSingleClick(View v) {
                    int position = (int) v.getTag();
                    if (position - add_send_friends.size() < add_receive_friends.size()){
                        int pos = position - add_send_friends.size();
                        denyFriend(users.get(add_receive_friends.get(pos)));
                    }
                    else {
                        int pos = position - add_send_friends.size() - add_receive_friends.size();

                    }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
}

    public void addFriend(User target){
        db.collection("users").document(user.getUid()).update("friend", FieldValue.arrayUnion(target.getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fragment.getData(user.getUid());
            }
        });
        db.collection("users").document(user.getUid()).update("add_receive_friends", FieldValue.arrayRemove(target.getUid()));
        db.collection("users").document(target.getUid()).update("add_send_friends", FieldValue.arrayRemove(user.getUid()));
        db.collection("users").document(target.getUid()).update("friend", FieldValue.arrayUnion(user.getUid()));
    }
    public void denyFriend(User target){
        db.collection("users").document(user.getUid()).update("add_receive_friends", FieldValue.arrayRemove(target.getUid()));
        db.collection("users").document(target.getUid()).update("add_send_friends", FieldValue.arrayRemove(user.getUid()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_header;
        TextView tv_name;
        TextView tv_nickname;
        TextView tv_allow;
        CircleImageView profile;
        FloatingActionButton fb_chat;
        FloatingActionButton fb_quest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_header = itemView.findViewById(R.id.tv_header);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_allow = itemView.findViewById(R.id.tv_allow);
            profile = itemView.findViewById(R.id.profile);
            fb_chat = itemView.findViewById(R.id.fb_chat);
            fb_quest = itemView.findViewById(R.id.fb_quest);
        }

    }
}
