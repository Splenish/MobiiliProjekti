package com.example.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

        Post currentPost = postList.get(position);

        TextView timeText = (TextView) listItem.findViewById(R.id.time_stamp);
        Date time_stamp = new java.util.Date(currentPost.time_stamp);
        timeText.setText(time_stamp.toString());

        ImageView image = (ImageView)listItem.findViewById(R.id.post_pic);
        Picasso.get().load(currentPost.getPicture()).into(image);

        TextView number_of_likes = listItem.findViewById(R.id.number_of_likes);
        number_of_likes.setText(currentPost.getLikes() + " Likes");


        TextView number_of_comments = listItem.findViewById(R.id.number_of_comments);
        number_of_comments.setText(currentPost.getNumber_of_comments() + " Comments");

        TextView message = listItem.findViewById(R.id.post_body);
        message.setText(currentPost.getMessage());

        TextView profile_name = listItem.findViewById(R.id.poster);
        profile_name.setText(currentPost.getUser_name());

        ImageView profile_pic = (ImageView)listItem.findViewById(R.id.profile_pic);
        Picasso.get().load(currentPost.getProfile_pic()).into(profile_pic);

        return listItem;
    }
}
