package com.example.lifeisquest;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    String name;
    String email;
    String uid;
    String profile;
    String phone;
    String token;

    public User() {}

    public User(String name, String email, String uid, String profile, String phone, String token){
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.profile = profile;
        this.phone = phone;
        this.token = token;
    }

    public String getPhone() {
        return phone;
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
