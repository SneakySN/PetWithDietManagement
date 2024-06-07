package com.example.petwithdietmanagement.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private int id;
    private String recipeName;
    private String cookingMethod;
    private String ingredients;
    private Nutrients nutrients;
    private String cookingTime;
    private String servingWeight;
    private String dishType;
    private Images images;
    private List<String> manualSteps;
    private List<String> manualImages;

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
        private String previewImage;
        private String ingredientPreviewImage;

        public Images() {
        }

        protected Images(Parcel in) {
            previewImage = in.readString();
            ingredientPreviewImage = in.readString();
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
            dest.writeString(previewImage);
            dest.writeString(ingredientPreviewImage);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getPreviewImage() {
            return previewImage;
        }

        public void setPreviewImage(String previewImage) {
            this.previewImage = previewImage;
        }

        public String getIngredientPreviewImage() {
            return ingredientPreviewImage;
        }

        public void setIngredientPreviewImage(String ingredientPreviewImage) {
            this.ingredientPreviewImage = ingredientPreviewImage;
        }
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        recipeName = in.readString();
        cookingMethod = in.readString();
        ingredients = in.readString();
        nutrients = in.readParcelable(Nutrients.class.getClassLoader());
        cookingTime = in.readString();
        servingWeight = in.readString();
        dishType = in.readString();
        images = in.readParcelable(Images.class.getClassLoader());
        manualSteps = in.createStringArrayList();
        manualImages = in.createStringArrayList();
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
        dest.writeString(recipeName);
        dest.writeString(cookingMethod);
        dest.writeString(ingredients);
        dest.writeParcelable(nutrients, flags);
        dest.writeString(cookingTime);
        dest.writeString(servingWeight);
        dest.writeString(dishType);
        dest.writeParcelable(images, flags);
        dest.writeStringList(manualSteps);
        dest.writeStringList(manualImages);
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
        return recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public String getCookingMethod() {
        return cookingMethod;
    }

    public void setCookingMethod(String cookingMethod) {
        this.cookingMethod = cookingMethod;
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
        return cookingTime;
    }

    public String getServingWeight() {
        return servingWeight;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getManualSteps() {
        return manualSteps;
    }

    public void setManualSteps(List<String> manualSteps) {
        this.manualSteps = manualSteps;
    }

    public List<String> getManualImages() {
        return manualImages;
    }

    public void setManualImages(List<String> manualImages) {
        this.manualImages = manualImages;
    }
}