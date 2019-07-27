package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - Select Movie Page solutions
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MovieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    SharedPreferences myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String welcomeMeg = null;
        TextView txtUser = (TextView) findViewById(R.id.txtUser);
        SharedPreferences myPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        String user = myPref.getString("userName","");

        welcomeMeg = "Welcome back   " + user;

        txtUser.setText(welcomeMeg);

        Spinner spinner_date = findViewById(R.id.spinner_date);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dateOfMovie,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myPreference = getSharedPreferences("BookingInfo", 0);
        final SharedPreferences.Editor prefEditor = myPreference.edit();


        //Apply the adapter
        spinner_date.setAdapter(adapter);

        spinner_date.setOnItemSelectedListener(this);

        Button btnDragon = (Button) findViewById(R.id.btnDragon);
        Button btnMarvel = (Button) findViewById(R.id.btnMarvel);
        Button btnAquaman = (Button) findViewById(R.id.btnAquaman);

        btnAquaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movie_id;
                Intent intent = new Intent(MovieActivity.this, ShowtimeActivity.class);
                movie_id = 1;
                prefEditor.putString("movie_id", String.valueOf(movie_id));
                prefEditor.apply();
                startActivity(intent);
            }
        });

        btnDragon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, ShowtimeActivity.class);
                int movie_id;
                movie_id = 8;
                prefEditor.putString("movie_id", String.valueOf(movie_id));
                prefEditor.apply();
                startActivity(intent);
            }
        });

        btnMarvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, ShowtimeActivity.class);
                int movie_id;
                movie_id = 7;
                prefEditor.putString("movie_id", String.valueOf(movie_id));
                prefEditor.apply();
                startActivity(intent);
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        myPreference = getSharedPreferences("BookingInfo", 0);
        SharedPreferences.Editor prefEditor = myPreference.edit();
        String showDate;
        showDate = parent.getItemAtPosition(position).toString();
        prefEditor.putString("showDate", showDate);
        prefEditor.apply();

    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        myPreference = getSharedPreferences("BookingInfo", 0);
        SharedPreferences.Editor prefEditor = myPreference.edit();
        String showDate = "Today, March 11";
        prefEditor.putString("showDate", showDate);
        prefEditor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String movie;
        Intent intent;
        //handle menu items
        switch(item.getItemId())
        {
            case R.id.book:
                intent = new Intent(MovieActivity.this, ConfirmationActivity.class);
                startActivity(intent);
                break;
            case R.id.person:

                intent = new Intent(MovieActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
                break;
        }
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //show the menu in the MainActivity
        getMenuInflater().inflate(R.menu.user_option, menu);
        return true;
    }
}
