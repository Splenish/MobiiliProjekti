package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoutboardActivity extends AppCompatActivity {

    private ListView listView;
    private PostArrayAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoutboard);

        listView = findViewById(R.id.post_list);

        ArrayList<Post> postList = new ArrayList<>();
        Post post1 = new Post(2, "Haista paska", "https://i0.wp.com/roomescapeartist.com/wp-content/uploads/2016/05/foolish-cat-wallpaper.jpg?resize=500%2C380&ssl=1",
                "found", 1515207522, "01", 1, "Seppo Papunen", "https://images-na.ssl-images-amazon.com/images/I/41fNXKl7olL._SY355_.jpg");
        Post post2 = new Post(1, "Jees tällane kissa löytyny rip", "https://pbs.twimg.com/profile_images/873941677114761216/dETQutCH_400x400.jpg",
                "found_dead", 1515217522, "02", 3, "Vittupää", "https://pbs.twimg.com/profile_images/1875677261/baconface_400x400.png");
        postList.add(post1);
        postList.add(post2);

        postAdapter = new PostArrayAdapter(this, postList);
        listView.setAdapter(postAdapter);

    }
}
