package com.example.petwithdietmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_ITEMS = "items";
    public static final String TEMP_TABLE_ITEMS = "items_temp";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_TYPE = "item_type";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_PRICE = "item_price";
    public static final String COLUMN_ITEM_IMAGE = "item_image";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PURCHASED = "purchased";
    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, TABLE_ITEMS);
    }

    private void createTable(SQLiteDatabase db, String tableName) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + tableName + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ITEM_TYPE + " TEXT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " INTEGER,"
                + COLUMN_ITEM_IMAGE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PURCHASED + " INTEGER" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // 1. 새 테이블 생성
            createTable(db, TEMP_TABLE_ITEMS);

            // 2. 기존 데이터 복사
            String insertData = "INSERT INTO " + TEMP_TABLE_ITEMS + " SELECT *, 0 FROM " + TABLE_ITEMS;
            db.execSQL(insertData);

            // 3. 기존 테이블 삭제
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

            // 4. 새 테이블 이름 변경
            db.execSQL("ALTER TABLE " + TEMP_TABLE_ITEMS + " RENAME TO " + TABLE_ITEMS);
        }
    }
}
