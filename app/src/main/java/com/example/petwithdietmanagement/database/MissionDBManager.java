package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.Mission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MissionDBManager {
    private MissionDBHelper dbHelper;
    private SQLiteDatabase database;

    public MissionDBManager(Context context) {
        dbHelper = new MissionDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // 미션 데이터 삽입 메소드
    public void insertMissionDataFromJson(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray missionsArray = jsonObject.getJSONArray("missions");

        for (int i = 0; i < missionsArray.length(); i++) {
            JSONObject missionObject = missionsArray.getJSONObject(i);

            ContentValues values = new ContentValues();
            values.put(MissionDBHelper.COLUMN_MISSION_ID, missionObject.getInt("id"));
            values.put(MissionDBHelper.COLUMN_MISSION_NAME, missionObject.getString("name"));
            values.put(MissionDBHelper.COLUMN_DESCRIPTION, missionObject.getString("description"));
            values.put(MissionDBHelper.COLUMN_MISSION_TYPE, missionObject.getString("type"));
            values.put(MissionDBHelper.COLUMN_MISSION_TARGET, missionObject.getInt("target"));
            values.put(MissionDBHelper.COLUMN_MISSION_UNIT, missionObject.getString("unit"));
            values.put(MissionDBHelper.COLUMN_REWARD, missionObject.getInt("reward"));

            database.insert(MissionDBHelper.TABLE_MISSIONS, null, values);
        }
    }

    // 다음 미션 ID를 가져오는 메소드
    private int getNextMissionId() {
        Cursor cursor = database.rawQuery("SELECT MAX(" + MissionDBHelper.COLUMN_MISSION_ID + ") FROM " + MissionDBHelper.TABLE_MISSIONS, null);
        int maxId = 0;
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();
        return maxId + 1;
    }

    // 모든 미션 데이터 가져오기 메소드
    public List<Mission> getAllMissions() {
        List<Mission> missions = new ArrayList<>();
        Cursor cursor = database.query(MissionDBHelper.TABLE_MISSIONS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Mission mission = new Mission();
                mission.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_ID)));
                mission.setName(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_NAME)));
                mission.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_DESCRIPTION)));
                mission.setType(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_TYPE)));
                mission.setTarget(cursor.getInt(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_TARGET)));
                mission.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_UNIT)));
                mission.setReward(cursor.getInt(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_REWARD)));

                missions.add(mission);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return missions;
    }

    // 특정 미션 데이터 가져오기 메소드
    public Mission getMissionById(int missionId) {
        Mission mission = null;
        String query = "SELECT * FROM " + MissionDBHelper.TABLE_MISSIONS + " WHERE " + MissionDBHelper.COLUMN_MISSION_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(missionId)});

        if (cursor.moveToFirst()) {
            mission = new Mission();
            mission.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_ID)));
            mission.setName(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_NAME)));
            mission.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_DESCRIPTION)));
            mission.setType(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_TYPE)));
            mission.setTarget(cursor.getInt(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_TARGET)));
            mission.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_MISSION_UNIT)));
            mission.setReward(cursor.getInt(cursor.getColumnIndexOrThrow(MissionDBHelper.COLUMN_REWARD)));
        }

        cursor.close();
        return mission;
    }

    // 미션 데이터 업데이트 메소드
    public void updateMission(Mission mission) {
        ContentValues values = new ContentValues();
        values.put(MissionDBHelper.COLUMN_MISSION_NAME, mission.getName());
        values.put(MissionDBHelper.COLUMN_DESCRIPTION, mission.getDescription());
        values.put(MissionDBHelper.COLUMN_MISSION_TYPE, mission.getType());
        values.put(MissionDBHelper.COLUMN_MISSION_TARGET, mission.getTarget());
        values.put(MissionDBHelper.COLUMN_MISSION_UNIT, mission.getUnit());
        values.put(MissionDBHelper.COLUMN_REWARD, mission.getReward());
        String whereClause = MissionDBHelper.COLUMN_MISSION_ID + "=?";
        String[] whereArgs = { String.valueOf(mission.getId()) };

        database.update(MissionDBHelper.TABLE_MISSIONS, values, whereClause, whereArgs);
    }

    // 미션 데이터 삭제 메소드
    public void deleteMission(int missionId) {
        String whereClause = MissionDBHelper.COLUMN_MISSION_ID + "=?";
        String[] whereArgs = { String.valueOf(missionId) };

        database.delete(MissionDBHelper.TABLE_MISSIONS, whereClause, whereArgs);
    }

    // MissionDBManager 클래스에 데이터베이스가 비어 있는지 확인하는 메소드 추가
    public boolean isDatabaseEmpty() {
        String query = "SELECT COUNT(*) FROM " + MissionDBHelper.TABLE_MISSIONS;
        Cursor cursor = database.rawQuery(query, null);
        boolean isEmpty = false;

        if (cursor.moveToFirst()) {
            isEmpty = cursor.getInt(0) == 0;
        }

        cursor.close();
        return isEmpty;
    }

    // 데이터베이스 닫기
    public void close() {
        dbHelper.close();
    }
}
