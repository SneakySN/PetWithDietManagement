package com.example.petwithdietmanagement.data;

import java.util.List;

public class User {
    private int user_id;
    private String name;
    private String username;
    private String password;
    private String profile_picture;
    private String goals;
    private String daily_meals;
    private Nutrients daily_nutrient_intake;
    private Health_info health_info;
    private int gold;
    private List<Item> items;

    // 영양소 정보를 위한 중첩 클래스
    public static class Nutrients {
        private String calories;
        private String protein;
        private String fat;
        private String carbohydrate;
        private String sodium;

        // Getters
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

    public static class Item {
        private String type;
        private int id;
        private boolean equipped;

        // Getters
        public String getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public boolean isEquipped() {
            return equipped;
        }
    }

    public static class Health_info {
        private int weight;
        private int height;
        private double bmi;
        private String blood_pressure;

        // Getters
        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }

        public double getBmi() {
            return bmi;
        }

        public String getBlood_pressure() {
            return blood_pressure;
        }
    }

    // Getters for User class fields
    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getGoals() {
        return goals;
    }

    public String getDaily_meals() {
        return daily_meals;
    }

    public Nutrients getDaily_nutrient_intake() {
        return daily_nutrient_intake;
    }

    public Health_info getHealth_info() {
        return health_info;
    }

    public int getGold() {
        return gold;
    }

    public List<Item> getItems() {
        return items;
    }
}
