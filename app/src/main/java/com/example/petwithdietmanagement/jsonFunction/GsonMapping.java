package com.example.petwithdietmanagement.jsonFunction;

import android.content.Context;
import android.content.res.AssetManager;
import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Missions;
import com.example.petwithdietmanagement.data.Pets;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.data.Store;
import com.example.petwithdietmanagement.data.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GsonMapping {
    public Map<String, Recipe> getRecipes(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Recipe>>(){}.getType());
    }

    public Map<String, User> getUser(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, User>>(){}.getType());
    }

    public Map<String, Pets> getPets(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Pets>>(){}.getType());
    }

    public Map<String, Calendar> getCalendar(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Calendar>>(){}.getType());
    }

    public Store getStore(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Store.class);
    }

    public Missions getMissions(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Missions.class);
    }

    public Object parseJsonFile(Context context, String fileName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            String json = new String(readAllBytesFromStream(inputStream), StandardCharsets.UTF_8);

            // 파일명에 따라 적절한 메서드를 호출합니다.
            if (fileName.equals("recipeList.json")) {
                return getRecipes(json);
            } else if (fileName.equals("userList.json")) {
                return getUser(json);
            } else if (fileName.equals("petsList.json")) {
                return getPets(json);
            } else if (fileName.equals("calendarList.json")) {
                return getCalendar(json);
            } else if (fileName.equals("storeList.json")) {
                return getStore(json);
            } else if (fileName.equals("missionsList.json")) {
                return getMissions(json);
            } else {
                throw new IllegalArgumentException("Unknown JSON file name");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] readAllBytesFromStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}
