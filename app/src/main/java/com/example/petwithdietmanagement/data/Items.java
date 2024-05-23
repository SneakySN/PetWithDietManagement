package com.example.petwithdietmanagement.data;

import java.util.HashMap;
import java.util.Map;

public class Items {
    private Store store;

    public Store getStore() {
        return store;
    }

    public static class Store {
        private Map<String, Background> backgrounds = new HashMap<>();
        private Map<String, Floor> floors = new HashMap<>();
        private Map<String, Carpet> carpets = new HashMap<>();
        private Map<String, Hat> hats = new HashMap<>();

        public Map<String, Background> getBackgrounds() {
            return backgrounds;
        }

        public Map<String, Floor> getFloors() {
            return floors;
        }

        public Map<String, Carpet> getCarpets() {
            return carpets;
        }

        public Map<String, Hat> getHats() {
            return hats;
        }
    }

    public static class Background {
        private int background_id;
        private String name;
        private int price;
        private String imageUrl;
        private String description;

        public int getBackground_id() {
            return background_id;
        }

        public void setBackground_id(int background_id) {
            this.background_id = background_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Floor {
        private int floor_id;
        private String name;
        private int price;
        private String imageUrl;
        private String description;

        public int getFloor_id() {
            return floor_id;
        }

        public void setFloor_id(int floor_id) {
            this.floor_id = floor_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Carpet {
        private int carpet_id;
        private String name;
        private int price;
        private String imageUrl;
        private String description;

        public int getCarpet_id() {
            return carpet_id;
        }

        public void setCarpet_id(int carpet_id) {
            this.carpet_id = carpet_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Hat {
        private int hat_id;
        private String name;
        private int price;
        private String imageUrl;
        private String description;

        public int getHat_id() {
            return hat_id;
        }

        public void setHat_id(int hat_id) {
            this.hat_id = hat_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
