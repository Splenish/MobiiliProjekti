package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("traps");
        final DatabaseReference trapRefs = database.getReference("traps").child("01").child("triggered");
        trapRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("SERVIISI", "Specific trap triggered " + dataSnapshot.getValue());
                if(dataSnapshot.getValue().toString().equals("true")) {
                    Log.d("SERVIISI", "loukkusi 01 on lauennut");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //myRef.setValue("Hello, World!");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Log.d("SERVIISI", "owner get value: " + dataSnapshot.child("owner").getValue());
                Trap aTrap = dataSnapshot.getValue(Trap.class);
                //Log.d("SERVIISI", "Trap methronds get owner: " + aTrap.getOwner());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Log.d("SERVIISI", "on changed: " + dataSnapshot.getKey());
                //Log.d("SERVIISI", "on changed: " + dataSnapshot.getValue());
                //Log.d("SERVIISI", "The updated trap triggered is: " + dataSnapshot.child("triggered"));
                //Log.d("SERVIISI", "" + dataSnapshot.getKey().getClass());
                if(dataSnapshot.getKey().equals("01")) {
                    //Log.d("FB", "on added: " + dataSnapshot.getKey());
                    //Log.d("SERVIISI", "Loukkusi '01' on lauennut");
                }

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
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVIISI", "on start command");
        return super.onStartCommand(intent, flags, startId);
    }
}
