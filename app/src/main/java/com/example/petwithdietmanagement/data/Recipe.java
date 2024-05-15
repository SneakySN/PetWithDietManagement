package com.example.petwithdietmanagement.data;


import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private String recipe_name;
    private String cooking_method;
    private String ingredients;
    private Nutrients nutrients;
    private String cooking_time;
    private String serving_weight;
    private String dish_type;
    private Map<String, String> images = new HashMap<>();
    private Map<String, String> manual_steps = new HashMap<>();
    private Map<String, String> manual_images = new HashMap<>();

    // 영양소 정보를 위한 중첩 클래스
    public static class Nutrients {
        private String calories;
        private String protein;
        private String fat;
        private String carbohydrate;
        private String sodium;

        public String getCalories() {
            return calories;
        }

        public String getProtein() {
            return protein;
        }

        public String getFat() {
            return fat;
        }

        public String getCarbohydrate() {
            return carbohydrate;
        }

        public String getSodium() {
            return sodium;
        }
    }

    public String getRecipeName() {
        return recipe_name;
    }

    public String getCookingMethod() {
        return cooking_method;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public String getCookingTime() {
        return cooking_time;
    }

    public String getServingWeight() {
        return serving_weight;
    }

    public String getDishType() {
        return dish_type;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public Map<String, String> getManualSteps() {
        return manual_steps;
    }

    public Map<String, String> getManualImages() {
        return manual_images;
    }
}

