package com.example.test;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DbHelper {
    ArrayList<Trap> ownedTraps = new ArrayList<>();




    public ArrayList<Trap> getListOfTraps(final String currentUser) {
        Log.d("SERVIISI3", "dbHelporisa");

        Log.d("SERVIISI3", "dbHelporisa2");
        return ownedTraps;
    }

}
