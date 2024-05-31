package com.example.petwithdietmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petwithdietmanagement.data.Mission;

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
    public void insertMission(String missionName, String description, String missionType, Integer missionTarget, String missionUnit, Integer reward) {
        ContentValues values = new ContentValues();
        values.put(MissionDBHelper.COLUMN_MISSION_ID, getNextMissionId());
        values.put(MissionDBHelper.COLUMN_MISSION_NAME, missionName);
        values.put(MissionDBHelper.COLUMN_DESCRIPTION, description);
        values.put(MissionDBHelper.COLUMN_MISSION_TYPE, missionType);
        values.put(MissionDBHelper.COLUMN_MISSION_TARGET, missionTarget);
        values.put(MissionDBHelper.COLUMN_MISSION_UNIT, missionUnit);
        values.put(MissionDBHelper.COLUMN_REWARD, reward);

        database.insert(MissionDBHelper.TABLE_MISSIONS, null, values);
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

    // 데이터베이스 닫기
    public void close() {
        dbHelper.close();
    }
}
