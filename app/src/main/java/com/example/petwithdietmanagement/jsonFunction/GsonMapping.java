package com.example.petwithdietmanagement.jsonFunction;

import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Missions;
import com.example.petwithdietmanagement.data.Pets;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.data.Store;
import com.example.petwithdietmanagement.data.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

}
