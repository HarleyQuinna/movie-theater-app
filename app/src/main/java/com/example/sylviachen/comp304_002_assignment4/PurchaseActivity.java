package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - Purchase and Payment Page solutions

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    double total = 0.00;
    double adult = 13.75;
    double children = 9.75;
    int noOfAdult = 0;
    int noOfChildren = 0;
    SharedPreferences myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Spinner spinner1 = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);

        TextView txtvPrice = findViewById(R.id.txtvPrice);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numberOfTickets,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        txtvPrice.setText(Double.toString(total));
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        //this.onCheckBoxClicked();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        CheckBox checkBoxAdult = findViewById(R.id.checkBoxAdult);
        CheckBox checkBoxChildren = findViewById(R.id.checkBoxChildren);
        TextView txtvPrice = findViewById(R.id.txtvPrice);

        if(parent.getId() == R.id.spinner && checkBoxAdult.isChecked())
        {
            noOfAdult = Integer.parseInt(parent.getItemAtPosition(position).toString());
        }
        else if(parent.getId() == R.id.spinner && ! checkBoxAdult.isChecked())
        {
            noOfAdult = 0;
        }
        else if(parent.getId() == R.id.spinner2 && checkBoxChildren.isChecked())
        {
            noOfChildren = Integer.parseInt(parent.getItemAtPosition(position).toString());
        }
        else if(parent.getId() == R.id.spinner2 && ! checkBoxChildren.isChecked())
        {
            noOfChildren = 0;
        }
        else if((! checkBoxAdult.isChecked() && ! checkBoxChildren.isChecked()))
        {
            noOfChildren = 0;
            noOfAdult = 0;
            Toast.makeText(getApplicationContext(),"Please select your ticket type", Toast.LENGTH_LONG).show();
        }

        total = noOfAdult * adult + noOfChildren * children;

        txtvPrice.setText(Double.toString(total));

    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        CheckBox checkBoxAdult = findViewById(R.id.checkBoxAdult);
        CheckBox checkBoxChildren = findViewById(R.id.checkBoxChildren);
        if(checkBoxAdult.isChecked())
        {
            noOfAdult = 1;

        }
        if(checkBoxChildren.isChecked())
        {
            noOfChildren = 1;
        }

    }

    public void checkOut(View view)
    {
        CheckBox checkBoxAdult = findViewById(R.id.checkBoxAdult);
        CheckBox checkBoxChildren = findViewById(R.id.checkBoxChildren);
        myPreference = getSharedPreferences("BookingInfo", 0);
        SharedPreferences.Editor prefEditor = myPreference.edit();
        prefEditor.putString("amount_paid", String.valueOf(total));
        prefEditor.apply();

        String movie_id = myPreference.getString("movie_id", "");
        String showtime = myPreference.getString("showtime", "");
        String showDate = myPreference.getString("showDate", "");
        String book_status = myPreference.getString("book_status", "");
        String amount_paid = "$ " + myPreference.getString("amount_paid", "");


        Intent intent = new Intent(PurchaseActivity.this, ConfirmationActivity.class);

        if(checkBoxAdult.isChecked() || checkBoxChildren.isChecked())
        {
            String bookStatus = "Booked";
            prefEditor.putString("book_status", bookStatus);
            String emailId = null;
            prefEditor.apply();
            startActivity(intent);

            SharedPreferences myPrefer = getSharedPreferences("SharedId", MODE_PRIVATE);
            String userName = myPrefer.getString("userName","");

            DatabaseManager dbM = new DatabaseManager(this);
            SQLiteDatabase db = dbM.getReadableDatabase();

            String[] columns = {"user_name", "email_id"};

            String selection = "user_name" + " = ?";
            String[] selectionArgs = {userName};
            Cursor cursor = db.query("tb_audience",
                    columns, selection, selectionArgs, null, null, null);

            if(null != cursor) {
                cursor.moveToFirst();
                emailId = cursor.getString(1);
            }

            final String fields[] = {"booking_id", "email_id", "movie_id", "payment_date", "amount_paid", "show_date", "show_time", "booking_status"};
            final String record[] = new String[8];
            record[1] = emailId;
            record[2] = movie_id;
            record[3] = showDate;
            record[4] = amount_paid;
            record[5] = showDate;
            record[6] = showtime;
            record[7] = book_status;
            ContentValues values = new ContentValues();

            //add the row to the database
            dbM.addRecord(values, "tb_booking", fields, record);
            prefEditor.putString("email_id", emailId);
            prefEditor.apply();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please select at least one ticket type", Toast.LENGTH_LONG).show();
        }


    }
}

