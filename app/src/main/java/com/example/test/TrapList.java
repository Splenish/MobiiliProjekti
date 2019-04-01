package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TrapList extends AppCompatActivity {

    private ListView listView;
    private TrapArrayAdapter trapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_list);

        listView = (ListView) findViewById(R.id.TrapList);
        ArrayList<TrapArrayData> TrapList2 = new ArrayList<>();
        TrapList2.add(new TrapArrayData(R.drawable.ic_launcher_background, "Trap" , "triggered"));
        TrapList2.add(new TrapArrayData(R.drawable.ic_launcher_background, "Trap" , "ni cigar"));
        TrapList2.add(new TrapArrayData(R.drawable.ic_launcher_background, "Trap" , "no cigar"));

        trapAdapter = new TrapArrayAdapter(this,TrapList2);
        listView.setAdapter(trapAdapter);

        /*String[] Traps = {"Trap1", "Trap2", "Trap3", "Trap4"};

        ListAdapter TrapListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Traps);

        ListView TrapList = (ListView) findViewById(R.id.TrapList);

        TrapList.setAdapter(TrapListAdapter);
        TrapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent appInfo = new Intent(TrapList.this, TrapDetails.class);
                startActivity(appInfo);
            }
        });*/


    }

}
