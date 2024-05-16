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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GsonMapping {
    public Map<String, Recipe> getRecipes(Reader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Recipe>>(){}.getType());
    }

    public Map<String, User> getUser(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, User>>(){}.getType());
    }

    public Map<String, Pets> getPets(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Pets>>(){}.getType());
    }

    public Map<String, Calendar> getCalendar(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Calendar>>(){}.getType());
    }

    public Store getStore(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Store.class);
    }

    public Missions getMissions(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Missions.class);
    }


}
