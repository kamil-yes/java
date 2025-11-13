package com.kamil.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DBAdapter {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public DBAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Aircraft> getAircrafts() {
        List<Aircraft> aircrafts = new ArrayList<>();
        Cursor cursor = db.query(
                DBHelper.TABLE,
                new String[]{
                        DBHelper.COLUMN_ID,
                        DBHelper.COLUMN_NAME,
                        DBHelper.COLUMN_MODEL,
                        DBHelper.COLUMN_CREATION_YEAR,
                        DBHelper.COLUMN_INSPECTION_YEAR
                },
                null, null, null, null, null
        );
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MODEL));
            int yearOfCreation = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CREATION_YEAR));
            int yearOfInspection = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INSPECTION_YEAR));
            aircrafts.add(new Aircraft(id, name, model, yearOfCreation, yearOfInspection));
        }
        cursor.close();
        return aircrafts;
    }

    public long insert(Aircraft aircraft) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, aircraft.name);
        cv.put(DBHelper.COLUMN_MODEL, aircraft.model);
        cv.put(DBHelper.COLUMN_CREATION_YEAR, aircraft.yearOfCreation);
        cv.put(DBHelper.COLUMN_INSPECTION_YEAR, aircraft.yearOfInspection);
        return db.insert(DBHelper.TABLE, null, cv);
    }

    public long update(Aircraft aircraft) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, aircraft.name);
        cv.put(DBHelper.COLUMN_MODEL, aircraft.model);
        cv.put(DBHelper.COLUMN_CREATION_YEAR, aircraft.yearOfCreation);
        cv.put(DBHelper.COLUMN_INSPECTION_YEAR, aircraft.yearOfInspection);
        return db.update(
                DBHelper.TABLE,
                cv,
                DBHelper.COLUMN_ID + " = ?",
                new String[] {String.valueOf(aircraft.id)}
        );
    }

    public long delete(long id) {
        return db.delete(
                DBHelper.TABLE,
                DBHelper.COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)}
        );
    }

    public Aircraft getAircraft(long id) {
        Cursor cursor = db.query(
                DBHelper.TABLE,
                null,
                DBHelper.COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)},
                null, null, null
        );
        Aircraft aircraft = null;
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MODEL));
            int yearOfCreation = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CREATION_YEAR));
            int yearOfInspection = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INSPECTION_YEAR));
            aircraft = new Aircraft(id, name, model, yearOfCreation, yearOfInspection);
        }
        cursor.close();
        return aircraft;
    }
}
