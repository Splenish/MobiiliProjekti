package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String currentUser = "01";
    ArrayList<Trap> ownedTraps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("SERVIISI2", "mainactivity on create");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("traps");


        Button pushDataButton = findViewById(R.id.the_button);
        pushDataButton.setOnClickListener(new View.OnClickListener() {
            EditText nameText = findViewById(R.id.name_edit);
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.the_button) {
                    DatabaseReference objRef = myRef.child("01");
                    objRef.child("triggered").setValue(false);
                    //objRef.setValue("triggered", false);
                }
            }
        });
        
        addTrapsToList();
        
    }

    public void addTrapsToList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("traps");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trap aTrap = dataSnapshot.getValue(Trap.class);
                Log.d("SERVIISI3", aTrap.getOwner());
                if(currentUser.equals(aTrap.getOwner())) {
                    ownedTraps.add(aTrap);
                    Log.d("SERVIISI3", aTrap.getOwner() + " trap addedded");

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


    @Override
    protected void onDestroy() {
     //   IS_APP_STARTUP = true;
        super.onDestroy();
    }
}
