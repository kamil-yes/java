package com.kamil.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBAdapterAircraft {
    private DBHelperAircraft dbHelperAircraft;
    private SQLiteDatabase db;

    public DBAdapterAircraft(Context context) {
        dbHelperAircraft = new DBHelperAircraft(context);
    }

    public DBAdapterAircraft open() {
        db = dbHelperAircraft.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelperAircraft.close();
    }

    public List<Aircraft> getAircrafts() {
        List<Aircraft> aircrafts = new ArrayList<>();
        Cursor cursor = db.query(
                DBHelperAircraft.TABLE,
                new String[]{
                        DBHelperAircraft.COLUMN_ID,
                        DBHelperAircraft.COLUMN_NAME,
                        DBHelperAircraft.COLUMN_MODEL,
                        DBHelperAircraft.COLUMN_CREATION_YEAR,
                        DBHelperAircraft.COLUMN_INSPECTION_YEAR,
                        DBHelperAircraft.COLUMN_PRICE,
                        DBHelperAircraft.COLUMN_DESCRIPTION
                },
                null, null, null, null, null
        );
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_NAME));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_MODEL));
            int yearOfCreation = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_CREATION_YEAR));
            int yearOfInspection = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_INSPECTION_YEAR));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_PRICE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_DESCRIPTION));
            aircrafts.add(new Aircraft(id, name, model, yearOfCreation, yearOfInspection, price, description));
        }
        cursor.close();
        return aircrafts;
    }

    public long insert(Aircraft aircraft) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelperAircraft.COLUMN_NAME, aircraft.name);
        cv.put(DBHelperAircraft.COLUMN_MODEL, aircraft.model);
        cv.put(DBHelperAircraft.COLUMN_CREATION_YEAR, aircraft.yearOfCreation);
        cv.put(DBHelperAircraft.COLUMN_INSPECTION_YEAR, aircraft.yearOfInspection);
        cv.put(DBHelperAircraft.COLUMN_PRICE, aircraft.price);
        cv.put(DBHelperAircraft.COLUMN_DESCRIPTION, aircraft.description);
        return db.insert(DBHelperAircraft.TABLE, null, cv);
    }

    public long update(Aircraft aircraft) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelperAircraft.COLUMN_NAME, aircraft.name);
        cv.put(DBHelperAircraft.COLUMN_MODEL, aircraft.model);
        cv.put(DBHelperAircraft.COLUMN_CREATION_YEAR, aircraft.yearOfCreation);
        cv.put(DBHelperAircraft.COLUMN_INSPECTION_YEAR, aircraft.yearOfInspection);
        cv.put(DBHelperAircraft.COLUMN_PRICE, aircraft.price);
        cv.put(DBHelperAircraft.COLUMN_DESCRIPTION, aircraft.description);
        return db.update(
                DBHelperAircraft.TABLE,
                cv,
                DBHelperAircraft.COLUMN_ID + " = ?",
                new String[] {String.valueOf(aircraft.id)}
        );
    }

    public long delete(long id) {
        return db.delete(
                DBHelperAircraft.TABLE,
                DBHelperAircraft.COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)}
        );
    }

    public Aircraft getAircraft(long id) {
        Cursor cursor = db.query(
                DBHelperAircraft.TABLE,
                null,
                DBHelperAircraft.COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)},
                null, null, null
        );
        Aircraft aircraft = null;
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_NAME));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_MODEL));
            int yearOfCreation = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_CREATION_YEAR));
            int yearOfInspection = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_INSPECTION_YEAR));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_PRICE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperAircraft.COLUMN_DESCRIPTION));
            aircraft = new Aircraft(id, name, model, yearOfCreation, yearOfInspection, price, description);
        }
        cursor.close();
        return aircraft;
    }
}
