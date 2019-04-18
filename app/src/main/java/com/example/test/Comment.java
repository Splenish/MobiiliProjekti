package com.example.test;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    String user_id;
    String message;
    long time_stamp;
    String user_name;
    String profile_pic;
    String post_id;

    public Comment() {

    }

    public Comment(String _user_id, String _message, long _time_stamp, String _user_name, String _profile_pic, String _post_id) {
        this.user_id = _user_id;
        this.message = _message;
        this.time_stamp = _time_stamp;
        this.user_name = _user_name;
        this.profile_pic = _profile_pic;
        this.post_id = _post_id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("time_stamp", time_stamp);
        result.put("user_id", user_id);
        result.put("user_name", user_name);
        result.put("profile_pic", profile_pic);
        result.put("post_id", post_id);
        return result;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
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

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
