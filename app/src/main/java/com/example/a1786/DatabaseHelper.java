package com.example.a1786;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hikingDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_HIKING = "CREATE TABLE hiking (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "location TEXT," +
                "date TEXT," +
                "length TEXT," +
                "level TEXT," +
                "parking TEXT" + ")";
        db.execSQL(CREATE_TABLE_HIKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS hiking");
        onCreate(db);
    }

    public boolean addHike(Hiking hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", hike.getName());
        values.put("location", hike.getLocation());
        values.put("date", hike.getDate());
        values.put("length", hike.getLength());
        values.put("level", hike.getLevel());
        values.put("parking", hike.getParking());

        long result = db.insert("hiking", null, values);
        db.close();
        return result != -1;
    }

    public boolean updateHike(Hiking hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", hike.getName());
        values.put("location", hike.getLocation());
        values.put("date", hike.getDate());
        values.put("length", hike.getLength());
        values.put("level", hike.getLevel());
        values.put("parking", hike.getParking());

        int result = db.update("hiking", values, "id = ?", new String[]{String.valueOf(hike.getId())});
        db.close();
        return result > 0;
    }

    public boolean deleteHike(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("hiking", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public Cursor getAllHikesCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM hiking", null);
    }

    @SuppressLint("Range")
    public List<Hiking> getAllHikes() {
        List<Hiking> hikes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hiking", null,null);

        if (cursor.moveToFirst()) {
            do {
                Hiking hike = new Hiking();
                hike.setName(cursor.getString(cursor.getColumnIndex("name")));
                hike.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                hike.setDate(cursor.getString(cursor.getColumnIndex("date")));
                hike.setLength(cursor.getString(cursor.getColumnIndex("length")));
                hike.setLevel(cursor.getString(cursor.getColumnIndex("level")));
                hike.setParking(cursor.getString(cursor.getColumnIndex("parking")));
                hikes.add(hike);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return hikes;
    }
}
