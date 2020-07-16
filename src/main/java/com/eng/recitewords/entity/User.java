package com.eng.recitewords.entity;

public class User {

    private String userId;
    private String userName;
    private String userTel;
    private String userEmail;
    private String userPassword;
    private String userLevel;
    private int userTarget;
    private int userTh;
    private int userTw;
    private String basics;

    public String getBasics() {
        return basics;
    }

    public void setBasics(String basics) {
        this.basics = basics;
    }

    public int getUserLast() {
        return userLast;
    }

    public void setUserLast(int userLast) {
        this.userLast = userLast;
    }

    private int userLast;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(int userTarget) {
        this.userTarget = userTarget;
    }

    public int getUserTh() {
        return userTh;
    }

    public void setUserTh(int userTh) {
        this.userTh = userTh;
    }

    public int getUserTw() {
        return userTw;
    }

    public void setUserTw(int userTw) {
        this.userTw = userTw;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", userTarget=" + userTarget +
                ", userTh=" + userTh +
                ", userTw=" + userTw +
                '}';
    }
}
