package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wanda on 30.01.2016.
 */
public class PomocnikBD extends SQLiteOpenHelper {

    public PomocnikBD(Context context) {
        super(context, bazadanych.DB_NAME, null, bazadanych.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT)", bazadanych.TABLE,
                        bazadanych.Kolumny.TASK);

        Log.d("PomocnikBD", "Pochodzi z tabeli: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("Jeśli istnieje usuń z: "+bazadanych.TABLE);
        onCreate(sqlDB);
    }
}
