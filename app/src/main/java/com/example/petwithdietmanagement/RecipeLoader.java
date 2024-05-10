package com.example.petwithdietmanagement;

import com.example.petwithdietmanagement.data.Recipe;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class RecipeLoader {

    public static Recipe loadRecipeData() {
        Gson gson = new Gson();
        String filePath = "app/src/main/java/com/example/petwithdietmanagement/data/recipeList.json";
        try (FileReader reader = new FileReader(filePath)) {
            Recipe recipe = gson.fromJson(reader, Recipe.class);
            return recipe;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {


    }
}
