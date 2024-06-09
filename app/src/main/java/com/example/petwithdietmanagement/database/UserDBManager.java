package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.Item;
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
    private ItemDBManager itemDBManager;

    public UserDBManager(Context context) {
        dbHelper = new UserDBHelper(context);
        database = dbHelper.getWritableDatabase();
        itemDBManager = new ItemDBManager(context); // Context를 사용하여 ItemDBManager 초기화
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
    public List<Items> getItemsByUserId(String userId) {
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

    public List<Integer> getUserItemIds(String userId) {
        List<Integer> itemIds = new ArrayList<>();
        String query = "SELECT " + UserDBHelper.COLUMN_ITEM_ID + " FROM " + UserDBHelper.TABLE_USER_ITEMS + " WHERE " + UserDBHelper.COLUMN_USER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userId});

        while (cursor.moveToNext()) {
            itemIds.add(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_ITEM_ID)));
        }
        cursor.close();
        return itemIds;
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

    // Update equipped status
    public void updateEquipped(String userId, int newItemId, boolean isDeselected) {
        // 새 아이템의 타입을 가져오기
        Item newItem = newItemId > 0 ? itemDBManager.getItemById(newItemId) : null;
        String itemType = newItem != null ? newItem.getItemType() : null;

        // 현재 착용 중인 동일 타입 아이템의 equipped 값을 0으로 업데이트
        if (!isDeselected && itemType != null) {
            List<Item> allItems = itemDBManager.getAllItems();
            List<Integer> itemIdsOfType = new ArrayList<>();

            for (Item item : allItems) {
                if (item.getItemType().equals(itemType)) {
                    itemIdsOfType.add(item.getItemId());
                }
            }

            String itemIdsString = itemIdsOfType.toString().replace("[", "").replace("]", "");
            String updateCurrentEquippedQuery = "UPDATE user_items SET equipped = 0 WHERE user_id = ? AND item_id IN (" + itemIdsString + ")";
            database.execSQL(updateCurrentEquippedQuery, new Object[]{userId});
        }

        // 새로운 아이템의 equipped 값을 1로 업데이트 (아이템 해제 시에는 실행되지 않음)
        if (!isDeselected && newItemId > 0) {
            String updateNewEquippedQuery = "UPDATE user_items SET equipped = 1 WHERE user_id = ? AND item_id = ?";
            database.execSQL(updateNewEquippedQuery, new Object[]{userId, newItemId});
        } else if (isDeselected && newItemId > 0) {
            // 특정 아이템의 equipped 값을 0으로 업데이트
            String updateDeselectedEquippedQuery = "UPDATE user_items SET equipped = 0 WHERE user_id = ? AND item_id = ?";
            database.execSQL(updateDeselectedEquippedQuery, new Object[]{userId, newItemId});
        }
    }

    public List<Integer> getEquippedItemIds(String userId) {
        List<Integer> itemIds = new ArrayList<>();
        String query = "SELECT item_id FROM user_items WHERE user_id = ? AND equipped = 1";
        Cursor cursor = database.rawQuery(query, new String[]{userId});

        while (cursor.moveToNext()) {
            itemIds.add(cursor.getInt(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_ITEM_ID)));
        }
        cursor.close();
        return itemIds;
    }
}