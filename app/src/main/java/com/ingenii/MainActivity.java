package com.ingenii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {
    FButton createProfile,showProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createProfile= (FButton) findViewById(R.id.createProfile);
        showProfile= (FButton) findViewById(R.id.showProfile);
        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondScreen.class));

            }
        });
        showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Three.class));
            }
        });

    }
}
