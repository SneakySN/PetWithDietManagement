package com.example.petwithdietmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MissionDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "missions.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MISSIONS = "missions";
    public static final String COLUMN_MISSION_ID = "mission_id";
    public static final String COLUMN_MISSION_NAME = "mission_name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MISSION_TYPE = "mission_type";
    public static final String COLUMN_MISSION_TARGET = "mission_target";
    public static final String COLUMN_MISSION_UNIT = "mission_unit";
    public static final String COLUMN_REWARD = "reward";

    public MissionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_MISSIONS + "("
                + COLUMN_MISSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MISSION_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_MISSION_TYPE + " TEXT,"
                + COLUMN_MISSION_TARGET + " TEXT,"
                + COLUMN_MISSION_UNIT + " TEXT,"
                + COLUMN_REWARD + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSIONS);
        onCreate(db);
    }
}
