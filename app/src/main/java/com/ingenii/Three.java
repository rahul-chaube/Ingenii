package com.ingenii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Three extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
