package com.kamil.java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperFavorite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int SCHEMA = 18;

    public static final String TABLE = "favorites";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_AIRCRAFT_ID = "aircraft_id";
    public static final String COLUMN_CREATION_DATE = "creation_date";

    public DBHelperFavorite(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(
                "CREATE TABLE " + TABLE + " (" +
                        COLUMN_USER_ID + " INTEGER, " +
                        COLUMN_AIRCRAFT_ID + " INTEGER," +
                        COLUMN_CREATION_DATE + " TEXT, " +
                        " PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_AIRCRAFT_ID + "), " +
                        " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + DBHelperUser.TABLE + "(id) ON DELETE CASCADE, " +
                        " FOREIGN KEY (" + COLUMN_AIRCRAFT_ID + ") REFERENCES " + DBHelperAircraft.TABLE + "(id) ON DELETE CASCADE);"
        );
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
        onCreate(db);
    }
}
