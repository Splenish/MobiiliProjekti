package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.test.ExtendedApplication.MY_PREFS_NAME;

public class PostCommentsActivity extends AppCompatActivity {

    ListView listView;
    private CommentArrayAdapter commentAdapter;
    ArrayList<Comment> commentList = new ArrayList<>();
    TextView no_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);


        ImageButton post_comment_button = findViewById(R.id.post_button);

        no_comments = findViewById(R.id.no_comments_text);

        listView = findViewById(R.id.comment_list);

        commentAdapter = new CommentArrayAdapter(this, commentList);
        listView.setAdapter(commentAdapter);

        getComments();

        post_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment_field = findViewById(R.id.post_comment_field);
                String comment_message = comment_field.getText().toString();
                SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
                String user_id = prefs.getString("uId", null);
                String user_name = prefs.getString("name", null);
                String profile_pic = prefs.getString("profile_pic", null);
                final Post aPost = (Post) getIntent().getSerializableExtra("passedPost");
                Log.d("POST_COMMENT", user_id);

                final Comment newComment = new Comment(user_id, comment_message, System.currentTimeMillis(), user_name,
                        profile_pic, aPost.id);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("shoutboard").child("comments").child(aPost.id).push();
                myRef.setValue(newComment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseDatabase.getInstance().getReference("shoutboard").child("posts").child(aPost.getId()).child("number_of_comments").setValue(aPost.getNumber_of_comments() + 1);
                    }
                });

            }
        });
    }

    public void getComments() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final Post aPost = (Post) getIntent().getSerializableExtra("passedPost");
        final DatabaseReference shoutboardRefs = database.getReference("shoutboard").child("comments").child(aPost.id);
        shoutboardRefs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comment aComment = dataSnapshot.getValue(Comment.class);
                Log.d("KOMMENTTI", "post message: " + aPost.message);
                Log.d("KOMMENTTI", "comment added listener");
                if(aComment.getPost_id().equals(aPost.id)) {
                    if(no_comments.getVisibility() == View.VISIBLE) {
                        no_comments.setVisibility(View.INVISIBLE);
                    }
                    Log.d("KOMMENTTI", "pitäs tunkee paskaa");
                    Log.d("KOMMENTTI",  "comment message" + aComment.getMessage());
                    Log.d("KOMMENTTI",  "comment username" + aComment.getUser_name());
                    commentList.add(0, aComment);
                    commentAdapter.notifyDataSetChanged();
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

}
