package com.example.petwithdietmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PROFILE_PICTURE = "profile_picture";
    public static final String COLUMN_GOALS = "goals";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_BLOOD_PRESSURE = "blood_pressure";
    public static final String COLUMN_GOLD = "gold";

    public static final String TABLE_USER_ITEMS = "user_items";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_EQUIPPED = "equipped";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PROFILE_PICTURE + " TEXT,"
                + COLUMN_GOALS + " TEXT,"
                + COLUMN_WEIGHT + " INTEGER,"
                + COLUMN_HEIGHT + " INTEGER,"
                + COLUMN_BLOOD_PRESSURE + " INTEGER,"
                + COLUMN_GOLD + " INTEGER" + ")";

        String CREATE_USER_ITEMS_TABLE = "CREATE TABLE " + TABLE_USER_ITEMS + "("
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_ITEM_ID + " INTEGER,"
                + COLUMN_EQUIPPED + " INTEGER,"
                + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_ITEM_ID + "),"
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_USER_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }
}
