package com.example.test;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ExtendedApplication extends Application {

    ArrayList<Trap> ownedTraps = new ArrayList<>();
    String currentUser = "01";
    static boolean IS_APP_STARTUP = true;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        createNotificationChannel();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("traps");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Trap aTrap = dataSnapshot.getValue(Trap.class);
                //Log.d("SERVIISI", "aTrap owner get value: " + aTrap.getOwner() + " " +  currentUser);

                if (aTrap.getOwner().equals(currentUser)) {
                    Log.d("SERVIISI2", "mennään serviceen");
                    Log.d("SERVICETEST", "servicee menevä trap ID " + aTrap.getTrapID());
                    startServiceIntent(aTrap);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Log.d("TIME", "Create notification channel");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "triggered_channel";
            String description = "channel for trigger notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("TRIGGER", "triggerChannel", importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d("TIME", "Channel created");
        }
    }

    public void startServiceIntent(Trap aTrap) {
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("passedTrap", aTrap);
        startService(serviceIntent);
        //Log.d("SERVIISI", "paska");
    }

}
