package com.example.petwithdietmanagement.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
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
    private List<String> manual_images;

    // 영양소 정보를 위한 중첩 클래스
    public static class Nutrients implements Parcelable {
        private Double calories;
        private Double protein;
        private Double fat;
        private Double carbohydrate;
        private Double sodium;

        public Nutrients() {
        }

        protected Nutrients(Parcel in) {
            if (in.readByte() == 0) {
                calories = null;
            } else {
                calories = in.readDouble();
            }
            if (in.readByte() == 0) {
                protein = null;
            } else {
                protein = in.readDouble();
            }
            if (in.readByte() == 0) {
                fat = null;
            } else {
                fat = in.readDouble();
            }
            if (in.readByte() == 0) {
                carbohydrate = null;
            } else {
                carbohydrate = in.readDouble();
            }
            if (in.readByte() == 0) {
                sodium = null;
            } else {
                sodium = in.readDouble();
            }
        }

        public static final Creator<Nutrients> CREATOR = new Creator<Nutrients>() {
            @Override
            public Nutrients createFromParcel(Parcel in) {
                return new Nutrients(in);
            }

            @Override
            public Nutrients[] newArray(int size) {
                return new Nutrients[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (calories == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(calories);
            }
            if (protein == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(protein);
            }
            if (fat == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(fat);
            }
            if (carbohydrate == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(carbohydrate);
            }
            if (sodium == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(sodium);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Double getCalories() {
            return calories;
        }

        public void setCalories(Double calories) {
            this.calories = calories;
        }

        public Double getProtein() {
            return protein;
        }

        public void setProtein(Double protein) {
            this.protein = protein;
        }

        public Double getFat() {
            return fat;
        }

        public void setFat(Double fat) {
            this.fat = fat;
        }

        public Double getCarbohydrate() {
            return carbohydrate;
        }

        public void setCarbohydrate(Double carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public Double getSodium() {
            return sodium;
        }

        public void setSodium(Double sodium) {
            this.sodium = sodium;
        }
    }

    public static class Images implements Parcelable {
        private String preview_image;
        private static String ingredient_preview_image;

        public Images() {
        }

        protected Images(Parcel in) {
            preview_image = in.readString();
            ingredient_preview_image = in.readString();
        }

        public static final Creator<Images> CREATOR = new Creator<Images>() {
            @Override
            public Images createFromParcel(Parcel in) {
                return new Images(in);
            }

            @Override
            public Images[] newArray(int size) {
                return new Images[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(preview_image);
            dest.writeString(ingredient_preview_image);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getPreviewImage() {
            return preview_image;
        }

        public void setPreviewImage(String previewImage) {
            this.preview_image = previewImage;
        }

        public static String getIngredientPreviewImage() {
            return ingredient_preview_image;
        }

        public void setIngredientPreviewImage(String ingredientPreviewImage) {
            ingredient_preview_image = ingredientPreviewImage;
        }
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        recipe_name = in.readString();
        cooking_method = in.readString();
        ingredients = in.readString();
        nutrients = in.readParcelable(Nutrients.class.getClassLoader());
        cooking_time = in.readString();
        serving_weight = in.readString();
        dish_type = in.readString();
        images = in.readParcelable(Images.class.getClassLoader());
        manual_steps = in.createStringArrayList();
        manual_images = in.createStringArrayList();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(recipe_name);
        dest.writeString(cooking_method);
        dest.writeString(ingredients);
        dest.writeParcelable(nutrients, flags);
        dest.writeString(cooking_time);
        dest.writeString(serving_weight);
        dest.writeString(dish_type);
        dest.writeParcelable(images, flags);
        dest.writeStringList(manual_steps);
        dest.writeStringList(manual_images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipe_name;
    }

    public void setRecipeName(String name) {
        this.recipe_name = name;
    }

    public String getCookingMethod() {
        return cooking_method;
    }

    public void setCookingMethod(String cookingMethod) {
        this.cooking_method = cookingMethod;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
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

    public void setDishType(String dishType) {
        this.dish_type = dishType;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getManualSteps() {
        return manual_steps;
    }

    public void setManualSteps(List<String> manualSteps) {
        this.manual_steps = manualSteps;
    }

    public List<String> getManualImages() {
        return manual_images;
    }

    public void setManualImages(List<String> manualImages) {
        this.manual_images = manualImages;
    }
}
