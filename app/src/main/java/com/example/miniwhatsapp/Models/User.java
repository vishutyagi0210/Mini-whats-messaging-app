package com.example.miniwhatsapp.Models;

public class User{
    String profilePicture;
    String userName;
    String password;
    String mail;
    String userId;
    String lastMessage;


    public User(String profilePicture , String userName , String password , String mail , String userId , String lastMessage){
        this.lastMessage = lastMessage;
        this.userName = userName;
        this.userId = userId;
        this.mail = mail;
        this.password = password;
    }

    public User(String userName , String password , String mail){
        this.userName = userName;
        this.password = password;
        this.mail = mail;
    }

    public User(){}

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
