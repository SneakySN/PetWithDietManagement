package com.example.petwithdietmanagement.data;

import java.util.List;

public class User {
    private String name;
    private String userId;
    private String password;
    private String profile_picture;
    private String goals;
    private HealthInfo health_info;
    private int gold;
    private List<Items> items;

    // HealthInfo class
    public static class HealthInfo {
        private int weight;
        private int height;
        private int blood_pressure;

        // Getters
        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }

        public int getBlood_pressure() {
            return blood_pressure;
        }

        // Setters
        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setBlood_pressure(int blood_pressure) {
            this.blood_pressure = blood_pressure;
        }
    }

    // Item class
    public static class Items {
        private int id;
        private int equipped;

        public int getId() {
            return id;
        }

        public int isEquipped() {
            return equipped;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setEquipped(int equipped) {
            this.equipped = equipped;
        }
    }

    // Getters for User class fields
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
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

    public HealthInfo getHealth_info() {
        return health_info;
    }

    public int getGold() {
        return gold;
    }

    public List<Items> getItems() {
        return items;
    }

    // Setters for User class fields
    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public void setHealth_info(HealthInfo health_info) {
        this.health_info = health_info;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
