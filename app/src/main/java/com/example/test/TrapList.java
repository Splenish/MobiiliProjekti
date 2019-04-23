package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrapList extends AppCompatActivity {


    private ListView listView;
    private TrapArrayAdapter trapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_list);

        listView = (ListView) findViewById(R.id.TrapList);


        //Intent intent = getIntent();

        final ArrayList<Trap> TrapList2 = (ArrayList<Trap>) getIntent().getSerializableExtra("trapListPassedToIntent");
        //Log.d("SERVIISI3","Intentistä saatu list size: " + TrapList2.size());
        //Log.d("SERVIISI3","Paskaa: " + TrapList2.isEmpty());
        trapAdapter = new TrapArrayAdapter(this,TrapList2);

        listView.setAdapter(trapAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                intent.putExtra("trapIntent", TrapList2.get(position));
                startActivity(intent);
            }
        });

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
