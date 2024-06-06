package com.example.petwithdietmanagement.jsonFunction;

import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Pet;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.data.User;
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

    public User getUsers(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    public Pet getPets(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Pet.class);
    }

    public Calendar getCalendar(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Calendar.class);
    }

    public Item getItems(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Item.class);
    }

    /*public Mission getMissions(InputStreamReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Mission.class);
    }*/


}
