package com.example.petwithdietmanagement.jsonFunction;

import com.example.petwithdietmanagement.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

public class GsonMapping {
    public Map<String, Recipe> getRecipes(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Map<String, Recipe>>(){}.getType());
    }
}
