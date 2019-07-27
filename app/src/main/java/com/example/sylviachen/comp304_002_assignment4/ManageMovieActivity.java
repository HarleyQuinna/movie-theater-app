package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - Manage Movie solutions
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManageMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_movie);


        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText txtName = (EditText)findViewById(R.id.txtName);
        final EditText txtDirector = (EditText)findViewById(R.id.txtDirector);
        final EditText txtGenre = (EditText)findViewById(R.id.txtGenre);

        final Context context = this;

        final DatabaseManager dbM = new DatabaseManager(this);

        final String fields[] = {"movie_id", "movie_name", "director", "genre"};
        final String record[] = new String[4];

       final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Movie ID","Name","Director", "Genre"};
        for(String c:headerText) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        SQLiteDatabase db = dbM.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT * FROM "+ "tb_movie";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
                    int user_id= cursor.getInt(cursor.getColumnIndex("movie_id"));
                    String user_name= cursor.getString(cursor.getColumnIndex("movie_name"));
                    String email_id= cursor.getString(cursor.getColumnIndex("director"));
                    String last_name = cursor.getString(cursor.getColumnIndex("genre"));

                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={user_id+"",user_name,email_id, last_name};
                    for(String text:colText) {
                        TextView tv = new TextView(context);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(12);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record[1] = txtName.getText().toString();
                record[2] = txtDirector.getText().toString();
                record[3] = txtGenre.getText().toString();

                ContentValues values = new ContentValues();

                //add the row to the database
                dbM.addRecord(values, "tb_movie", fields, record);
                SQLiteDatabase database = dbM.getReadableDatabase();
                database.beginTransaction();

                try
                {
                    String[] columns = {"movie_id", "movie_name", "director", "genre"};

                    String movieName = txtName.getText().toString();
                    String selection = "movie_name" + " = ?";
                    String[] selectionArgs = {movieName};
                    Cursor cursor = database.query("tb_movie",
                            columns, selection, selectionArgs, null, null, null);

                    if(null != cursor)
                    {
                        cursor.moveToFirst();
                            // Read columns data
                        int movie_id= cursor.getInt(cursor.getColumnIndex("movie_id"));
                        String movie_name= cursor.getString(cursor.getColumnIndex("movie_name"));
                        String director= cursor.getString(cursor.getColumnIndex("director"));
                        String genre = cursor.getString(cursor.getColumnIndex("genre"));

                            // dara rows
                            TableRow row = new TableRow(context);
                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                            String[] colText={movie_id+"",movie_name,director, genre};
                            for(String text:colText) {
                                TextView tv = new TextView(context);
                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));
                                tv.setGravity(Gravity.CENTER);
                                tv.setTextSize(14);
                                tv.setPadding(5, 5, 5, 5);
                                tv.setText(text);
                                row.addView(tv);
                            }
                            tableLayout.addView(row);

                    }
                    database.setTransactionSuccessful();

                }
                catch (SQLiteException e)
                {
                    e.printStackTrace();

                }
                finally
                {
                    database.endTransaction();
                    // End the transaction.
                    database.close();
                    // Close database
                }
            }
        });

        final EditText txtId = (EditText) findViewById(R.id.txtId);

        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbM.deleteRecord("tb_movie", "movie_id", txtId.getText().toString());
                tableLayout.removeAllViews();
                // Add header row
                TableRow rowHeader = new TableRow(context);
                rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] headerText={"Movie ID","Name","Director", "Genre"};
                for(String c:headerText) {
                    TextView tv = new TextView(context);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(15);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(c);
                    rowHeader.addView(tv);
                }
                tableLayout.addView(rowHeader);

                SQLiteDatabase db = dbM.getReadableDatabase();
                // Start the transaction.
                db.beginTransaction();

                try
                {
                    String selectQuery = "SELECT * FROM "+ "tb_movie";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount() >0)
                    {
                        while (cursor.moveToNext()) {
                            // Read columns data
                            int user_id= cursor.getInt(cursor.getColumnIndex("movie_id"));
                            String user_name= cursor.getString(cursor.getColumnIndex("movie_name"));
                            String email_id= cursor.getString(cursor.getColumnIndex("director"));
                            String last_name = cursor.getString(cursor.getColumnIndex("genre"));

                            // dara rows
                            TableRow row = new TableRow(context);
                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                            String[] colText={user_id+"",user_name,email_id, last_name};
                            for(String text:colText) {
                                TextView tv = new TextView(context);
                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));
                                tv.setGravity(Gravity.CENTER);
                                tv.setTextSize(12);
                                tv.setPadding(5, 5, 5, 5);
                                tv.setText(text);
                                row.addView(tv);
                            }
                            tableLayout.addView(row);

                        }

                    }
                    db.setTransactionSuccessful();

                }
                catch (SQLiteException e)
                {
                    e.printStackTrace();

                }
                finally
                {
                    db.endTransaction();
                    // End the transaction.
                    db.close();
                    // Close database
                }


            }
        });


    }
}
