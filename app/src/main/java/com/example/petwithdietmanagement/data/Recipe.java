package com.example.petwithdietmanagement.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {
    private int id;
    private String recipe_name;
    private String cooking_method;
    private String ingredients;
    private Nutrients nutrients;
    private String cooking_time;
    private String serving_weight;
    private String dish_type;
    private Images images;
    private List<String> manual_steps;
    private List<String> manual_images ;

    // 영양소 정보를 위한 중첩 클래스
    public static class Nutrients {
        private Double calories;
        private Double protein;
        private Double fat;
        private Double carbohydrate;
        private Double sodium;

        public Double getCalories() { return calories; }
        public void setCalories(Double calories) { this.calories = calories; }

        public Double getProtein() { return protein; }
        public void setProtein(Double protein) { this.protein = protein; }

        public Double getFat() { return fat; }
        public void setFat(Double fat) { this.fat = fat; }

        public Double getCarbohydrate() { return carbohydrate; }
        public void setCarbohydrate(Double carbohydrate) { this.carbohydrate = carbohydrate; }

        public Double getSodium() { return sodium; }
        public void setSodium(Double sodium) { this.sodium = sodium; }

    }

    public static class Images{
        private String preview_image;
        private String ingredient_preview_image;
        public String getPreviewImage() { return preview_image; }
        public void setPreviewImage(String previewImage) { this.preview_image = previewImage; }

        public String getIngredientPreviewImage() { return ingredient_preview_image; }
        public void setIngredientPreviewImage(String ingredientPreviewImage) { this.ingredient_preview_image = ingredientPreviewImage; }


    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRecipeName() { return recipe_name; }
    public void setRecipeName(String name) { this.recipe_name = name; }

    public String getCookingMethod() { return cooking_method; }
    public void setCookingMethod(String cookingMethod) { this.cooking_method = cookingMethod; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public Nutrients getNutrients() { return nutrients; }
    public void setNutrients(Nutrients nutrients) { this.nutrients = nutrients; }

    public String getCookingTime() {
        return cooking_time;
    }

    public String getServingWeight() {
        return serving_weight;
    }

    public String getDishType() { return dish_type; }
    public void setDishType(String dishType) { this.dish_type = dishType; }

    public Images getImages() { return images; }
    public void setImages(Images images) { this.images = images; }

    public List<String> getManualSteps() { return manual_steps; }
    public void setManualSteps(List<String> manualSteps) { this.manual_steps = manualSteps; }

    public List<String> getManualImages() { return manual_images; }
    public void setManualImages(List<String> manualImages) { this.manual_images = manualImages; }
}

