package com.kamil.java;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBAdapterFavorite {
    private Context context;
    private DBHelperFavorite dbHelperFavorite;
    private SQLiteDatabase db;

    public DBAdapterFavorite(Context context) {
        this.context = context;
        dbHelperFavorite = new DBHelperFavorite(context);
    }

    public DBAdapterFavorite open() {
        db = dbHelperFavorite.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelperFavorite.close();
    }

    public List<Favorite> getFavorites(User user) {
        List<Favorite> favorites = new ArrayList<>();
        Cursor cursor = db.query(
                DBHelperFavorite.TABLE,
                new String[]{
                        DBHelperFavorite.COLUMN_USER_ID,
                        DBHelperFavorite.COLUMN_AIRCRAFT_ID,
                        DBHelperFavorite.COLUMN_CREATION_DATE,
                },
                DBHelperFavorite.COLUMN_USER_ID + " = ?",
                new String[] {String.valueOf(user.id)},
                null, null, null
        );
        while(cursor.moveToNext()) {
            long userId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperFavorite.COLUMN_USER_ID));
            long aircraftId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperFavorite.COLUMN_AIRCRAFT_ID));
            String creationDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperFavorite.COLUMN_CREATION_DATE));

            DBAdapterAircraft dbAdapterAircraft = new DBAdapterAircraft(context).open();
            favorites.add(new Favorite(user, dbAdapterAircraft.getAircraft(aircraftId), creationDate));
        }
        cursor.close();
        return favorites;
    }

    public long insert(User user, Aircraft aircraft) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelperFavorite.COLUMN_USER_ID, user.id);
        cv.put(DBHelperFavorite.COLUMN_AIRCRAFT_ID, aircraft.id);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String creationDate = timeFormat.format(new Date());

        cv.put(DBHelperFavorite.COLUMN_CREATION_DATE, creationDate);
        return db.insert(DBHelperFavorite.TABLE, null, cv);
    }

    public long delete(User user, Aircraft aircraft) {
        return db.delete(
                DBHelperFavorite.TABLE,
                DBHelperFavorite.COLUMN_USER_ID + " = ? AND " + DBHelperFavorite.COLUMN_AIRCRAFT_ID + " = ?",
                new String[] {String.valueOf(user.id), String.valueOf(aircraft.id)}
        );
    }

    public boolean isFavorite(User user, Aircraft aircraft) {
        Cursor cursor = db.query(
                DBHelperFavorite.TABLE,
                null,
                DBHelperFavorite.COLUMN_USER_ID + " = ? AND " + DBHelperFavorite.COLUMN_AIRCRAFT_ID + " = ?",
                new String[] {String.valueOf(user.id), String.valueOf(aircraft.id)},
                null, null, null
        );
        boolean isFavorite = cursor.moveToFirst();
        cursor.close();
        return isFavorite;
    }
}
