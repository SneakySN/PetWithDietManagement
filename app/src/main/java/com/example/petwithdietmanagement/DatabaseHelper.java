package com.example.petwithdietmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_COOKING_METHOD = "cooking_method";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_PROTEIN = "protein";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_CARBOHYDRATE = "carbohydrate";
    public static final String COLUMN_SODIUM = "sodium";
    public static final String COLUMN_DISH_TYPE = "dish_type";
    public static final String COLUMN_PREVIEW_IMAGE = "preview_image";
    public static final String COLUMN_INGREDIENT_PREVIEW_IMAGE = "ingredient_preview_image";
    public static final String COLUMN_MANUAL_STEPS = "manual_steps";
    public static final String COLUMN_MANUAL_IMAGES = "manual_images";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RECIPE_NAME + " TEXT,"
                + COLUMN_COOKING_METHOD + " TEXT,"
                + COLUMN_INGREDIENTS + " TEXT,"
                + COLUMN_CALORIES + " TEXT,"
                + COLUMN_PROTEIN + " TEXT,"
                + COLUMN_FAT + " TEXT,"
                + COLUMN_CARBOHYDRATE + " TEXT,"
                + COLUMN_SODIUM + " TEXT,"
                + COLUMN_DISH_TYPE + " TEXT,"
                + COLUMN_PREVIEW_IMAGE + " TEXT,"
                + COLUMN_INGREDIENT_PREVIEW_IMAGE + " TEXT,"
                + COLUMN_MANUAL_STEPS + " TEXT,"
                + COLUMN_MANUAL_IMAGES + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }
}
