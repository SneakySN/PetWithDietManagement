package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.User;
import com.example.petwithdietmanagement.data.User.Items;
import com.example.petwithdietmanagement.data.User.HealthInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class UserDBManager {
    private UserDBHelper dbHelper;
    private SQLiteDatabase database;

    public UserDBManager(Context context) {
        dbHelper = new UserDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Insert user data from JSON string
    public void insertUserData(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("user");

        ContentValues values = new ContentValues();
        values.put(UserDBHelper.COLUMN_USER_ID, userObject.getString("userId"));
        values.put(UserDBHelper.COLUMN_NAME, userObject.getString("name"));
        values.put(UserDBHelper.COLUMN_PASSWORD, userObject.getString("password"));
        values.put(UserDBHelper.COLUMN_PROFILE_PICTURE, userObject.getString("profile_picture"));
        values.put(UserDBHelper.COLUMN_GOALS, userObject.getString("goals"));

        JSONObject healthInfoObject = userObject.getJSONObject("health_info");
        values.put(UserDBHelper.COLUMN_WEIGHT, healthInfoObject.getInt("weight"));
        values.put(UserDBHelper.COLUMN_HEIGHT, healthInfoObject.getInt("height"));
        values.put(UserDBHelper.COLUMN_BLOOD_PRESSURE, healthInfoObject.getInt("blood_pressure"));

        values.put(UserDBHelper.COLUMN_GOLD, userObject.getInt("gold"));

        database.insert(UserDBHelper.TABLE_USERS, null, values);

        JSONArray itemsArray = userObject.getJSONArray("items");
        for (int j = 0; j < itemsArray.length(); j++) {
            JSONObject itemObject = itemsArray.getJSONObject(j);

            ContentValues itemValues = new ContentValues();
            itemValues.put(UserDBHelper.COLUMN_USER_ID, userObject.getString("userId"));
            itemValues.put(UserDBHelper.COLUMN_ITEM_ID, itemObject.getInt("id"));
            itemValues.put(UserDBHelper.COLUMN_EQUIPPED, itemObject.getInt("equipped"));

            database.insert(UserDBHelper.TABLE_USER_ITEMS, null, itemValues);
        }
    }

    // Insert user items
    private void insertUserItems(String userId, List<Items> items) {
        for (Items item : items) {
            ContentValues values = new ContentValues();
            values.put(UserDBHelper.COLUMN_USER_ID, userId);
            values.put(UserDBHelper.COLUMN_ITEM_ID, item.getId());
            values.put(UserDBHelper.COLUMN_EQUIPPED, item.isEquipped());
            database.insert(UserDBHelper.TABLE_USER_ITEMS, null, values);
        }
    }

    // Get user by user ID
    public User getUserById(String userId) {
        User user = null;
        String query = "SELECT * FROM " + UserDBHelper.TABLE_USERS + " WHERE " + UserDBHelper.COLUMN_USER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userId});

        if (cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_PASSWORD)));
            user.setProfile_picture(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_PROFILE_PICTURE)));
            user.setGoals(cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_GOALS)));

            HealthInfo healthInfo = new HealthInfo();
            healthInfo.setWeight(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_WEIGHT)));
            healthInfo.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_HEIGHT)));
            healthInfo.setBlood_pressure(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_BLOOD_PRESSURE)));
            user.setHealth_info(healthInfo);

            user.setGold(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_GOLD)));
            user.setItems(getItemsByUserId(userId));
        }
        cursor.close();
        return user;
    }

    // Get items by user ID
    private List<Items> getItemsByUserId(String userId) {
        List<Items> items = new ArrayList<>();
        String query = "SELECT * FROM " + UserDBHelper.TABLE_USER_ITEMS + " WHERE " + UserDBHelper.COLUMN_USER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userId});

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_ITEM_ID)));
            item.setEquipped(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_EQUIPPED)));
            items.add(item);
        }
        cursor.close();
        return items;
    }

    // Update user
    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBHelper.COLUMN_NAME, user.getName());
        values.put(UserDBHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(UserDBHelper.COLUMN_PROFILE_PICTURE, user.getProfile_picture());
        values.put(UserDBHelper.COLUMN_GOALS, user.getGoals());

        HealthInfo healthInfo = user.getHealth_info();
        if (healthInfo != null) {
            values.put(UserDBHelper.COLUMN_WEIGHT, healthInfo.getWeight());
            values.put(UserDBHelper.COLUMN_HEIGHT, healthInfo.getHeight());
            values.put(UserDBHelper.COLUMN_BLOOD_PRESSURE, healthInfo.getBlood_pressure());
        }

        values.put(UserDBHelper.COLUMN_GOLD, user.getGold());
        String whereClause = UserDBHelper.COLUMN_USER_ID + "=?";
        String[] whereArgs = { user.getUserId() };

        database.update(UserDBHelper.TABLE_USERS, values, whereClause, whereArgs);
        updateUserItems(user.getUserId(), user.getItems());
    }

    // Add item to user
    public void addItemToUser(String userId, int itemId, boolean equipped) {
        ContentValues values = new ContentValues();
        values.put(UserDBHelper.COLUMN_USER_ID, userId);
        values.put(UserDBHelper.COLUMN_ITEM_ID, itemId);
        values.put(UserDBHelper.COLUMN_EQUIPPED, equipped ? 1 : 0);

        database.insert(UserDBHelper.TABLE_USER_ITEMS, null, values);
    }

    // Update user items
    private void updateUserItems(String userId, List<Items> items) {
        deleteUserItems(userId);
        insertUserItems(userId, items);
    }

    // Delete user items by user ID
    private void deleteUserItems(String userId) {
        String whereClause = UserDBHelper.COLUMN_USER_ID + "=?";
        String[] whereArgs = { userId };
        database.delete(UserDBHelper.TABLE_USER_ITEMS, whereClause, whereArgs);
    }

    // Delete user
    public void deleteUser(String userId) {
        deleteUserItems(userId);
        String whereClause = UserDBHelper.COLUMN_USER_ID + "=?";
        String[] whereArgs = { userId };

        database.delete(UserDBHelper.TABLE_USERS, whereClause, whereArgs);
    }

    // Check if the database is empty
    public boolean isDatabaseEmpty() {
        String query = "SELECT COUNT(*) FROM " + UserDBHelper.TABLE_USERS;
        Cursor cursor = database.rawQuery(query, null);
        boolean isEmpty = false;

        if (cursor.moveToFirst()) {
            isEmpty = cursor.getInt(0) == 0;
        }

        cursor.close();
        return isEmpty;
    }

    public void close() {
        dbHelper.close();
    }
}
