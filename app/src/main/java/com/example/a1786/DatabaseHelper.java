package com.example.a1786;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    static final String DB_NAME = "HIKING.DB";

    // database version
    static final int DB_VERSION = 1;

    // Table Names
    static final String TABLE_USERS = "users";
    static final String TABLE_HIKES = "hikes";
    static final String TABLE_IMAGES = "images";

    // Common column names
    static final String COLUMN_ID = "_id";

    // USERS Table - column names
    static final String COLUMN_USERNAME = "username";
    static final String COLUMN_EMAIL = "email";

    // HIKES Table - column names
    static final String COLUMN_NAME = "name";
    static final String COLUMN_LOCATION = "location";
    static final String COLUMN_DESCRIPTION = "description";

    // IMAGES Table - column names
    static final String COLUMN_PATH = "path";
    static final String COLUMN_HIKE_ID = "hike_id";

    // Table Create Statements
    // User table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT" + ")";

    // Hike table create statement
    private static final String CREATE_TABLE_HIKES = "CREATE TABLE "
            + TABLE_HIKES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_LOCATION + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT" + ")";

    // Image table create statement
    private static final String CREATE_TABLE_IMAGES = "CREATE TABLE "
            + TABLE_IMAGES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PATH + " TEXT,"
            + COLUMN_HIKE_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_HIKE_ID + ") REFERENCES "
            + TABLE_HIKES + "(" + COLUMN_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_HIKES);
        db.execSQL(CREATE_TABLE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);

        // create new tables
        onCreate(db);
    }

    // ADD AND GET HIKING
    public boolean addHike(String name, String location, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_HIKES, null, contentValues);
        db.close();

        // result will be -1 if there was an error inserting the data
        return result != -1;
    }

    public Cursor getAllHikes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HIKES, null);
    }


}
