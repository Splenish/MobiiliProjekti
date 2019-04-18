package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;

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
}
