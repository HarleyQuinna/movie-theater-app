package com.example.sylviachen.comp304_002_assignment4;

// Author: Si CHen
//Assignment 4 - RegisterPage solutions

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    //
    private static final String tables[]={"tb_audience","tb_admin", "tb_movie", "tb_booking"};
    //
    private static final String tableCreator[] =
            {"CREATE TABLE tb_audience (user_id INTEGER PRIMARY KEY AUTOINCREMENT , user_name TEXT, " +
                    "password TEXT, first_name TEXT, last_name TEXT, email_id TEXT, address TEXT, city TEXT, postal_code TEXT);",
                    "CREATE TABLE tb_admin (employee_id INTEGER PRIMARY KEY AUTOINCREMENT , user_name TEXT, " +
                            "password TEXT, first_name TEXT, last_name TEXT, email_id TEXT, address TEXT, city TEXT, postal_code TEXT);",
                    "CREATE TABLE tb_movie (movie_id INTEGER PRIMARY KEY AUTOINCREMENT , movie_name TEXT, " +
                            "director TEXT, genre TEXT);",
                    "CREATE TABLE tb_booking (booking_id INTEGER PRIMARY KEY AUTOINCREMENT , email_id TEXT, " +
                            "movie_id INTEGER, payment_date TEXT, amount_paid DECIMAL, show_date TEXT, show_time TEXT, booking_status TEXT);"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText txtUser = (EditText)findViewById(R.id.txtUser);
        final EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
        final EditText txtFirst = (EditText)findViewById(R.id.txtFirst);
        final EditText txtLast = (EditText)findViewById(R.id.txtLast);
        final EditText txtAddress = (EditText)findViewById(R.id.txtAddress);
        final EditText txtCity = (EditText)findViewById(R.id.txtCity);
        final EditText txtPostal = (EditText)findViewById(R.id.txtPostal);
        final EditText txtPw = (EditText)findViewById(R.id.txtPassword1);
        final EditText txtPwCon = (EditText)findViewById(R.id.txtPassword2);

        final TextView userValidate = (TextView)findViewById(R.id.userValidate);
        final TextView emailValidate = (TextView)findViewById(R.id.emailValidate);
        final TextView firstValidate = (TextView)findViewById(R.id.firstValidate);
        final TextView lastValidate = (TextView)findViewById(R.id.lastValidate);
        final TextView pwValidate = (TextView)findViewById(R.id.pwValidate);
        final TextView pwconValidate = (TextView)findViewById(R.id.pwconValidate);

        //define email and password pattern
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";


        Button btnRegister = findViewById(R.id.btnRegister);

        final DatabaseManager db = new DatabaseManager(this);
        //db.createDatabase(getApplicationContext());
        db.dbInitialize(tables,tableCreator);

        final String audience_fields[] = {"user_id", "user_name", "password", "first_name", "last_name", "email_id", "address", "city", "postal_code"};
        final String record[] = new String[9];

        final String admin_fields[] = {"employee_id",  "user_name", "password", "first_name", "last_name", "email_id", "address", "city", "postal_code"};

        final SQLiteDatabase database = db.getReadableDatabase();

        //action for submit button
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                SharedPreferences myPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                String guest = myPref.getString("guest","");

                record[1] = txtUser.getText().toString();
                record[2] = txtPw.getText().toString();
                record[3] = txtFirst.getText().toString();
                record[4] = txtLast.getText().toString();
                record[5] = txtEmail.getText().toString();
                record[6] = txtAddress.getText().toString();
                record[7] = txtCity.getText().toString();
                record[8] = txtPostal.getText().toString();

                if(guest.equals(getResources().getString(R.string.audience)))
                {
                    String[] column_name = {"user_name"};
                    final String[] column_email = {"email_id"};
                    String selection_name = "user_name" + " = ?";
                    String selection_email = "email_id" + " = ?";
                    String[] selectionArgs_name = {txtUser.getText().toString()};
                    String[] selectionArgs_email = {txtEmail.getText().toString()};
                    final Cursor cursor_name = database.query("tb_audience",
                            column_name, selection_name, selectionArgs_name, null, null, null);

                    final Cursor cursor_email = database.query("tb_audience",
                            column_email, selection_email, selectionArgs_email, null, null, null);

                    //validation for registration input
                    boolean isValid = true;
                    if(txtUser.getText().toString().contains(" ") || txtUser.getText().toString().equals(""))
                    {
                        isValid = false;
                        userValidate.setText(getResources().getString(R.string.user_validation));
                    }
                    if(cursor_name.getCount() > 0)
                    {
                        isValid = false;
                        userValidate.setText(getResources().getString(R.string.user_repeat));
                    }
                    if(! txtEmail.getText().toString().trim().matches(emailPattern) || txtEmail.getText().toString().equals(""))
                    {
                        isValid = false;
                        emailValidate.setText(getResources().getString(R.string.email_validation));
                    }
                    if(cursor_email.getCount() > 0)
                    {
                        isValid = false;
                        emailValidate.setText(getResources().getString(R.string.email_repeat));
                    }
                    if(txtFirst.getText().toString().equals(""))
                    {
                        isValid = false;
                        firstValidate.setText(getResources().getString(R.string.first_validation));
                    }
                    if(txtLast.getText().toString().equals(""))
                    {
                        isValid = false;
                        lastValidate.setText(getResources().getString(R.string.last_validation));
                    }
                    if(! txtPw.getText().toString().matches(passwordPattern) || txtPw.getText().toString().equals(""))
                    {
                        isValid = false;
                        pwValidate.setText(getResources().getString(R.string.pw_validation));
                    }
                    if(! txtPwCon.getText().toString().equals(txtPw.getText().toString()) || txtPwCon.getText().toString().equals(""))
                    {
                        isValid = false;
                        pwconValidate.setText(getResources().getString(R.string.pwcon_validation));
                    }
                    if(isValid) {
                        Intent intent = new Intent(RegisterActivity.this, PersonalInfoActivity.class);

                        //create shared preference to store the userName
                        SharedPreferences myPreference = getSharedPreferences("SharedId", 0);
                        SharedPreferences.Editor prefEditor = myPreference.edit();
                        prefEditor.putString("userName", record[1]);
                        prefEditor.apply();

                        startActivity(intent);
                        ContentValues values = new ContentValues();

                        //add the row to the database
                        db.addRecord(values, "tb_audience", audience_fields, record);
                    }
                }
                else if(guest.equals(getResources().getString(R.string.admin)))
                {
                    String[] column_name = {"user_name"};
                    final String[] column_email = {"email_id"};
                    String selection_name = "user_name" + " = ?";
                    String selection_email = "email_id" + " = ?";
                    String[] selectionArgs_name = {txtUser.getText().toString()};
                    String[] selectionArgs_email = {txtEmail.getText().toString()};
                    final Cursor cursor_name = database.query("tb_admin",
                            column_name, selection_name, selectionArgs_name, null, null, null);

                    final Cursor cursor_email = database.query("tb_admin",
                            column_email, selection_email, selectionArgs_email, null, null, null);

                    //validation for registration input
                    boolean isValid = true;
                    if(txtUser.getText().toString().contains(" ") || txtUser.getText().toString().equals(""))
                    {
                        isValid = false;
                        userValidate.setText(getResources().getString(R.string.user_validation));
                    }
                    if(cursor_name.getCount() > 0)
                    {
                        isValid = false;
                        userValidate.setText(getResources().getString(R.string.user_repeat));
                    }
                    if(! txtEmail.getText().toString().trim().matches(emailPattern) || txtEmail.getText().toString().equals(""))
                    {
                        isValid = false;
                        emailValidate.setText(getResources().getString(R.string.email_validation));
                    }
                    if(cursor_email.getCount() > 0)
                    {
                        isValid = false;
                        emailValidate.setText(getResources().getString(R.string.email_repeat));
                    }
                    if(txtFirst.getText().toString().equals(""))
                    {
                        isValid = false;
                        firstValidate.setText(getResources().getString(R.string.first_validation));
                    }
                    if(txtLast.getText().toString().equals(""))
                    {
                        isValid = false;
                        lastValidate.setText(getResources().getString(R.string.last_validation));
                    }
                    if(! txtPw.getText().toString().matches(passwordPattern) || txtPw.getText().toString().equals(""))
                    {
                        isValid = false;
                        pwValidate.setText(getResources().getString(R.string.pw_validation));
                    }
                    if(! txtPwCon.getText().toString().equals(txtPw.getText().toString()) || txtPwCon.getText().toString().equals(""))
                    {
                        isValid = false;
                        pwconValidate.setText(getResources().getString(R.string.pwcon_validation));
                    }
                    if(isValid) {
                        Intent intent = new Intent(RegisterActivity.this, PersonalInfoActivity.class);

                        //create shared preference to store the userName
                        SharedPreferences myPreference = getSharedPreferences("SharedId", 0);
                        SharedPreferences.Editor prefEditor = myPreference.edit();
                        prefEditor.putString("userName", record[1]);
                        prefEditor.apply();

                        startActivity(intent);
                        ContentValues values = new ContentValues();
                        db.addRecord(values, "tb_admin", admin_fields,record);
                    }



                }
            }
        });

    }
}

