package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - Admin Home Page solutions
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class AdminHomeActivity extends AppCompatActivity {

    String[] tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        String welcomeMeg = null;
        TextView txtUser = (TextView) findViewById(R.id.txtUser);
        SharedPreferences myPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        String user = myPref.getString("userName","");

        welcomeMeg = "Welcome back   " + user;

        txtUser.setText(welcomeMeg);

        Button btnMovie = (Button) findViewById(R.id.btnManageMovie);
        Button btnAudience = (Button) findViewById(R.id.btnManageAudience);
        Button btnBooking = (Button) findViewById(R.id.btnManageBooking);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageMovieActivity.class);
                startActivity(intent);
            }
        });

        btnAudience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageAudienceActivity.class);
                startActivity(intent);
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageBookingActivity.class);
                startActivity(intent);
            }
        });
    }
}
