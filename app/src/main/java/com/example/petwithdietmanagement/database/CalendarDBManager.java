package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.Calendar_update;

import java.util.ArrayList;
import java.util.List;

public class CalendarDBManager {
    private CalendarDBHelper dbHelper;
    private SQLiteDatabase database;

    public CalendarDBManager(Context context) {
        dbHelper = new CalendarDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // 새로운 캘린더 데이터를 데이터베이스에 삽입합니다.
    public void insertCalendarData(String userId, String date, String mealtime, int foodId) {
        int newId = getNextId(); // 새 항목의 ID를 가져옵니다.
        ContentValues values = new ContentValues();
        values.put(CalendarDBHelper.COLUMN_ID, newId);
        values.put(CalendarDBHelper.COLUMN_USER_ID, userId);
        values.put(CalendarDBHelper.COLUMN_DATE, date);
        values.put(CalendarDBHelper.COLUMN_MEALTIME, mealtime);
        values.put(CalendarDBHelper.COLUMN_FOOD_ID, foodId);

        database.insert(CalendarDBHelper.TABLE_CALENDAR, null, values); // 데이터베이스에 항목을 삽입합니다.
    }

    // 새 캘린더 항목을 위한 다음 ID를 반환합니다. 데이터베이스가 비어 있는 경우 1을 반환합니다.
    private int getNextId() {
        if (isDatabaseEmpty()) { // 데이터베이스가 비어있는지 확인합니다.
            return 1;
        } else {
            Cursor cursor = database.rawQuery("SELECT MAX(" + CalendarDBHelper.COLUMN_ID + ") FROM " + CalendarDBHelper.TABLE_CALENDAR, null);
            int maxId = 0;
            if (cursor.moveToFirst()) {
                maxId = cursor.getInt(0); // 가장 큰 ID 값을 가져옵니다.
            }
            cursor.close();
            return maxId + 1;
        }
    }

    // 인수로 받는 조건에 따라 데이터를 가져오는 메소드
    public List<Calendar_update> getCalendarData(String userId, String... conditions) {
        List<Calendar_update> calendarData = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM " + CalendarDBHelper.TABLE_CALENDAR + " WHERE " + CalendarDBHelper.COLUMN_USER_ID + " = ?");
        List<String> selectionArgsList = new ArrayList<>();
        selectionArgsList.add(userId);

        if (conditions.length > 0) {
            query.append(" AND ").append(CalendarDBHelper.COLUMN_DATE).append(" = ?");
            selectionArgsList.add(conditions[0]);
        }
        if (conditions.length > 1) {
            query.append(" AND ").append(CalendarDBHelper.COLUMN_MEALTIME).append(" = ?");
            selectionArgsList.add(conditions[1]);
        }
        if (conditions.length > 2) {
            query.append(" AND ").append(CalendarDBHelper.COLUMN_FOOD_ID).append(" = ?");
            selectionArgsList.add(conditions[2]);
        }

        String[] selectionArgs = selectionArgsList.toArray(new String[0]);
        Cursor cursor = database.rawQuery(query.toString(), selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Calendar_update calendar = new Calendar_update();
                calendar.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(CalendarDBHelper.COLUMN_USER_ID)));
                calendar.setDate(cursor.getString(cursor.getColumnIndexOrThrow(CalendarDBHelper.COLUMN_DATE)));
                calendar.setMealtime(cursor.getString(cursor.getColumnIndexOrThrow(CalendarDBHelper.COLUMN_MEALTIME)));
                calendar.setFoodId(cursor.getInt(cursor.getColumnIndexOrThrow(CalendarDBHelper.COLUMN_FOOD_ID)));
                calendarData.add(calendar);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return calendarData;
    }

    // 인수로 받는 조건에 따라 캘린더 데이터를 삭제합니다.
    public void deleteCalendarData(String userId, String... conditions) {
        StringBuilder whereClause = new StringBuilder(CalendarDBHelper.COLUMN_USER_ID + "=?");
        List<String> whereArgsList = new ArrayList<>();
        whereArgsList.add(userId);

        if (conditions.length > 0) {
            whereClause.append(" AND ").append(CalendarDBHelper.COLUMN_DATE).append("=?");
            whereArgsList.add(conditions[0]);
        }
        if (conditions.length > 1) {
            whereClause.append(" AND ").append(CalendarDBHelper.COLUMN_MEALTIME).append("=?");
            whereArgsList.add(conditions[1]);
        }
        if (conditions.length > 2) {
            whereClause.append(" AND ").append(CalendarDBHelper.COLUMN_FOOD_ID).append("=?");
            whereArgsList.add(conditions[2]);
        }

        String[] whereArgs = whereArgsList.toArray(new String[0]);
        database.delete(CalendarDBHelper.TABLE_CALENDAR, whereClause.toString(), whereArgs); // 조건에 맞는 데이터를 삭제합니다.
    }

    // 데이터베이스가 비어있는지 확인합니다.
    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + CalendarDBHelper.TABLE_CALENDAR, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isEmpty = count == 0;
        }
        if (cursor != null) {
            cursor.close();
        }
        return isEmpty;
    }

    // 데이터베이스를 닫습니다.
    public void close() {
        dbHelper.close();
    }
}
