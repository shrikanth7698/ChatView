package com.shrikanthravi.chatviewlibrary;

import android.net.Uri;

import java.util.List;

/**
 * Created by shrikanthravi on 16/02/18.
 */


public class Message {

    long id;
    String type;
    String body;
    String time;
    String status;
    List<Uri> imageList;


    public Message(String type, String body, String time) {
        this.type = type;
        this.body = body;
        this.time = time;
    }

    public Message(String type, String body, String time, String status) {
        this.type = type;
        this.body = body;
        this.time = time;
        this.status = status;
    }

    public Message(String type, String body, String time, List<Uri> imageList) {
        this.type = type;
        this.body = body;
        this.time = time;
        this.imageList = imageList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Uri> getImageList() {
        return imageList;
    }

    public void setImageList(List<Uri> imageList) {
        this.imageList = imageList;
    }
}

