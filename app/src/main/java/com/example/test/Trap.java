package com.example.test;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Trap implements Serializable{

    String pos;
    String owner;
    Boolean triggered;
    String trapID;
    String urlString;


    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public String getTrapID() {
        return trapID;
    }

    public void setTrapID(String trapID) {
        this.trapID = trapID;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTriggered(Boolean triggered) {
        this.triggered = triggered;
    }

    private Trap() {

    }

    public String getOwner() {
        return owner;
    }


    public Boolean getTriggered() {
        return triggered;
    }

    public Trap(String _owner, String _pos, boolean _triggered, String _trapID, String urlString_) {
        this.owner = _owner;
        this.pos = _pos;
        this.triggered = _triggered;
        this.trapID = _trapID;
        this.urlString = urlString_;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("owner", owner);
        result.put("pos", pos);
        result.put("triggered", triggered);
        result.put("trapID", trapID);
        result.put("urlString", urlString);
        return result;
    }

}
