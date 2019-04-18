package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class GuideList extends Nav_MainToolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);

        ListView listView = (ListView) findViewById(R.id.GuideList);
        ArrayList<Guide> GuideList = new ArrayList<>();
        GuideList.add(new Guide(R.drawable.lily_340_235, "Loukuttamisen Toimintaohjeet" , "meow meow meow, purr purr purr, guide my whiskers pet my fur"));

        GuideList.add(new Guide(R.drawable.lily2_340_235, "Loukun Viritysohjeet" , "The cat is on the prowl, the dogs are coming out"));
        GuideList.add(new Guide(R.drawable.lily3_340_235, "Tutustu The Cube-älylaitteeseen" , "Baby they chase me, up and down my street, as soon as I slow down, another comes around trying to fetch me, like am his treat"));
        GuideList.add(new Guide(R.drawable.lily4_340_235, "Miksi kissoja loukutetaan?" , "The booty is on the duty, for me, scatch my back, I'll scratch yours."));
        GuideList.add(new Guide(R.drawable.lily5_340_235, "Talteenotetut eläimet" , "Eat ass"));
        GuideList.add(new Guide(R.drawable.lily6_340_235, "Villiintynyt kissa vai kesy lemmikki?" , "Ping pong ching chong taekwondo ying yang"));

        GuideArrayAdapter guideAdapter = new GuideArrayAdapter(this, GuideList);
        listView.setAdapter(guideAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent Intent = new Intent(getBaseContext(), Guide_Activity1.class);
                    startActivity(Intent);
                }
                else if (position == 1) {
                    Intent Intent = new Intent(getBaseContext(), Guide_Activity2.class);
                    startActivity(Intent);
                }
                else if (position == 2) {
                    Intent Intent = new Intent(getBaseContext(), Guide_Activity3.class);
                    startActivity(Intent);
                }
                else if (position == 3) {
                    Intent Intent = new Intent(getBaseContext(), Guide_Activity4.class);
                    startActivity(Intent);
                }
                else if (position == 4) {
                    Intent Intent = new Intent(getBaseContext(), Guide_Activity5.class);
                    startActivity(Intent);
                }
                else if (position == 5) {
                    Intent Intent = new Intent(getBaseContext(), Guide_Activity6.class);
                    startActivity(Intent);
                }
            }
        });

    }
}
