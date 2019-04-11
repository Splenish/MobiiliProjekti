package com.example.test;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GuideArrayAdapter extends ArrayAdapter<Guide> {

    private Context GuideContext;
    private List<Guide> guideList = new ArrayList<>();

    GuideArrayAdapter(@NonNull Context context, ArrayList<Guide> list) {
        super(context, 0 , list);
        GuideContext = context;
        guideList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(GuideContext).inflate(R.layout.guidelist_item,parent,false);

        Guide currentGuide = guideList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_icon);
        image.setImageResource(currentGuide.getImg());

        TextView title = (TextView) listItem.findViewById(R.id.textView_title);
        title.setText(currentGuide.getTitle());

        TextView text = (TextView) listItem.findViewById(R.id.textView_text);
        text.setText(currentGuide.gettext());

        return listItem;
    }
}
