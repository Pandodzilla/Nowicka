package db;

import android.provider.BaseColumns;

/**
 * Created by Wanda on 30.01.2016.
 */
public class bazadanych {
    public static final String DB_NAME = "com.example.wanda.nowicka.db.zadania";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "zadania";

    public class Kolumny {
        public static final String TASK = "zadanie";
        public static final String _ID = BaseColumns._ID;
    }
}