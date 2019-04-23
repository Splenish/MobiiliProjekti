package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;


import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsHelper {

    public void userToPrefs(Context context, String name, String profile_pic, String email, String uId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("profile_pic", profile_pic);
        editor.putString("email", email);
        editor.putString("uId", uId);
        editor.apply();
    }

    public void clearUserFromPrefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", MODE_PRIVATE);
        preferences.edit().remove("name").apply();
        preferences.edit().remove("email").apply();
        preferences.edit().remove("profile_pic").apply();
        preferences.edit().remove("uId").apply();
    }

    public void saveListToPrefs(ArrayList<Trap> _list, Context context) {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<List<Trap>>(){}.getType();
        String strObject = gson.toJson(_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences  myPrefs = context.getSharedPreferences("traplist", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString("list", strObject);
        prefsEditor.apply();
    }

    public ArrayList<Trap> getListFromPrefs(Context context) {
        Type listOfObjects = new TypeToken<List<Trap>>(){}.getType();
        Gson gson = new Gson();
        SharedPreferences prefs = context.getSharedPreferences("traplist", MODE_PRIVATE);
        String json = prefs.getString("list", "");
        List<Trap> list2 = gson.fromJson(json, listOfObjects);
        ArrayList<Trap> traplist = new ArrayList<>(list2.size());
        traplist.addAll(list2);
        return  traplist;
    }
}
