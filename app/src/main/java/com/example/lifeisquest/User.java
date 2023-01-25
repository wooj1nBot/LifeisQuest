package com.example.lifeisquest;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    String name;
    String email;
    String uid;
    String profile = null;
    String password;
    String token;
    ArrayList<String> quest;
    ArrayList<String> friend;


    public User() {}

    public User(String name, String email, String uid, String token, String password){
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.password = password;
        this.token = token;
    }

    public ArrayList<String> getFriend() {
        return friend;
    }

    public ArrayList<String> getQuest() {
        return quest;
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
}
