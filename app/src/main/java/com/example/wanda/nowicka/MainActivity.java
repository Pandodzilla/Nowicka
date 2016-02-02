package com.example.wanda.nowicka;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import db.PomocnikBD;
import db.bazadanych;

import static db.bazadanych.*;

public class MainActivity extends ListActivity {
    private PomocnikBD helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();}


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Dodaj zadanie");
                builder.setMessage("Co planujesz?");
                final EditText inputField = new EditText(this);
                builder.setView(inputField);
                builder.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = inputField.getText().toString();
                        Log.d("MainActivity", task);

                        PomocnikBD helper = new PomocnikBD(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();


                        values.put(bazadanych.Kolumny.TASK, task);

                        db.insertWithOnConflict(TABLE, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);
                    }
                });
                builder.setNegativeButton("Wstecz", null);
                builder.create().show();
                updateUI();

                return true;

            default:
                return false;

        }
    }

    private void updateUI() {
        helper = new PomocnikBD(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TABLE,
                new String[]{bazadanych.Kolumny._ID, bazadanych.Kolumny.TASK},
                null, null, null, null, null);

        SimpleCursorAdapter ListAdapter = new SimpleCursorAdapter(
                this,
                R.layout.wyglad_zadania,
                cursor,
                new String[]{bazadanych.Kolumny.TASK},
                new int[]{R.id.wyglad_zadania},
                0
        );
        this.setListAdapter(ListAdapter);

    }

    public void usunButtonClick(View view) {
        View v = (View) view.getParent();
        TextView wyglad_zadania = (TextView) v.findViewById(R.id.wyglad_zadania);
        String zadanie = wyglad_zadania.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                bazadanych.TABLE,
                bazadanych.Kolumny.TASK,
                zadanie);


        helper = new PomocnikBD(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }
}
