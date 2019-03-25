package com.example.test;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Trap {

    float lat;
    float lng;
    int owner;
    Boolean triggered;

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setTriggered(Boolean triggered) {
        this.triggered = triggered;
    }

    private Trap() {

    }

    public int getOwner() {
        return owner;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public Boolean getTriggered() {
        return triggered;
    }

    public Trap(int _owner, float _lat, float _lng, boolean _triggered) {
        this.owner = _owner;
        this.lat = _lat;
        this.lng = _lng;
        this.triggered = _triggered;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("owner", owner);
        result.put("lat", lat);
        result.put("lng", lng);
        result.put("triggered", triggered);

        return result;
    }

}
