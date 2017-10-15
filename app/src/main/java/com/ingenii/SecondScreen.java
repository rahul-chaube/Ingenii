package com.ingenii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import info.hoang8f.widget.FButton;

public class SecondScreen extends AppCompatActivity {
    EditText name,company,designation,companyLogo,url;
    String  strName,strCompany,strDesignation,strLogo,strUrl;
    boolean allFilled = true;
    FButton fButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name= (EditText) findViewById(R.id.userName);
        company= (EditText) findViewById(R.id.company);
        designation= (EditText) findViewById(R.id.designation);
        companyLogo= (EditText) findViewById(R.id.companyLogo);
        url= (EditText) findViewById(R.id.url);
        fButton= (FButton) findViewById(R.id.done);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allFilled = !name.getText().toString().isEmpty();

                allFilled = !company.getText().toString().isEmpty();

                if (designation.getText().toString().isEmpty())
                    allFilled = false;
                else if (companyLogo.getText().toString().isEmpty())
                    allFilled = false;
                else if (url.getText().toString().isEmpty())
                    allFilled = false;

                if (allFilled){
                        save();
                }
                else
                    Toast.makeText(SecondScreen.this, "all field are mandotaory", Toast.LENGTH_SHORT).show();

            }
        });
    }
    void save()
    {
        Pref.setName(this,name.getText().toString());
        Pref.setDesignation(this,designation.getText().toString());
        Pref.setCompany(this,company.getText().toString());
        Pref.setLogo(this,companyLogo.getText().toString());
        Pref.setUrl(this,url.getText().toString());

        Toast.makeText(this, " Data Saved ", Toast.LENGTH_SHORT).show();
        finish();
    }
}
