package com.example.lifeisquest;

public class SimpleFriend {
    String id;
    String name;
    String profile;
    String token;
    String sender;

    public SimpleFriend(){}

    public SimpleFriend(String id, String name, String profile, String token, String sender){
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.token = token;
        this.sender = sender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String getSender() {
        return sender;
    }

    public String getToken() {
        return token;
    }

    public void setNickname(String nickname) {
        this.name = nickname;
    }

    public String getId() {
        return id;
    }

}
