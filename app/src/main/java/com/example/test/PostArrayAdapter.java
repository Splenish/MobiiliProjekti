package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostArrayAdapter extends ArrayAdapter<Post> {

    private Context postContext;
    private ArrayList<Post> postList = new ArrayList<>();

    public PostArrayAdapter(@NonNull Context context, ArrayList<Post> list) {
        super(context, 0 , list);
        postContext = context;
        postList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(postContext).inflate(R.layout.shoutboard_list_item,parent,false);

        final Post currentPost = postList.get(position);

        TextView timeText = (TextView) listItem.findViewById(R.id.time_stamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentPost.getTime_stamp());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String minute_string = "";
        if(minute < 10) {
            minute_string = "0" + minute;
        } else {
            minute_string = minute + "";
        }
        String time_stamp = hour + ":" + minute_string
                + " " + calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH);
        timeText.setText(time_stamp);

        ImageView image = (ImageView)listItem.findViewById(R.id.post_pic);
        Picasso.get().load(currentPost.getPicture()).into(image);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postRef = db.getReference("shoutboard").child("posts");

        TextView typeText = listItem.findViewById(R.id.type);
        typeText.setText(currentPost.getPost_type().toUpperCase());


        TextView number_of_likes = listItem.findViewById(R.id.number_of_likes);
        number_of_likes.setText(currentPost.getLikes() + " Likes");
        number_of_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Log.d("bugat", "like klik");
                FirebaseDatabase.getInstance().getReference("shoutboard").child("posts").child(currentPost.getId()).child("likes").setValue(currentPost.getLikes() + 1);
            }
        });


        TextView number_of_comments = listItem.findViewById(R.id.number_of_comments);
        number_of_comments.setText(currentPost.getNumber_of_comments() + " Comments");
        number_of_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bugat", "komment klik");
                Intent intent = new Intent(postContext, PostCommentsActivity.class);
                intent.putExtra("passedPost", currentPost);
                postContext.startActivity(intent);
            }
        });

        TextView message = listItem.findViewById(R.id.post_body);
        message.setText(currentPost.getMessage());

        TextView profile_name = listItem.findViewById(R.id.poster);
        profile_name.setText(currentPost.getUser_name());

        ImageView profile_pic = (ImageView)listItem.findViewById(R.id.profile_pic);
        Picasso.get().load(currentPost.getProfile_pic()).into(profile_pic);

        return listItem;
    }
}
