package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - Select ShowtimePage solutions

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShowtimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        SharedPreferences myPrefer = getSharedPreferences("BookingInfo", MODE_PRIVATE);
        String movie_id = myPrefer.getString("movie_id","");

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView txtName = (TextView) findViewById(R.id.txtName);
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        TextView txtIntro = (TextView) findViewById(R.id.txtIntro);
        TextView txtGenre = (TextView) findViewById(R.id.txtGenre);
        ImageView imageHead = (ImageView) findViewById(R.id.imagehead);
        ImageView imageAvatar = (ImageView) findViewById(R.id.imageAvatar);

        if(Integer.parseInt(movie_id) == 1)
        {
           txtName.setText(getResources().getString(R.string.aquaman));
           txtIntro.setText(getResources().getString(R.string.aqua_intro));
           txtTime.setText(getResources().getString(R.string.atime));
           txtGenre.setText(getResources().getString(R.string.agen));
           imageHead.setImageResource(R.drawable.ahead);
           imageAvatar.setImageResource(R.drawable.aavater);
        }
        else if(Integer.parseInt(movie_id) == 7)
        {
            txtName.setText(getResources().getString(R.string.marvel));
            txtIntro.setText(getResources().getString(R.string.marvel_intro));
            txtTime.setText(getResources().getString(R.string.mtime));
            txtGenre.setText(getResources().getString(R.string.mgen));
            imageHead.setImageResource(R.drawable.marvel_head);
            imageAvatar.setImageResource(R.drawable.marvel_avatar);
        }
        else if(Integer.parseInt(movie_id) == 8)
        {
            txtName.setText(getResources().getString(R.string.dragon));
            txtIntro.setText(getResources().getString(R.string.d_intro));
            txtTime.setText(getResources().getString(R.string.dtime));
            txtGenre.setText(getResources().getString(R.string.dgen));
            imageHead.setImageResource(R.drawable.dragon_head);
            imageAvatar.setImageResource(R.drawable.dragon_avatat);
        }

        //create sharedPreference to store the audience/admin options
        SharedPreferences myPreference = getSharedPreferences("BookingInfo", 0);
        final SharedPreferences.Editor prefEditor = myPreference.edit();

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton rb1 = findViewById(R.id.radioButton1);
                RadioButton rb2 = findViewById(R.id.radioButton2);
                RadioButton rb3 = findViewById(R.id.radioButton3);
                RadioButton rb4 = findViewById(R.id.radioButton4);
                RadioButton rb5 = findViewById(R.id.radioButton5);

                if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() ||rb4.isChecked() || rb5.isChecked())
                {
                    Intent i = getIntent();
                    Intent intent = new Intent(ShowtimeActivity.this, PurchaseActivity.class);

                    if(rb1.isChecked())
                    {
                        String time = getResources().getString(R.string.showtime1);
                        prefEditor.putString("showtime", time);
                        prefEditor.apply();
                    }
                    else if(rb2.isChecked())
                    {
                        String time = getResources().getString(R.string.showtime2);
                        prefEditor.putString("showtime", time);
                        prefEditor.apply();
                    }
                    else if(rb3.isChecked())
                    {
                        String time = getResources().getString(R.string.showtime3);
                        prefEditor.putString("showtime", time);
                        prefEditor.apply();
                    }
                    else if(rb4.isChecked())
                    {
                        String time = getResources().getString(R.string.showtime4);
                        prefEditor.putString("showtime", time);
                        prefEditor.apply();
                    }
                    else if(rb5.isChecked())
                    {
                        String time = getResources().getString(R.string.showtime5);
                        prefEditor.putString("showtime", time);
                        prefEditor.apply();
                    }
                    startActivity(intent);
                }

            }

        });
    }
}
