package com.example.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.GONE;

public class ShoutboardActivity extends AppCompatActivity {

    ListView listView;
    private PostArrayAdapter postAdapter;
    ArrayList<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoutboard);

        listView = findViewById(R.id.post_list);

        /*
        Post post1 = new Post(2, "Haista paska", "https://i0.wp.com/roomescapeartist.com/wp-content/uploads/2016/05/foolish-cat-wallpaper.jpg?resize=500%2C380&ssl=1",
                "found", 1515207522, "01", 1, "Seppo Papunen", "https://images-na.ssl-images-amazon.com/images/I/41fNXKl7olL._SY355_.jpg");
        Post post2 = new Post(1, "Jees tällane kissa löytyny rip", "https://pbs.twimg.com/profile_images/873941677114761216/dETQutCH_400x400.jpg",
                "found_dead", 1515217522, "02", 3, "Vittupää", "https://pbs.twimg.com/profile_images/1875677261/baconface_400x400.png");
        postList.add(post1);
        postList.add(post2);
        */
        postAdapter = new PostArrayAdapter(this, postList);
        listView.setAdapter(postAdapter);
        /*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("bugat", "view " + view.toString());
                return false;
            }
        });*/


        getShoutboardItems();



    }

    public void getShoutboardItems() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference shoutboardRefs = database.getReference("shoutboard").child("posts");
        shoutboardRefs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post aPost = dataSnapshot.getValue(Post.class);
                postList.add(0, aPost);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post aPost = dataSnapshot.getValue(Post.class);
                for(int i = 0; i < postList.size(); i++) {
                    if(postList.get(i).getId() == aPost.getId()) {
                        postList.remove(i);
                        postList.add(i, aPost);
                        break;
                    }
                }
                postAdapter.notifyDataSetChanged();
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
}
