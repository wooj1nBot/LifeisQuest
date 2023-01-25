package com.example.lifeisquest;

public class Quest {

    private static final int QUEST_STATE_WAIT = 0;
    private static final int QUEST_STATE_ACCEPT = 1;
    private static final int QUEST_STATE_DENY = 2;
    private static final int QUEST_STATE_SUCCESS = 3;
    private static final int QUEST_STATE_FAIL = 4;

    private String ID_GIVER; // 퀘스트 주는 사람 ID
    private String ID_RECEIVER; // 퀘스트 받는 사람 ID
    private String MESSAGE; // 퀘스트 요청내용
    private String QUEST_TITLE; // 퀘스트 제목
    private String QUEST_REWARD_TEXT; //퀘스트 보상(텍스트)
    private String QUEST_REWARD_IMAGE; //퀘스트 보상(이미지)
    private int QUEST_STATE = QUEST_STATE_WAIT; // 퀘스트 상태
    private int QUEST_DIFFICULTY; // 퀘스트 난이도
    private long QUEST_SEND_TIME; // 퀘스트 보낸 시간
    private long QUEST_DEADLINE; // 퀘스트 받은 시간
    private double[] QUEST_LOCATION;
    // 좌표 파일 형식 받아서 겟셋 하고 해야함 ㅇㅇ
    public Quest(){}
    public Quest(String ID_GIVER, String ID_RECEIVER, String MESSAGE, String QUEST_TITLE,
                 int QUEST_DIFFICULTY, long QUEST_SEND_TIME, long QUEST_DEADLINE, String QUEST_REWARD_TEXT){
        this.ID_GIVER=ID_GIVER;
        this.ID_RECEIVER = ID_RECEIVER;
        this.MESSAGE=MESSAGE;
        this.QUEST_TITLE=QUEST_TITLE;
        this.QUEST_DIFFICULTY=QUEST_DIFFICULTY;
        this.QUEST_SEND_TIME = QUEST_SEND_TIME;
        this.QUEST_DEADLINE = QUEST_DEADLINE;
        this.QUEST_REWARD_TEXT = QUEST_REWARD_TEXT;
    }





    public int getQUEST_DIFFICULTY() {
        return QUEST_DIFFICULTY;
    }


    public int getQUEST_STATE() {
        return QUEST_STATE;
    }

    public long getQUEST_DEADLINE() {
        return QUEST_DEADLINE;
    }

    public long getQUEST_SEND_TIME() {
        return QUEST_SEND_TIME;
    }

    public String getQUEST_REWARD_IMAGE() {
        return QUEST_REWARD_IMAGE;
    }

    public String getQUEST_REWARD_TEXT() {
        return QUEST_REWARD_TEXT;
    }

    public void setQUEST_SEND_TIME(long QUEST_SEND_TIME) {
        this.QUEST_SEND_TIME = QUEST_SEND_TIME;
    }

    public void setQUEST_DEADLINE(long QUEST_DEADLINE) {
        this.QUEST_DEADLINE = QUEST_DEADLINE;
    }

    public void setQUEST_REWARD_IMAGE(String QUEST_REWARD_IMAGE) {
        this.QUEST_REWARD_IMAGE = QUEST_REWARD_IMAGE;
    }

    public void setQUEST_LOCATION(double[] QUEST_LOCATION) {
        this.QUEST_LOCATION = QUEST_LOCATION;
    }

    public void setQUEST_REWARD_TEXT(String QUEST_REWARD_TEXT) {
        this.QUEST_REWARD_TEXT = QUEST_REWARD_TEXT;
    }

    public void setQUEST_STATE(int QUEST_STATE) {
        this.QUEST_STATE = QUEST_STATE;
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


    public void setQUEST_DIFFICULTY(int QUEST_DIFFICULTY) {
        this.QUEST_DIFFICULTY = QUEST_DIFFICULTY;
    }


    public void setQUEST_SEND_TIME(int QUEST_SEND_TIME) {
        this.QUEST_SEND_TIME = QUEST_SEND_TIME;
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
