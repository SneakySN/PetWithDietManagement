package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDBManager {
    private ItemDBHelper dbHelper;
    private SQLiteDatabase database;

    public ItemDBManager(Context context) {
        dbHelper = new ItemDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // 아이템 데이터를 삽입하는 메소드
    public void insertItemData(String itemType, String itemName, int itemPrice, String itemImage, String description, int purchased) {
        ContentValues values = new ContentValues();
        values.put(ItemDBHelper.COLUMN_ITEM_ID, getNextItemId());
        values.put(ItemDBHelper.COLUMN_ITEM_TYPE, itemType);
        values.put(ItemDBHelper.COLUMN_ITEM_NAME, itemName);
        values.put(ItemDBHelper.COLUMN_ITEM_PRICE, itemPrice);
        values.put(ItemDBHelper.COLUMN_ITEM_IMAGE, itemImage);
        values.put(ItemDBHelper.COLUMN_DESCRIPTION, description);
        values.put(ItemDBHelper.COLUMN_PURCHASED, purchased);

        database.insert(ItemDBHelper.TABLE_ITEMS, null, values);
    }

    // 다음 아이템 ID를 가져오는 메소드
    private int getNextItemId() {
        Cursor cursor = database.rawQuery("SELECT MAX(" + ItemDBHelper.COLUMN_ITEM_ID + ") FROM " + ItemDBHelper.TABLE_ITEMS, null);
        int maxId = 0;
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();
        return maxId + 1;
    }

    // Item_type으로 필터링해서 아이템 배열을 반환하는 메소드(Item_type: Hat, Background, Carpet)
    public List<Item> getItemsByType(String itemType) {
        List<Item> itemList = new ArrayList<>();
        String selection = null;
        String[] selectionArgs = null;

        if (itemType != null) {
            selection = ItemDBHelper.COLUMN_ITEM_TYPE + "=?";
            selectionArgs = new String[]{itemType};
        }

        Cursor cursor = database.query(ItemDBHelper.TABLE_ITEMS, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setItemId(cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_ID)));
            item.setItemType(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_TYPE)));
            item.setItemName(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_NAME)));
            item.setItemPrice(cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_PRICE)));
            item.setItemImage(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_IMAGE)));
            item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_DESCRIPTION)));
            item.setPurchased(cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_PURCHASED)));

            itemList.add(item);
        }
        cursor.close();
        return itemList;
    }

    // 특정 아이템 데이터를 수정하는 메소드
    public void updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemDBHelper.COLUMN_ITEM_TYPE, item.getItemType());
        values.put(ItemDBHelper.COLUMN_ITEM_NAME, item.getItemName());
        values.put(ItemDBHelper.COLUMN_ITEM_PRICE, item.getItemPrice());
        values.put(ItemDBHelper.COLUMN_ITEM_IMAGE, item.getItemImage());
        values.put(ItemDBHelper.COLUMN_DESCRIPTION, item.getDescription());
        values.put(ItemDBHelper.COLUMN_PURCHASED, item.isPurchased());

        String whereClause = ItemDBHelper.COLUMN_ITEM_ID + "=?";
        String[] whereArgs = { String.valueOf(item.getItemId()) };

        database.update(ItemDBHelper.TABLE_ITEMS, values, whereClause, whereArgs);
    }

    // id만을 인자로 받아 Item 클래스를 반환하는 메소드
    public Item getItemById(int id) {
        Item item = null;
        String query = "SELECT * FROM " + ItemDBHelper.TABLE_ITEMS + " WHERE " + ItemDBHelper.COLUMN_ITEM_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            item = new Item();
            item.setItemId(cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_ID)));
            item.setItemType(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_TYPE)));
            item.setItemName(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_NAME)));
            item.setItemPrice(cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_PRICE)));
            item.setItemImage(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_IMAGE)));
            item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_DESCRIPTION)));
            item.setPurchased(cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_PURCHASED)));
        }

        cursor.close();
        return item;
    }

    // 아이템 이름을 인자로 받아 해당 아이템의 ID를 반환하는 메소드
    public int getItemIdByName(String itemName) {
        int id = -1;
        String query = "SELECT " + ItemDBHelper.COLUMN_ITEM_ID + " FROM " + ItemDBHelper.TABLE_ITEMS + " WHERE " + ItemDBHelper.COLUMN_ITEM_NAME + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{itemName});

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(ItemDBHelper.COLUMN_ITEM_ID));
        }

        cursor.close();
        return id;
    }

    // 아이템 데이터 삭제 메소드
    public void deleteItem(int id) {
        String whereClause = ItemDBHelper.COLUMN_ITEM_ID + "=?";
        String[] whereArgs = { String.valueOf(id) };

        database.delete(ItemDBHelper.TABLE_ITEMS, whereClause, whereArgs);
    }

    public void close() {
        dbHelper.close();
    }
}
