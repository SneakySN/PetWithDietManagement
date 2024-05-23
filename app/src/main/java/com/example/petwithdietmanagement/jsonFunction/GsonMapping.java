package com.example.petwithdietmanagement.jsonFunction;

import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Missions;
import com.example.petwithdietmanagement.data.Pets;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.data.Items;
import com.example.petwithdietmanagement.data.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class GsonMapping {
    public Map<String, Recipe> getRecipes(Reader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String,Recipe>>(){}.getType());
    }

    public Users getUsers(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Users.class);
    }

    public Pets getPets(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Pets.class);
    }

    public Calendar getCalendar(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Calendar.class);
    }

    public Items getItems(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Items.class);
    }

    public Missions getMissions(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Missions.class);
    }


}
