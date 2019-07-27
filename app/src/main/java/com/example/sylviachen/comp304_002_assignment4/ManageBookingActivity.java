package com.example.sylviachen.comp304_002_assignment4;

// Author: Si Chen
//Assignment 4 - Manage BookingInfo solutions
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManageBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);

        final DatabaseManager dbM = new DatabaseManager(this);

        ImageButton imageButton_back = (ImageButton) findViewById(R.id.btnBack);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        final Context context = this;
        // Add header row
        TableRow rowHeader = new TableRow(this);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Book ID","Email","Movie ID", "Payment", "Status"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
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
            String selectQuery = "SELECT * FROM "+ "tb_booking";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
                    int book_id= cursor.getInt(cursor.getColumnIndex("booking_id"));
                    String email_id= cursor.getString(cursor.getColumnIndex("email_id"));
                    String movie_id= cursor.getString(cursor.getColumnIndex("movie_id"));
                    String payment = cursor.getString(cursor.getColumnIndex("amount_paid"));
                    String status = cursor.getString(cursor.getColumnIndex("booking_status"));

                    // dara rows
                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={book_id+"",email_id, movie_id, payment, status};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
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

        final EditText txtId = (EditText) findViewById(R.id.txtId);

       Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbM.deleteRecord("tb_booking", "booking_id", txtId.getText().toString());
                tableLayout.removeAllViews();
                // Add header row
                TableRow rowHeader = new TableRow(context);
                rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] headerText={"Book ID","Email","Movie ID", "Payment", "Status"};
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
                    String selectQuery = "SELECT * FROM "+ "tb_booking";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount() >0)
                    {
                        while (cursor.moveToNext()) {
                            // Read columns data
                            int book_id= cursor.getInt(cursor.getColumnIndex("booking_id"));
                            String email_id= cursor.getString(cursor.getColumnIndex("email_id"));
                            String movie_id= cursor.getString(cursor.getColumnIndex("movie_id"));
                            String payment = cursor.getString(cursor.getColumnIndex("amount_paid"));
                            String status = cursor.getString(cursor.getColumnIndex("booking_status"));

                            // dara rows
                            TableRow row = new TableRow(context);
                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                            String[] colText={book_id+"",email_id, movie_id, payment, status};
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
                finally {
                    db.endTransaction();
                    // End the transaction.
                    db.close();
                    // Close database
                }

            }
        });

        final CheckBox checkBoxCancel = (CheckBox) findViewById(R.id.checkboxCancel);

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxCancel.isChecked())
                {
                    String booking_id = txtId.getText().toString();
                    ContentValues cv = new ContentValues();
                    String[] fields = {"booking_id", "booking_status"};
                    String[] record = {booking_id, "Canceled"};
                    dbM.updateRecord(cv, "tb_booking", fields, record);
                    tableLayout.removeAllViews();
                    // Add header row
                    TableRow rowHeader = new TableRow(context);
                    rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
                    rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] headerText={"Book ID","Email","Movie ID", "Payment", "Status"};
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
                        String selectQuery = "SELECT * FROM "+ "tb_booking";
                        Cursor cursor = db.rawQuery(selectQuery,null);
                        if(cursor.getCount() >0)
                        {
                            while (cursor.moveToNext()) {
                                // Read columns data
                                int book_id= cursor.getInt(cursor.getColumnIndex("booking_id"));
                                String email_id= cursor.getString(cursor.getColumnIndex("email_id"));
                                String movie_id= cursor.getString(cursor.getColumnIndex("movie_id"));
                                String payment = cursor.getString(cursor.getColumnIndex("amount_paid"));
                                String status = cursor.getString(cursor.getColumnIndex("booking_status"));

                                // dara rows
                                TableRow row = new TableRow(context);
                                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                        TableLayout.LayoutParams.WRAP_CONTENT));
                                String[] colText={book_id+"",email_id, movie_id, payment, status};
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
                    finally {
                        db.endTransaction();
                        // End the transaction.
                        db.close();
                        // Close database
                    }
                }
            }
        });
    }
}
