package com.example.test;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    String name;
    String email;
    String profile_pic;

    public User(String _name, String _email, String _profile_pic) {
        this.name = _name;
        this.email = _email;
        this.profile_pic = _profile_pic;
    }

    public  User() {

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("profile_pic", profile_pic);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
