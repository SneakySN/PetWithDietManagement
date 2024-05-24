package com.example.petwithdietmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public RecipeDatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void insertRecipeData(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray keys = jsonObject.names();

        for (int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            JSONObject recipe = jsonObject.getJSONObject(key);

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_RECIPE_NAME, recipe.getString("recipe_name"));
            values.put(DatabaseHelper.COLUMN_COOKING_METHOD, recipe.getString("cooking_method"));
            values.put(DatabaseHelper.COLUMN_INGREDIENTS, recipe.getString("ingredients"));
            JSONObject nutrients = recipe.getJSONObject("nutrients");
            values.put(DatabaseHelper.COLUMN_CALORIES, nutrients.getString("calories"));
            values.put(DatabaseHelper.COLUMN_PROTEIN, nutrients.getString("protein"));
            values.put(DatabaseHelper.COLUMN_FAT, nutrients.getString("fat"));
            values.put(DatabaseHelper.COLUMN_CARBOHYDRATE, nutrients.getString("carbohydrate"));
            values.put(DatabaseHelper.COLUMN_SODIUM, nutrients.getString("sodium"));
            values.put(DatabaseHelper.COLUMN_DISH_TYPE, recipe.getString("dish_type"));
            JSONObject images = recipe.getJSONObject("images");
            values.put(DatabaseHelper.COLUMN_PREVIEW_IMAGE, images.getString("preview_image"));
            values.put(DatabaseHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE, images.getString("ingredient_preview_image"));
            values.put(DatabaseHelper.COLUMN_MANUAL_STEPS, new JSONArray(recipe.getString("manual_steps")).toString());
            values.put(DatabaseHelper.COLUMN_MANUAL_IMAGES, new JSONArray(recipe.getString("manual_images")).toString());

            database.insert(DatabaseHelper.TABLE_RECIPES, null, values);
        }
    }

    public JSONArray getRecipes() throws JSONException {
        JSONArray resultArray = new JSONArray();
        Cursor cursor = database.query(DatabaseHelper.TABLE_RECIPES, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            JSONObject recipe = new JSONObject();
            recipe.put(DatabaseHelper.COLUMN_RECIPE_NAME, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RECIPE_NAME)));
            recipe.put(DatabaseHelper.COLUMN_COOKING_METHOD, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COOKING_METHOD)));
            recipe.put(DatabaseHelper.COLUMN_INGREDIENTS, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INGREDIENTS)));
            recipe.put(DatabaseHelper.COLUMN_CALORIES, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CALORIES)));
            recipe.put(DatabaseHelper.COLUMN_PROTEIN, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROTEIN)));
            recipe.put(DatabaseHelper.COLUMN_FAT, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FAT)));
            recipe.put(DatabaseHelper.COLUMN_CARBOHYDRATE, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CARBOHYDRATE)));
            recipe.put(DatabaseHelper.COLUMN_SODIUM, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SODIUM)));
            recipe.put(DatabaseHelper.COLUMN_DISH_TYPE, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISH_TYPE)));
            recipe.put(DatabaseHelper.COLUMN_PREVIEW_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PREVIEW_IMAGE)));
            recipe.put(DatabaseHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE)));
            recipe.put(DatabaseHelper.COLUMN_MANUAL_STEPS, new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MANUAL_STEPS))));
            recipe.put(DatabaseHelper.COLUMN_MANUAL_IMAGES, new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MANUAL_IMAGES))));

            resultArray.put(recipe);
        }
        cursor.close();
        return resultArray;
    }

    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_RECIPES, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isEmpty = count == 0;
        }
        if (cursor != null) {
            cursor.close();
        }
        return isEmpty;
    }
    public void close() {
        dbHelper.close();
    }
}