package com.example.test;

import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Trap> ownedTraps = new ArrayList<>();
    ArrayList<Trap> trapList = new ArrayList<>();
    String currentUser = "01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("traps");

        //myRef.setValue("Hello, World!");

        Button pushDataButton = findViewById(R.id.the_button);
        pushDataButton.setOnClickListener(new View.OnClickListener() {
            EditText nameText = findViewById(R.id.name_edit);
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.the_button) {
                    DatabaseReference objRef = myRef.child("01");
                    objRef.child("triggered").setValue(false);
                }
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Log.d("FB", "owner get value: " + dataSnapshot.child("owner").getValue());
                //Trap aTrap = dataSnapshot.getValue(Trap.class);
                //Log.d("FB", "Trap methronds get owner: " + aTrap.getOwner());
                //if(aTrap.getOwner() == currentUser) {
                //    ownedTraps.add(aTrap);
                //}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Log.d("FB", "on changed: " + dataSnapshot.getKey());
                //Log.d("FB", "on changed: " + dataSnapshot.getValue());
                //Log.d("FB", "The updated trap triggered is: " + dataSnapshot.child("triggered"));
                Log.d("FB", "" + dataSnapshot.getKey().getClass());
                if(dataSnapshot.getKey().equals("01")) {
                    //Log.d("FB", "on added: " + dataSnapshot.getKey());
                    Log.d("FB", "Loukkusi '01' on lauennut");
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
    configureToTrapList();
    }



    private void configureToTrapList() {
        Button ToTrapList = (Button) findViewById(R.id.ToTrapList);
        ToTrapList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrapList.class));
            }
        });
    }
}
