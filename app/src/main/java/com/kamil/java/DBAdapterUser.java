package com.kamil.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapterUser {
    private DBHelperUser dbHelperUser;
    private SQLiteDatabase db;

    public DBAdapterUser(Context context) {
        dbHelperUser = new DBHelperUser(context);
    }

    public DBAdapterUser open() {
        db = dbHelperUser.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelperUser.close();
    }

    public long insert(String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelperUser.COLUMN_EMAIL, email);
        cv.put(DBHelperUser.COLUMN_PASSWORD, password);
        return db.insert(DBHelperUser.TABLE, null, cv);
    }

    public User getUser(long id) {
        Cursor cursor = db.query(
                DBHelperUser.TABLE,
                null,
                DBHelperUser.COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)},
                null, null, null
        );
        User User = null;
        if(cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperUser.COLUMN_EMAIL));
            User = new User(id, email);
        }
        cursor.close();
        return User;
    }

    public User login(String email, String password) {
        Cursor cursor = db.query(
                DBHelperUser.TABLE,
                null,
                DBHelperUser.COLUMN_EMAIL + " = ? AND " + DBHelperUser.COLUMN_PASSWORD + " = ?",
                new String[] {email, password},
                null, null, null
        );
        User User = null;
        if(cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelperUser.COLUMN_ID));

            User = new User(id, email);
        }
        cursor.close();
        return User;
    }
}
