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
import java.util.Calendar;
import java.util.Date;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    private Context commentContext;
    private ArrayList<Comment> commentList = new ArrayList<>();

    public CommentArrayAdapter(@NonNull Context context, ArrayList<Comment> list) {
        super(context, 0 , list);
        commentContext = context;
        commentList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(commentContext).inflate(R.layout.comment_list_item,parent,false);

        final Comment currentComment = commentList.get(position);

        TextView commenter = listItem.findViewById(R.id.commenter_user_name);
        commenter.setText(currentComment.getUser_name());

        TextView message = listItem.findViewById(R.id.comment_message);
        message.setText(currentComment.getMessage());

        TextView timeText = listItem.findViewById(R.id.comment_time_stamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentComment.getTime_stamp());
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

        ImageView image = listItem.findViewById(R.id.comment_profile_pic);
        Picasso.get().load(currentComment.getProfile_pic()).into(image);


    return listItem;
    }
}
