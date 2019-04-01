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
