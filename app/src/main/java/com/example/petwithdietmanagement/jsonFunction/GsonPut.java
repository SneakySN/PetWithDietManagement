package com.example.petwithdietmanagement.jsonFunction;

import com.example.petwithdietmanagement.data.Calendar;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class GsonPut {

    public static void addMealsToCalendar(Calendar calendar, String userId, String date, Calendar.User.Meals meals) {
        Calendar.User user = calendar.getUsers().get(userId);
        if (user == null) {
            user = new Calendar.User();
            calendar.getUsers().put(userId, user);
        }
        user.getFood_log().put(date, meals);
    }

    public static void saveCalendarToJson(Calendar calendar, String filePath) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath, false)) { // 덮어쓰기 위해 append 모드를 false로 설정
            gson.toJson(calendar, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
