package com.example.sylviachen.comp304_002_assignment4;

// Author: Si CHen
//Assignment 4 - HomePage solutions

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define radioGroup and set onCheckedChangeListener
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);
                RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);

                //create a new intent
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                //create sharedPreference to store the audience/admin options
                SharedPreferences myPreference = getSharedPreferences("MySharedPreferences", 0);
                SharedPreferences.Editor prefEditor = myPreference.edit();

                String guest = null;

                //set and handle radioButton onCheckedChanged event
                if(rb1.isChecked())
                {
                    guest = getResources().getString(R.string.audience);
                    startActivity(intent);
                }
                else if(rb2.isChecked())
                {
                    guest = getResources().getString(R.string.admin);
                    startActivity(intent);
                }
                prefEditor.putString("guest", guest);
                prefEditor.apply();
            }
        });
    }
}
