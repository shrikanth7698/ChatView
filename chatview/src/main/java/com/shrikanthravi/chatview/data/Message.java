package com.shrikanthravi.chatview.data;


import android.net.Uri;

import java.util.List;

/**
 * Created by shrikanthravi on 16/02/18.
 */


public class Message {


    public static String LeftSimpleMessage = "LEFT";
    public static String RightSimpleMessage = "RIGHT";
    public static String LeftSingleImage = "LeftImage";
    public static String RightSingleImage = "RightImage";

    //Can hold upto 11 images.
    public static String LeftMultipleImages = "LeftImages";
    public static String RightMultipleImages = "RightImages";

    //Single Video
    public static String LeftVideo = "LeftVideo";
    public static String RightVideo = "RightVideo";

    protected long id;
    protected String type;
    protected String body;
    protected String time;
    protected String status;
    protected List<Uri> imageList;
    protected String userName;
    protected Uri userIcon;
    protected Uri videoUri;

    public Message(){

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Uri getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Uri userIcon) {
        this.userIcon = userIcon;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }
}


