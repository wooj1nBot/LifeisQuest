package com.example.lifeisquest;

import java.util.ArrayList;

public class Quest {
    String ID_GIVER; // 퀘스트 주는 사람 ID
    String ID_RECEIVER; // 퀘스트 받는 사람 ID
    String MESSAGE; // 퀘스트 요청내용
    String QUEST_TITLE; // 퀘스트 제목
    Boolean QUEST_ACCEPT; // 퀘스트 수락 여부
    Boolean QUEST_SUCCESS; // 퀘스트 성공 여부
    int QUEST_DIFFICULTY; // 퀘스트 난이도
    int QUEST_SEND_TIME; // 퀘스트 보낸 시간
    int QUEST_RECEIVED_TIME; // 퀘스트 받은 시간
    double[] QUEST_LOCATION;
    // 좌표 파일 형식 받아서 겟셋 하고 해야함 ㅇㅇ
    public Quest(){}
    public Quest(String ID_GIVER,String ID_RECIVER,String MESSAGE,String QUEST_TITLE,
                 Boolean QUEST_ACCEPT,Boolean QUEST_SUCCESS,int QUEST_DIFFICULTY,int QUEST_RECEIVED_TIME,int QUEST_SPENTED_TIME,double[] QUEST_LOCATION){
        this.ID_GIVER=ID_GIVER;
        this.ID_RECEIVER=ID_RECIVER;
        this.MESSAGE=MESSAGE;
        this.QUEST_TITLE=QUEST_TITLE;
        this.QUEST_ACCEPT=QUEST_ACCEPT;
        this.QUEST_SUCCESS=QUEST_SUCCESS;
        this.QUEST_DIFFICULTY=QUEST_DIFFICULTY;
        this.QUEST_SEND_TIME=QUEST_SPENTED_TIME;
        this.QUEST_RECEIVED_TIME=QUEST_RECEIVED_TIME;
        this.QUEST_LOCATION=QUEST_LOCATION;
    }

    public Boolean getQUEST_ACCEPT() {
        return QUEST_ACCEPT;
    }

    public Boolean getQUEST_SUCCESS() {
        return QUEST_SUCCESS;
    }

    public int getQUEST_DIFFICULTY() {
        return QUEST_DIFFICULTY;
    }

    public int getQUEST_RECEIVED_TIME() {
        return QUEST_RECEIVED_TIME;
    }

    public int getQUEST_SEND_TIME() {
        return QUEST_SEND_TIME;
    }

    public String getID_GIVER() {
        return ID_GIVER;
    }

    public String getID_RECEIVER() {
        return ID_RECEIVER;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public String getQUEST_TITLE() {
        return QUEST_TITLE;
    }

    public double[] getQUEST_LOCATION() {
        return QUEST_LOCATION;
    }

    public double getQUEST_LOCATION_LATITUDE(){
        return QUEST_LOCATION[0];
    }

    public double getQUEST_LOCATION_LONGITUDE(){
        return QUEST_LOCATION[1];
    }

    public void setID_GIVER(String ID_GIVER) {
        this.ID_GIVER = ID_GIVER;
    }

    public void setID_RECEIVER(String ID_RECEIVER) {
        this.ID_RECEIVER = ID_RECEIVER;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public void setQUEST_ACCEPT(Boolean QUEST_ACCEPT) {
        this.QUEST_ACCEPT = QUEST_ACCEPT;
    }

    public void setQUEST_DIFFICULTY(int QUEST_DIFFICULTY) {
        this.QUEST_DIFFICULTY = QUEST_DIFFICULTY;
    }

    public void setQUEST_RECEIVED_TIME(int QUEST_RECEIVED_TIME) {
        this.QUEST_RECEIVED_TIME = QUEST_RECEIVED_TIME;
    }

    public void setQUEST_SEND_TIME(int QUEST_SEND_TIME) {
        this.QUEST_SEND_TIME = QUEST_SEND_TIME;
    }

    public void setQUEST_SUCCESS(Boolean QUEST_SUCCESS) {
        this.QUEST_SUCCESS = QUEST_SUCCESS;
    }

    public void setQUEST_TITLE(String QUEST_TITLE) {
        this.QUEST_TITLE = QUEST_TITLE;
    }

    public double[] setQUEST_LOCATION() {
        return QUEST_LOCATION;
    }

    public double setQUEST_LOCATION_LATITUDE(){
        return QUEST_LOCATION[0];
    }

    public double setQUEST_LOCATION_LONGITUDE(){
        return QUEST_LOCATION[1];
    }

}
