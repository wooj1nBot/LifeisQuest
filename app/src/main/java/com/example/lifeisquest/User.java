package com.example.lifeisquest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    String name;
    String email;
    String uid;
    String profile;
    String token;
    ArrayList<String> quest;
    ArrayList<String> friend;




    public User() {}

    public User(String name, String email, String uid, String profile, String token){
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.profile = profile;
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

}
