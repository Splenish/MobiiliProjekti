package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bottom_Nav_Bar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom__nav__bar);
    }

    private void configureButton1() {
        Button Button1 = (Button) findViewById(R.id.Button1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configureButton2() {
        Button Button2 = (Button) findViewById(R.id.Button2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configureButton3() {
        Button Button3 = (Button) findViewById(R.id.Button3);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configureButton4() {
        Button Button4 = (Button) findViewById(R.id.Button4);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configureButton5() {
        Button Button5 = (Button) findViewById(R.id.Button5);
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
