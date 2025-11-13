package com.kamil.java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int SCHEMA = 1;

    public static final String TABLE = "aircrafts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_CREATION_YEAR = "creation_year";
    public static final String COLUMN_INSPECTION_YEAR = "inspection_year";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_CREATION_YEAR + " INTEGER, " +
                COLUMN_INSPECTION_YEAR + ", INTEGER);"
        );
        db.execSQL(
                "INSERT INTO " + TABLE + " (" +
                COLUMN_NAME + ", " +
                COLUMN_MODEL + ", " +
                COLUMN_CREATION_YEAR + ", " +
                COLUMN_INSPECTION_YEAR +") VALUES " +
                    "('AI1234', 'Airbus A380', 2020, 2023), " +
                    "('BG9876', 'Boeng 777', 2023, 2025), " +
                    "('AB3456', 'Cesna 172', 2018, 2020);"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
        onCreate(db);
    }
}
