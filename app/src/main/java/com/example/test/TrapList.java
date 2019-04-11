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

public class TrapList extends Nav_MainToolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_list);

        ListView listView = (ListView) findViewById(R.id.TrapList);


        //Intent intent = getIntent();
        ArrayList<Trap> TrapList2 = (ArrayList<Trap>) getIntent().getSerializableExtra("trapListPassedToIntent");
        //Log.d("SERVIISI3","Intentistä saatu list size: " + TrapList2.size());
        //Log.d("SERVIISI3","Shit: " + TrapList2.isEmpty());
        TrapArrayAdapter trapAdapter = new TrapArrayAdapter(this, TrapList2);
        listView.setAdapter(trapAdapter);

    }

}
