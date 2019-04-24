package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Trap trapPassedFromIntent;
    private ValueEventListener listener;
    String posString;
    Marker trapMarker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TextView trapName = findViewById(R.id.teksti);
        TextView trapStateText = findViewById(R.id.trap_state_text);
        Intent intent = getIntent();



        trapPassedFromIntent = (Trap) intent.getSerializableExtra("trapIntent");
        Log.d("SERVICETEST", "trapPassedFromIntent ID " + trapPassedFromIntent.getTrapID());
        posString = trapPassedFromIntent.getPos();



        String[] latLng = posString.split(",");
        Geocoder geocoder = new Geocoder(this);

        try{
            Log.d("MAPPIA", "lat und long" + latLng[0] + "," + latLng[1]);
            List<Address> matches = geocoder.getFromLocation(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]), 1);
            Log.d("MAPPIA", "address from reverse geocode" + matches.get(0));
            Log.d("MAPPIA", "address from reverse geocode" + matches.get(0).getAddressLine(0));
            TextView address = findViewById(R.id.trap_address);
            address.setText(matches.get(0).getAddressLine(0));
        } catch (Exception e) {
            TextView address = findViewById(R.id.trap_address);
            address.setText("No location available");
            Log.d("MAPPIA", "Geocode error");
            Log.e("MAPPIA", "expection", e);

        }

        Button reset_btn = findViewById(R.id.resetButton);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("traps");
                DatabaseReference objRef = myRef.child(trapPassedFromIntent.getTrapID());
                objRef.child("triggered").setValue(false);
                Toast.makeText(MapActivity.this, "Trap reset!", Toast.LENGTH_SHORT).show();
                //objRef.setValue("triggered", false);
            }
        });

        trapName.setText("Trap " + trapPassedFromIntent.getTrapID());

        TextView user_name = findViewById(R.id.owner_text_username);
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        user_name.setText(name);

        if(trapPassedFromIntent.getTriggered()) {
            trapStateText.setText("Trap triggered");
        } else {
            trapStateText.setText("Trap not triggered");
        }


        ImageView cat_pic = findViewById(R.id.cat_pic);

        //Picasso.with(getContext()).load(getString(R.string.default_trap_pic_url)).fit().into(cat_pic);

        if(trapPassedFromIntent.getTriggered()) {
            Picasso.get().load(trapPassedFromIntent.getUrlString()).into(cat_pic);
            Log.d("MAPPIA", "url string " + trapPassedFromIntent.getUrlString());
            TextView text = findViewById(R.id.cat_announcement);
            text.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(getString(R.string.default_trap_pic_url)).into(cat_pic);
            TextView text = findViewById(R.id.cat_announcement);
            text.setVisibility(View.VISIBLE);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference trapRefs = database.getReference("traps").child(trapPassedFromIntent.getTrapID()).child("pos");
        //final DatabaseReference trapRefs = database.getReference("traps").child("01").child("pos");

        listener = trapRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("SERVIISI", "Specific trap triggered " + dataSnapshot.getValue());
                posString = dataSnapshot.getValue().toString();
                updateMarker();
                //Log.d("PASKAMAKE", "on data change" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        putMarkerToCoord();
    }

    public void putMarkerToCoord() {
        String[] latLng = posString.split(",");

        try{
            float lat = Float.parseFloat(latLng[0]);
            float lng = Float.parseFloat(latLng[1]);
            LatLng trapLocation = new LatLng(lat, lng);

            trapMarker = mMap.addMarker(new MarkerOptions().position(trapLocation).title(trapPassedFromIntent.getTrapID()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(trapLocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(trapLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        } catch (Exception e) {
            TextView address = findViewById(R.id.trap_address);
            address.setText("No location available");
        }



    }


    public void updateMarker() {
        String[] latLng = posString.split(",");

        try {
            float lat = Float.parseFloat(latLng[0]);
            float lng = Float.parseFloat(latLng[1]);

            LatLng trapLocation = new LatLng(lat, lng);

            trapMarker.setPosition(trapLocation);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(trapLocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(trapLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        } catch (Exception e) {
            TextView address = findViewById(R.id.trap_address);
            address.setText("No location available");
        }


    }





    @Override
    protected void onDestroy() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference trapRefs = database.getReference("traps").child(trapPassedFromIntent.getTrapID()).child("lat");
        trapRefs.removeEventListener(listener);

        super.onDestroy();
    }
}