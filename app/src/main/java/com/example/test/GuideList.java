package com.example.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GuideList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);

        ListView listView = (ListView) findViewById(R.id.GuideList);
        ArrayList<Guide> GuideList = new ArrayList<>();
        GuideList.add(new Guide(R.drawable.lily_340_235, "Loukuttamisen Toimintaohjeet" , "Yleisohjeet kissojen loukutuseen"));

        GuideList.add(new Guide(R.drawable.lily2_340_235, "Loukun Viritysohjeet" , "Ohjeet askeleittain loukun viritykseen"));
        GuideList.add(new Guide(R.drawable.lily3_340_235, "Tutustu The Cube-älylaitteeseen" , "The Cube tekee auttaa sekä loukutettuja kissoja, että loukuttajaa"));
        GuideList.add(new Guide(R.drawable.lily4_340_235, "Miksi kissoja loukutetaan?" , "Suomessa hylätään vuodessa 20 000 kissaa"));
        GuideList.add(new Guide(R.drawable.lily5_340_235, "Talteenotetut eläimet" , "Mitä talteenotetulla eläimelle tehdään?"));
        GuideList.add(new Guide(R.drawable.lily6_340_235, "Villiintynyt kissa vai kesy lemmikki?" , "Luontoon hylätty tai siellä syntynyt kissa voi villiintyä"));

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        BottomNavigationView bottomvan = findViewById(R.id.Bottom_Navigation);
        bottomvan.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                SharedPrefsHelper helper = new SharedPrefsHelper();
                ArrayList<Trap> trapList = helper.getListFromPrefs(getBaseContext());
                if (menuItem.getItemId() == R.id.Bottom_Map){
                    for(Trap trap :trapList){
                        Log.d("trapPosTest",trap.getPos());
                    }
                    if(trapList.size() != 0) {
                        Intent intentMap = new Intent(getBaseContext(), MainMapActivity.class);
                        intentMap.putExtra("trapListPassedToMapIntent", trapList);
                        startActivity(intentMap);
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Initializing traplist", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Traps){
                    Intent intent = new Intent(getBaseContext(), TrapList.class);
                    intent.putExtra("trapListPassedToIntent", trapList);
                    //Log.d("SERVIISI3","Intenttii menevä listan size: " + trapList.size());
                    startActivity(intent);
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Post){
                    Intent intent = new Intent(getBaseContext(), NewPostActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Guides){
                    Intent intent = new Intent(getBaseContext(), GuideList.class);
                    startActivity(intent);
                    return true;
                }
                else if (menuItem.getItemId() == R.id.Bottom_Shout){
                    Intent intent = new Intent(getBaseContext(), ShoutboardActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }
}
