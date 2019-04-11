package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GuideList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);

        ListView listView = (ListView) findViewById(R.id.GuideList);
        ArrayList<Guide> GuideList = new ArrayList<>();
        GuideList.add(new Guide(R.drawable.ic_menu_send, "Funkadelic" , "meow meow meow, purr purr purr, guide my whiskers pet my fur"));
        GuideList.add(new Guide(R.drawable.ic_menu_send, " " , "The cat is on the prowl, the dogs are coming out"));
        GuideList.add(new Guide(R.drawable.ic_menu_send, " " , "Baby they chase me, up and down my street, as soon as I slow down," +
                                                                        " another comes around trying to fetch me, like am his treat"));
        GuideList.add(new Guide(R.drawable.ic_menu_send, " " , "The booty is on the duty, for me, scatch my back, I'll scratch yours."));

        GuideArrayAdapter guideAdapter = new GuideArrayAdapter(this, GuideList);
        listView.setAdapter(guideAdapter);

    }
}
