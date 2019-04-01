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


public class TrapArrayAdapter extends ArrayAdapter<TrapArrayData> {

    private Context trapContext;
    private List<TrapArrayData> TrapList2 = new ArrayList<>();

    public TrapArrayAdapter(@NonNull Context context, ArrayList<TrapArrayData> list) {
        super(context, 0 , list);
        trapContext = context;
        TrapList2 = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(trapContext).inflate(R.layout.traplist_item,parent,false);

        TrapArrayData currentTrap = TrapList2.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_icon);
        image.setImageResource(currentTrap.getmImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentTrap.gettrapName());

        TextView state = (TextView) listItem.findViewById(R.id.textView_release);
        state.setText(currentTrap.gettrapState());

        return listItem;
    }
}
