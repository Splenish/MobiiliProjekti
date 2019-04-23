package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class MapInfoWindowAdapter extends MainMapActivity implements GoogleMap.InfoWindowAdapter {

    private final View infoWindow;
    private Context infoContext;
    Trap trap;
    ImageView imageView;

    public MapInfoWindowAdapter(Context context, Trap sentTrap){
        trap = sentTrap;
        infoContext = context;
        infoWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void openInfoWindow(Marker marker, View view){
        String title = marker.getTitle();
        imageView = view.findViewById(R.id.image_info_window);
        if(!title.equals("Stray cat!")){
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(trap.getUrlString()).into(imageView);
        }
        else{ imageView.setVisibility(View.GONE); }

        TextView textView = view.findViewById(R.id.text_info_window);
        textView.setText(title);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        openInfoWindow(marker,infoWindow);
        return infoWindow;
    }
}
