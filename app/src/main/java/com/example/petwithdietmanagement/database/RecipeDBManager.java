package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeDBManager {
    private RecipeDBHelper dbHelper;
    private SQLiteDatabase database;

    public RecipeDBManager(Context context) {
        dbHelper = new RecipeDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getRecipesByName(String name) {
        String sql = "SELECT * FROM recipes WHERE recipe_name LIKE ?";
        return database.rawQuery(sql, new String[]{"%" + name + "%"});
    }

    public void insertRecipeData(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray keys = jsonObject.names();

        for (int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            JSONObject recipe = jsonObject.getJSONObject(key);

            ContentValues values = new ContentValues();
            values.put(RecipeDBHelper.COLUMN_RECIPE_NAME, recipe.getString("recipe_name"));
            values.put(RecipeDBHelper.COLUMN_COOKING_METHOD, recipe.getString("cooking_method"));
            values.put(RecipeDBHelper.COLUMN_INGREDIENTS, recipe.getString("ingredients"));
            JSONObject nutrients = recipe.getJSONObject("nutrients");
            values.put(RecipeDBHelper.COLUMN_CALORIES, nutrients.getDouble("calories"));
            values.put(RecipeDBHelper.COLUMN_PROTEIN, nutrients.getDouble("protein"));
            values.put(RecipeDBHelper.COLUMN_FAT, nutrients.getDouble("fat"));
            values.put(RecipeDBHelper.COLUMN_CARBOHYDRATE, nutrients.getDouble("carbohydrate"));
            values.put(RecipeDBHelper.COLUMN_SODIUM, nutrients.getDouble("sodium"));
            values.put(RecipeDBHelper.COLUMN_DISH_TYPE, recipe.getString("dish_type"));
            JSONObject images = recipe.getJSONObject("images");
            values.put(RecipeDBHelper.COLUMN_PREVIEW_IMAGE, images.getString("preview_image"));
            values.put(RecipeDBHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE, images.getString("ingredient_preview_image"));
            values.put(RecipeDBHelper.COLUMN_MANUAL_STEPS, new JSONArray(recipe.getString("manual_steps")).toString());
            values.put(RecipeDBHelper.COLUMN_MANUAL_IMAGES, new JSONArray(recipe.getString("manual_images")).toString());

            database.insert(RecipeDBHelper.TABLE_RECIPES, null, values);
        }
    }

    // 정렬된 기준과 선택된 필터링(cookingMethod, dishType)으로 주어진 수 만큼의 레시피 배열을 반환받는 함수
    public JSONArray getRecipes(String orderByColumn, boolean ascending, int limit, String cookingMethod, String dishType) throws JSONException {
        JSONArray resultArray = new JSONArray();
        String order = ascending ? "ASC" : "DESC";
        String orderBy = orderByColumn + " " + order;

        String selection = "";
        List<String> selectionArgsList = new ArrayList<>();

        if (cookingMethod != null && !cookingMethod.isEmpty()) {
            selection += RecipeDBHelper.COLUMN_COOKING_METHOD + "=?";
            selectionArgsList.add(cookingMethod);
        }

        if (dishType != null && !dishType.isEmpty()) {
            if (!selection.isEmpty()) {
                selection += " AND ";
            }
            selection += RecipeDBHelper.COLUMN_DISH_TYPE + "=?";
            selectionArgsList.add(dishType);
        }

        String[] selectionArgs = selectionArgsList.toArray(new String[0]);

        Cursor cursor = database.query(RecipeDBHelper.TABLE_RECIPES, null, selection, selectionArgs, null, null, orderBy, String.valueOf(limit));

        while (cursor.moveToNext()) {
            JSONObject recipe = new JSONObject();
            recipe.put(RecipeDBHelper.COLUMN_RECIPE_NAME, cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_RECIPE_NAME)));
            recipe.put(RecipeDBHelper.COLUMN_COOKING_METHOD, cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_COOKING_METHOD)));
            recipe.put(RecipeDBHelper.COLUMN_INGREDIENTS, cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_INGREDIENTS)));
            recipe.put(RecipeDBHelper.COLUMN_CALORIES, cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_CALORIES)));
            recipe.put(RecipeDBHelper.COLUMN_PROTEIN, cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_PROTEIN)));
            recipe.put(RecipeDBHelper.COLUMN_FAT, cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_FAT)));
            recipe.put(RecipeDBHelper.COLUMN_CARBOHYDRATE, cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_CARBOHYDRATE)));
            recipe.put(RecipeDBHelper.COLUMN_SODIUM, cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_SODIUM)));
            recipe.put(RecipeDBHelper.COLUMN_DISH_TYPE, cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_DISH_TYPE)));
            recipe.put(RecipeDBHelper.COLUMN_PREVIEW_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_PREVIEW_IMAGE)));
            recipe.put(RecipeDBHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE)));
            recipe.put(RecipeDBHelper.COLUMN_MANUAL_STEPS, new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_MANUAL_STEPS))));
            recipe.put(RecipeDBHelper.COLUMN_MANUAL_IMAGES, new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_MANUAL_IMAGES))));

            resultArray.put(recipe);
        }
        cursor.close();
        return resultArray;
    }

    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + RecipeDBHelper.TABLE_RECIPES, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isEmpty = count == 0;
        }
        if (cursor != null) {
            cursor.close();
        }
        return isEmpty;
    }

    // 특정 레시피의 특정 컬럼 데이터를 수정하는 메소드
    public void updateRecipeField(String recipeName, String column, String newValue) {
        ContentValues values = new ContentValues();
        values.put(column, newValue);

        // 업데이트 조건
        String whereClause = RecipeDBHelper.COLUMN_RECIPE_NAME + "=?";
        String[] whereArgs = {recipeName};

        database.update(RecipeDBHelper.TABLE_RECIPES, values, whereClause, whereArgs);
    }

    // id만을 인자로 받아 Recipe 클래스를 반환하는 메소드
    public Recipe getRecipeById(int id) throws JSONException {
        Recipe recipe = null;
        String query = "SELECT * FROM " + RecipeDBHelper.TABLE_RECIPES + " WHERE " + RecipeDBHelper.COLUMN_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            recipe = new Recipe();
            recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_ID)));
            recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_RECIPE_NAME)));
            recipe.setCookingMethod(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_COOKING_METHOD)));
            recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_INGREDIENTS)));
            Recipe.Nutrients nutrients = new Recipe.Nutrients();
            nutrients.setCalories(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_CALORIES)));
            nutrients.setProtein(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_PROTEIN)));
            nutrients.setFat(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_FAT)));
            nutrients.setCarbohydrate(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_CARBOHYDRATE)));
            nutrients.setSodium(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_SODIUM)));
            recipe.setNutrients(nutrients);
            recipe.setDishType(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_DISH_TYPE)));
            Recipe.Images images = new Recipe.Images();
            images.setPreviewImage(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_PREVIEW_IMAGE)));
            images.setIngredientPreviewImage(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE)));
            recipe.setImages(images);
            try {
                recipe.setManualSteps(jsonArrayToList(new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_MANUAL_STEPS)))));
                recipe.setManualImages(jsonArrayToList(new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_MANUAL_IMAGES)))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        cursor.close();
        return recipe;
    }

    //ManualSteps과 ManualImages를 list로 변환시키는 함수
    private List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    // 레시피 이름을 인자로 받아 해당 레시피의 ID를 반환하는 메소드
    public int getRecipeIdByName(String recipeName) {
        int id = -1;
        String query = "SELECT " + RecipeDBHelper.COLUMN_ID + " FROM " + RecipeDBHelper.TABLE_RECIPES + " WHERE " + RecipeDBHelper.COLUMN_RECIPE_NAME + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{recipeName});

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_ID));
        }

        cursor.close();
        return id;
    }

    // Recipe 객체 리스트를 반환하는 메소드 추가
    public List<Recipe> getRecipesByQuery(String query) {
        List<Recipe> recipeList = new ArrayList<>();
        Cursor cursor = getRecipesByName(query);
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_ID)));
                recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_RECIPE_NAME)));
                recipe.setCookingMethod(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_COOKING_METHOD)));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_INGREDIENTS)));
                Recipe.Nutrients nutrients = new Recipe.Nutrients();
                nutrients.setCalories(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_CALORIES)));
                nutrients.setProtein(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_PROTEIN)));
                nutrients.setFat(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_FAT)));
                nutrients.setCarbohydrate(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_CARBOHYDRATE)));
                nutrients.setSodium(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_SODIUM)));
                recipe.setNutrients(nutrients);
                Recipe.Images images = new Recipe.Images();
                images.setPreviewImage(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_PREVIEW_IMAGE)));
                images.setIngredientPreviewImage(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_INGREDIENT_PREVIEW_IMAGE)));
                recipe.setImages(images);
                try {
                    recipe.setManualSteps(jsonArrayToList(new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_MANUAL_STEPS)))));
                    recipe.setManualImages(jsonArrayToList(new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_MANUAL_IMAGES)))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipeList;
    }

    public void close() {
        dbHelper.close();
    }
}
