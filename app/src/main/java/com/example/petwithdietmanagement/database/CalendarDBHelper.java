package com.example.petwithdietmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_CALENDAR = "calendar";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MEALTIME = "mealtime";
    public static final String COLUMN_FOOD_ID = "food_id";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CALENDAR + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_MEALTIME + " TEXT, " +
                    COLUMN_FOOD_ID + " INTEGER);";

    public CalendarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
        onCreate(db);
    }
}
