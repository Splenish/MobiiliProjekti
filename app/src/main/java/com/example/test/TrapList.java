package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    }

}
