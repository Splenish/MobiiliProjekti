package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.common.reflect.TypeToken;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Trap> trapList = new ArrayList<>();
    String currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onResume() {
        getOwnedTraps();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView logoutButton = findViewById(R.id.cattus);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPrefsHelper helper = new SharedPrefsHelper();
                helper.clearUserFromPrefs(getBaseContext());
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String profile_pic = prefs.getString("profile_pic", null);
        String email = prefs.getString("email", null);
        currentUser = prefs.getString("uId", null);

        Log.d("SHOUTBOARD", "name: " + name);
        Log.d("SHOUTBOARD", "pic: " + profile_pic);
        Log.d("SHOUTBOARD", "email: " + email);
        Log.d("SHOUTBOARD", "currentser: " + currentUser);

        // configureToTrapList();

        //Log.d("SERVIISI2", "mainactivity on create");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("traps");


        /*Button pushDataButton = findViewById(R.id.the_button);
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



        Button buttonMainMap = findViewById(R.id.button_main_map);
        buttonMainMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Trap trap :trapList){
                    Log.d("trapPosTest",trap.getPos());
                }
                if(trapList.size() != 0) {
                    Intent intentMap = new Intent(getBaseContext(), MainMapActivity.class);
                    intentMap.putExtra("trapListPassedToMapIntent", trapList);
                    startActivity(intentMap);
                }
                else {
                    Toast.makeText(MainActivity.this, "Initializing traplist", Toast.LENGTH_SHORT).show();
                }
            }
        });



        configureToTrapList();
        configureToToolbar();
        configureGuides();

        Button shoutboard_button = findViewById(R.id.shoutboard_button);
        shoutboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ShoutboardActivity.class);
                startActivity(intent);
            }
        });*/



        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        BottomNavigationView bottomvan = findViewById(R.id.Bottom_Navigation);
        bottomvan.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                if (menuItem.getItemId() == R.id.Bottom_Map){
                    for(Trap trap :trapList){
                        Log.d("trapPosTest",trap.getPos());
                    }
                    if(trapList.size() != 0) {
                        Intent intentMap = new Intent(getBaseContext(), MainMapActivity.class);
                        intentMap.putExtra("trapListPassedToMapIntent", trapList);
                        startActivity(intentMap);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Initializing traplist", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Traps){
                    Intent intent = new Intent(getBaseContext(), TrapList.class);
                    intent.putExtra("trapListPassedToIntent", trapList);
                    //Log.d("SERVIISI3","Intenttii menevä listan size: " + trapList.size());
                    startActivity(intent);
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Post){
                    Intent intent = new Intent(getBaseContext(), NewPostActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Guides){
                    Intent intent = new Intent(getBaseContext(), GuideList.class);
                    startActivity(intent);
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Shout){
                    Intent intent = new Intent(getBaseContext(), ShoutboardActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        //------------------------------------------------shitfuck------------------------------------
        //bottomvan.setSelectedItemId(R.id.Bottom_Post);
    }



    /*
    private void configureToToolbar() {
        Button ToToolbar = (Button) findViewById(R.id.ToToolbar);
        ToToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Nav_MainToolbar.class);
                startActivity(intent);
            }
        });
    }
    private void configureGuides() {
        Button Guides = (Button) findViewById(R.id.Guides);
        Guides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuideList.class);
                startActivity(intent);
            }
        });
    }
    */

    public void getOwnedTraps() {

        trapList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference trapRefs = database.getReference("traps");
        trapRefs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trap aTrap = dataSnapshot.getValue(Trap.class);
                //Log.d("MAPPIA", "datasnapshot url " + dataSnapshot.child("urlString").getValue().toString());
                if(aTrap.getOwner().equals(currentUser)) {
                    Log.d("TURPAAN", "trappia perseese: " + aTrap.getTrapID());
                    trapList.add(aTrap);
                    SharedPrefsHelper helper = new SharedPrefsHelper();

                    helper.saveListToPrefs(trapList, getBaseContext());
                    //Log.d("MAPPIA", "trap added to list url: " + aTrap.getUrlString());
                    //Log.d("PASKA", "size= " + trapList.size());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trap aTrap = dataSnapshot.getValue(Trap.class);
                //Log.d("MAPPIA", "jorge bls");
                for(int i = 0; i < trapList.size(); i++) {
                    if(trapList.get(i).getTrapID().equals(aTrap.getTrapID())) {
                        trapList.remove(i);
                        trapList.add(i, aTrap);
                        SharedPrefsHelper helper = new SharedPrefsHelper();

                        helper.saveListToPrefs(trapList, getBaseContext());
                    }
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
    }



    /*
    private void configureToTrapList() {
        Button ToTrapList = (Button) findViewById(R.id.ToTrapList);
        ToTrapList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrapList.class);
                intent.putExtra("trapListPassedToIntent", trapList);
                //Log.d("SERVIISI3","Intenttii menevä listan size: " + trapList.size());
                startActivity(intent);
            }
        });

    }*/

}
