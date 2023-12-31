package com.example.a1786;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HikeDatabase";

    // Table name
    private static final String TABLE_HIKES = "hikes";
    public static final String TABLE_OBSERVATIONS = "observations";

    // Hikes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DATE = "date";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_PARKING = "parking";
    private static final String KEY_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HIKES_TABLE = "CREATE TABLE " + TABLE_HIKES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOCATION + " TEXT," + KEY_DATE + " TEXT," + KEY_LENGTH + " TEXT,"
                + KEY_LEVEL + " TEXT," + KEY_PARKING + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_HIKES_TABLE);
        String CREATE_OBSERVATIONS_TABLE =
                "CREATE TABLE " + TABLE_OBSERVATIONS + " (observation_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hike_id INTEGER, observation TEXT, time_of_observation TEXT, additional_comments TEXT, " +
                        "image BLOB, " +
                        "FOREIGN KEY (hike_id) REFERENCES " + TABLE_HIKES + " (hike_id));";
        db.execSQL(CREATE_OBSERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKES);

        // Create tables again
        onCreate(db);
    }

    public boolean addHike(Hiking hike) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, hike.getName());
        values.put(KEY_LOCATION, hike.getLocation());
        values.put(KEY_DATE, hike.getDate());
        values.put(KEY_LENGTH, hike.getLength());
        values.put(KEY_LEVEL, hike.getLevel());
        values.put(KEY_PARKING, hike.getParking());
        values.put(KEY_DESCRIPTION, hike.getDescription());

        // Inserting Row
        db.insert(TABLE_HIKES, null, values);
        db.close(); // Closing database connection

        return true;
    }

    public Hiking getHike(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HIKES, new String[] { KEY_ID, KEY_NAME, KEY_LOCATION, KEY_DATE, KEY_LENGTH, KEY_LEVEL, KEY_PARKING, KEY_DESCRIPTION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Hiking hike = new Hiking(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return hike;
    }
    public boolean updateHike(int hikeId, String name, String location, String date,
                          String length, String level, String parking, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_LOCATION, location);
        values.put(KEY_DATE, date);
        values.put(KEY_LENGTH, length);
        values.put(KEY_LEVEL, level);
        values.put(KEY_PARKING, parking);
        values.put(KEY_DESCRIPTION, description);

        int rowsAffected = db.update(TABLE_HIKES, values, KEY_ID + " = ?", new String[]{String.valueOf(hikeId)});

        db.close();

        // Return true if at least one line is updated, otherwise return false
        return rowsAffected > 0;
    }

    public void deleteAllHike(Hiking hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIKES, KEY_ID + " = ?",
                new String[] { String.valueOf(hike.getId()) });
        db.close();
    }

    public boolean deleteHikeById(int hikeId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_HIKES, "id = ?", new String[]{String.valueOf(hikeId)});
            db.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Hiking> getAllHikes() {
        List<Hiking> hikeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HIKES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hiking hike = new Hiking();
                hike.setId(cursor.getInt(0));
                hike.setName(cursor.getString(1));
                hike.setLocation(cursor.getString(2));
                hike.setDate(cursor.getString(3));
                hike.setLength(cursor.getString(4));
                hike.setLevel(cursor.getString(5));
                hike.setParking(cursor.getString(6));
                hike.setDescription(cursor.getString(7));

                // Adding hike to list
                hikeList.add(hike);
            } while (cursor.moveToNext());
        }

        // return hike list
        return hikeList;
    }

    //------------------------------OBSERVATION-----------------------------------------------------
    // Add a Observation
    public long addObservation(Observation observation) {
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hike_id", observation.getHikeId());
        values.put("observation", observation.getObservation());
        values.put("time_of_observation", observation.getTimeOfObservation());
        values.put("additional_comments", observation.getAdditionalComments());
        values.put("image", observation.getImageBytes());

        try {
            result = db.insert(TABLE_OBSERVATIONS, null, values);
        } catch (Exception e) {
        } finally {
            db.close();
        }

        return result;
    }
    public boolean addImageToObservation(int observationId, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", imageBytes);

        int rowsAffected = db.update(TABLE_OBSERVATIONS, values, "observation_id = ?",
                new String[]{String.valueOf(observationId)});

        db.close();

        return rowsAffected > 0;
    }


    // Update a Observation
    public boolean updateObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("observation", observation.getObservation());
        values.put("time_of_observation", observation.getTimeOfObservation());
        values.put("additional_comments", observation.getAdditionalComments());
        values.put("image", observation.getImageBytes());

        int rowsAffected = db.update(TABLE_OBSERVATIONS, values, "observation_id = ?",
                new String[]{String.valueOf(observation.getObservationId())});

        db.close();

        return rowsAffected > 0;
    }

    public void deleteObservation(int observationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OBSERVATIONS, "observation_id = ?", new String[]{String.valueOf(observationId)});
        db.close();
    }

    public Observation getObservationById(int observationId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OBSERVATIONS, null, "observation_id = ?",
                new String[]{String.valueOf(observationId)}, null, null, null);

        Observation observation = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                observation = cursorToObservation(cursor);
            }
            cursor.close();
        }

        db.close();

        return observation;
    }

    public List<Observation> getAllObservations() {
        List<Observation> observations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OBSERVATIONS, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Observation observation = cursorToObservation(cursor);
                observations.add(observation);
            }
            cursor.close();
        }

        db.close();

        return observations;
    }

    // Trong DatabaseHelper
    public List<Observation> getAllObservationsForHike(int hikeId) {
        List<Observation> observations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_OBSERVATIONS + " WHERE hike_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hikeId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Observation observation = cursorToObservation(cursor);
                observations.add(observation);
            }
            cursor.close();
        }

        db.close();

        return observations;
    }


    @SuppressLint("Range")
    public Hiking getHikeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("hikes", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Hiking hike = new Hiking();
            hike.setName(cursor.getString(cursor.getColumnIndex("name")));
            hike.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            hike.setDate(cursor.getString(cursor.getColumnIndex("date")));
            hike.setLength(cursor.getString(cursor.getColumnIndex("length")));
            hike.setLevel(cursor.getString(cursor.getColumnIndex("level")));
            hike.setParking(cursor.getString(cursor.getColumnIndex("parking")));
            hike.setDescription(cursor.getString(cursor.getColumnIndex("description")));

            cursor.close();
            db.close();
            return hike;
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }
    @SuppressLint("Range")
    private Observation cursorToObservation(Cursor cursor) {
        Observation observation = new Observation();
        observation.setObservationId(cursor.getInt(cursor.getColumnIndex("observation_id")));
        observation.setObservation(cursor.getString(cursor.getColumnIndex("observation")));
        observation.setTimeOfObservation(cursor.getString(cursor.getColumnIndex("time_of_observation")));
        observation.setAdditionalComments(cursor.getString(cursor.getColumnIndex("additional_comments")));
        observation.setImageBytes(cursor.getBlob(cursor.getColumnIndex("image")));

        // Retrieve the associated Hike
        int hikeId = cursor.getInt(cursor.getColumnIndex("hike_id"));
        Hiking hike = getHikeById(hikeId);
        observation.setHike(hike);

        return observation;
    }
}
