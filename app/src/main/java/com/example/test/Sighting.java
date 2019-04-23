package com.example.test;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Sighting {
    private String position;
    private long time_stamp;
    private String id;

    public Sighting(){ }

    public Sighting(String id_, String position_, long time_stamp_) {
        this.position = position_;
        this.id = id_;
        this.time_stamp = time_stamp_;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("position", position);
        result.put("time_stamp", time_stamp);
        result.put("id", id);
        return result;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
