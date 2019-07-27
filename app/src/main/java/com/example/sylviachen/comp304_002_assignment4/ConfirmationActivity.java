package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - BookInfoPage solutions
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences myPreference = getSharedPreferences("BookingInfo", 0);

        String movie_id = myPreference.getString("movie_id", "");
        String showtime = myPreference.getString("showtime", "");
        String showDate = myPreference.getString("showDate", "");
        String book_status = myPreference.getString("book_status", "");
        String amount_paid = "$ " + myPreference.getString("amount_paid", "");
        String email_id = myPreference.getString("email_id", "");

        ImageView imageView = (ImageView) findViewById(R.id.imageTicket);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        TextView txtMovie = (TextView) findViewById(R.id.txtMovieId);
        TextView txtAmount = (TextView) findViewById(R.id.txtAmount);
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        TextView txtDate = (TextView) findViewById(R.id.txtShowDate);

        if(Integer.parseInt(movie_id) == 1)
        {
            imageView.setImageResource(R.drawable.aavater);
        }
        else if(Integer.parseInt(movie_id) == 7)
        {
            imageView.setImageResource(R.drawable.marvel_avatar);
        }
        else if(Integer.parseInt(movie_id) == 8)
        {
            imageView.setImageResource(R.drawable.dragon_avatat);
        }
        txtAmount.setText(amount_paid);
        txtDate.setText(showDate);
        txtTime.setText(showtime);
        txtMovie.setText(movie_id);
        txtEmail.setText(email_id);
    }
}
