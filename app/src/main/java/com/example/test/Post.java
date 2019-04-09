package com.example.test;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post implements Serializable {
    int likes;
    String message;
    String picture;
    String post_type;
    int time_stamp;
    String user_id;
    int number_of_comments;
    String user_name;
    String profile_pic;

    public Post(int _likes, String _message, String _picture, String _post_type,
                int _time_stamp, String _user_id, int _number_of_comments,
                String _user_name, String _profile_pic) {
        this.likes = _likes;
        this.message = _message;
        this.picture = _picture;
        this.post_type = _post_type;
        this.time_stamp = _time_stamp;
        this.user_id = _user_id;
        this.number_of_comments = _number_of_comments;
        this.user_name = _user_name;
        this.profile_pic = _profile_pic;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("likes", likes);
        result.put("message", message);
        result.put("picture", picture);
        result.put("post_type", post_type);
        result.put("time_stamp", time_stamp);
        result.put("user_id", user_id);
        result.put("number_of_comments", number_of_comments);
        result.put("user_name", user_name);
        result.put("profile_pic", profile_pic);
        return result;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public int getNumber_of_comments() {
        return number_of_comments;
    }

    public void setNumber_of_comments(int number_of_comments) {
        this.number_of_comments = number_of_comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public int getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(int time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
