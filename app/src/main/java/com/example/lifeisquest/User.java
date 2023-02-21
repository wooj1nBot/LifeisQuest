package com.example.lifeisquest;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    String name;
    String nickname;
    String email;
    String uid;
    String profile = null;
    String password;
    String token;
    ArrayList<String> quest;
    ArrayList<String> friend;
    ArrayList<String> add_send_friends;
    ArrayList<String> add_receive_friends;


    public User() {}

    public User(String name, String nickname, String email, String uid, String token, String password){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.uid = uid;
        this.password = password;
        this.token = token;
    }

    public ArrayList<String> getFriend() {
        return friend;
    }

    public ArrayList<String> getAdd_receive_friends() {
        return add_receive_friends;
    }

    public ArrayList<String> getAdd_send_friends() {
        return add_send_friends;
    }

    public ArrayList<String> getQuest() {
        return quest;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAdd_receive_friends(ArrayList<String> add_receive_friends) {
        this.add_receive_friends = add_receive_friends;
    }

    public void setAdd_send_friends(ArrayList<String> add_send_friends) {
        this.add_send_friends = add_send_friends;
    }

    public void setFriend(ArrayList<String> friend) {
        this.friend = friend;
    }

    public void setQuest(ArrayList<String> quest) {
        this.quest = quest;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
