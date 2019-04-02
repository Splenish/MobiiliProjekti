package com.example.test;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Trap trapPassedFromIntent;
    private ValueEventListener listener;
    String posString;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Log.d("PASKAMAKE", "vittu");
        super.onCreate(savedInstanceState, persistentState);
        Log.d("PASKAMAKE", "saatana");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d("PASKAMAKE", "paskamake");
        Intent intent = getIntent();
        trapPassedFromIntent = (Trap) intent.getSerializableExtra("trapIntent");


        Log.d("PASKAMAKE", "trap pos" + trapPassedFromIntent.getPos());
        posString = trapPassedFromIntent.getPos();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference trapRefs = database.getReference("traps").child(trapPassedFromIntent.getTrapID()).child("pos");
        final DatabaseReference trapRefs = database.getReference("traps").child("01").child("pos");

        listener = trapRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("SERVIISI", "Specific trap triggered " + dataSnapshot.getValue());
                Log.d("PASKAMAKE", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("PASKAMAKE", "on map ready");
        // Add a marker in Sydney, Australia, and move the camera.

        String[] latLng = posString.split(",");

        float lat = Float.parseFloat(latLng[0]);
        float lng = Float.parseFloat(latLng[1]);

        LatLng sydney = new LatLng(lat, lng);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Log.d("PASKAMAKE", "on map ready");
    }


    @Override
    protected void onDestroy() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference trapRefs = database.getReference("traps").child(trapPassedFromIntent.getTrapID()).child("lat");
        trapRefs.removeEventListener(listener);

        super.onDestroy();
    }
}