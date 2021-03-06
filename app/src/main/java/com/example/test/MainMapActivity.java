package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainMapActivity  extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mainMap;
    private ArrayList<Trap> TrapList;
    private ArrayList<Circle> CircleList;
    Trap trapToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        Log.d("viesti","mapin oncreatessa");

        TrapList = (ArrayList<Trap>) getIntent().getSerializableExtra("trapListPassedToMapIntent");
        CircleList = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_main);
        mapFragment.getMapAsync(this);

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
                SharedPrefsHelper helper = new SharedPrefsHelper();
                ArrayList<Trap> trapList = helper.getListFromPrefs(getBaseContext());
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
                        Toast.makeText(getBaseContext(), "Initializing traplist", Toast.LENGTH_SHORT).show();
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
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trap_custom_marker, null);
        ImageView markerImageView = customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    public void getSightings(){
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference sightingReference = firebaseDatabase.getReference("sightings");
        sightingReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Sighting aSighting = dataSnapshot.getValue(Sighting.class);
                long sightingTimeStamp = aSighting.getTime_stamp();
                long timeStamp = System.currentTimeMillis();
                if(timeStamp-sightingTimeStamp < 172800000){
                    String[] latLngStrings = aSighting.getPosition().split(",");
                    LatLng latLng = new LatLng(Double.parseDouble(latLngStrings[0]), Double.parseDouble(latLngStrings[1]));
                    Log.d("viesti", latLngStrings[0] + "," + latLngStrings[1]);
                    mainMap.addMarker(new MarkerOptions().position(latLng).title("Stray cat!").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.cat_marker))));
                    mainMap.addCircle(new CircleOptions().center(latLng).radius(500).strokeColor(0x99A171AD).fillColor(0x22A171AD).strokeWidth(3));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {    }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {    }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("viesti","mapin onMapReadyssa");

        mainMap = googleMap;
        final ArrayList<Marker> markerList = new ArrayList<>();
        getSightings();
        mainMap.getUiSettings().setMapToolbarEnabled(false);

        for(Trap trap : TrapList){
            String[] latLngStrings = trap.getPos().split(",");
            try {
                LatLng latLng = new LatLng(Double.parseDouble(latLngStrings[0]),Double.parseDouble(latLngStrings[1]));
                Marker marker = mainMap.addMarker(new MarkerOptions().position(latLng).title(trap.getTrapID()).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.trap_marker))));
                markerList.add(marker);
            } catch (Exception e) {
                Log.e("MYAPP", "exception", e);
            }

        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker marker : markerList){
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        final int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);

        mainMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng position) {
                mainMap.addMarker(new MarkerOptions().position(position).title("Stray cat!").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.cat_marker))));
                mainMap.addCircle(new CircleOptions().center(position).radius(500).strokeColor(0x99A171AD).fillColor(0x22A171AD).strokeWidth(3));
                mainMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                mainMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                Long timestamp = System.currentTimeMillis();
                String stringTimestamp = timestamp.toString();
                DatabaseReference myReference = FirebaseDatabase.getInstance().getReference("sightings").push();
                String key = myReference.getKey();
                String pos = position.latitude+","+position.longitude;
                Sighting sighting = new Sighting(key,pos,timestamp);
                myReference.setValue(sighting);
                Log.d("viesti",stringTimestamp);
            }
        });
        mainMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int position = 0;
                String id = marker.getTitle();
                for (Trap trap : TrapList){
                    if (trap.getTrapID().equals(id)){
                        trapToSend = TrapList.get(position);
                        break;
                    } else{ position++; }
                }

                MapInfoWindowAdapter infoAdapter = new MapInfoWindowAdapter(MainMapActivity.this, trapToSend);
                mainMap.setInfoWindowAdapter(infoAdapter);
                float zoom = 15;mainMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), zoom));
                return false;
            }
        });
        mainMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(!marker.getTitle().equals("Stray cat!")){
                    Intent intentGoToTrap = new Intent(getBaseContext(),MapActivity.class);
                    String id = marker.getTitle();
                    int position = 0;
                    for (Trap trap : TrapList){
                        if (trap.getTrapID().equals(id)){
                            break;
                        } else{ position++; }
                    }
                    Trap trapToSend = TrapList.get(position);
                    intentGoToTrap.putExtra("trapIntent",trapToSend);
                    startActivity(intentGoToTrap);
                }
            }
        });


    }
}