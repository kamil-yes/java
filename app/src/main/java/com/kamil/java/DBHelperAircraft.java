package com.kamil.java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperAircraft extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int SCHEMA = 19;

    public static final String TABLE = "aircrafts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_CREATION_YEAR = "creation_year";
    public static final String COLUMN_INSPECTION_YEAR = "inspection_year";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";

    public DBHelperAircraft(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_CREATION_YEAR + " INTEGER, " +
                COLUMN_INSPECTION_YEAR + " INTEGER, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT);"
        );
        db.execSQL(
                "INSERT INTO " + TABLE + " (" +
                COLUMN_NAME + ", " +
                COLUMN_MODEL + ", " +
                COLUMN_CREATION_YEAR + ", " +
                COLUMN_INSPECTION_YEAR + ", " +
                COLUMN_PRICE + ", " +
                COLUMN_DESCRIPTION + ") VALUES " +
                    "('AI1234', 'Airbus A380', 2020, 2023, 1000000000, 'Best airliner!'), " +
                    "('BG9876', 'Boeng 777', 2023, 2025, 2000000000, 'Very nice!!!'), " +
                    "('AB3456', 'Cesna 172', 2018, 2020, 3000000, 'So good!');"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
        onCreate(db);
    }
}
