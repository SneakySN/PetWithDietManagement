package com.example.petwithdietmanagement.data;

import java.util.List;

public class User {
    private String name;
    private String username;
    private String password;
    private String profile_picture;
    private String goals;
    private String daily_info;
    private HealthInfo health_info;
    private int gold;
    private List<Item> items;

    // HealthInfo class
    public static class HealthInfo {
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

    // Item class
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

    // Getters for User class fields
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

    public String getDaily_info() {
        return daily_info;
    }

    public HealthInfo getHealth_info() {
        return health_info;
    }

    public int getGold() {
        return gold;
    }

    public List<Item> getItems() {
        return items;
    }
}
