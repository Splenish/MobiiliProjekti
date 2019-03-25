package com.example.test;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class ExtendedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
