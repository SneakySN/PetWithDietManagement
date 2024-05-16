package com.example.petwithdietmanagement.data;

import java.util.List;

public class Store {
    private List<Background> backgrounds;
    private List<Floor> floors;
    private List<Carpet> carpets;
    private List<Hat> hats;

    // Getters and setters
    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(List<Background> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Carpet> getCarpets() {
        return carpets;
    }

    public void setCarpets(List<Carpet> carpets) {
        this.carpets = carpets;
    }

    public List<Hat> getHats() {
        return hats;
    }

    public void setHats(List<Hat> hats) {
        this.hats = hats;
    }

    public static class Background {
        private int background_id;
        private String name;
        private int price;
        private String imageUrl;
        private String description;

        // Getters and setters
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

        // Getters and setters
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

        // Getters and setters
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

        // Getters and setters
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
