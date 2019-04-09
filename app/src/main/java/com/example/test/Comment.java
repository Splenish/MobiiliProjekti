package com.example.test;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    String user_id;
    String message;
    int time_stamp;

    public Comment(String _user_id, String _message, int _time_stamp) {
        this.user_id = _user_id;
        this.message = _message;
        this.time_stamp = _time_stamp;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("time_stamp", time_stamp);
        result.put("user_id", user_id);
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

    public int getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(int time_stamp) {
        this.time_stamp = time_stamp;
    }
}
