package com.example.miniwhatsapp.Models;

public class Messages {

    String Uid;
    String message;
    Long timeStem;


    public Messages(String Uid , String message , Long timeStem){
        this.Uid = Uid;
        this.message = message;
        this.timeStem = timeStem;
    }

    public Messages(String Uid , String message){
        this.Uid = Uid;
        this.message = message;
    }

    public Messages(){}


    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStem() {
        return timeStem;
    }

    public void setTimeStem(Long timeStem) {
        this.timeStem = timeStem;
    }
}
