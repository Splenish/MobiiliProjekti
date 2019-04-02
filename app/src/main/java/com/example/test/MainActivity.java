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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Trap> trapList = new ArrayList<>();
    String currentUser = "01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getOwnedTraps();
        configureToTrapList();

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

        Button mapButton = findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                //intent.putExtra("trapIntent", trapList.get(Integer.parseInt(currentUser)));

                intent.putExtra("trapIntent", trapList.get(0));
                startActivity(intent);
            }
        });

    }


    public void getOwnedTraps() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference trapRefs = database.getReference("traps");
        trapRefs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trap aTrap = dataSnapshot.getValue(Trap.class);
                if(aTrap.getOwner().equals(currentUser)) {
                    trapList.add(aTrap);
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




    private void configureToTrapList() {
        Button ToTrapList = (Button) findViewById(R.id.ToTrapList);
        ToTrapList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrapList.class);
                intent.putExtra("trapListPassedToIntent", trapList);
                Log.d("SERVIISI3","Intenttii menevä listan size: " + trapList.size());
                startActivity(intent);
            }
        });

    }

}
