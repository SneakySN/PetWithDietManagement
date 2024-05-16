package com.example.petwithdietmanagement.data;

import java.util.List;
import java.util.Map;


public class Calendar {
    private int user_id;
    private Map<String, Meals> food_log;
    // Getters and setters for User class fields
    public int getUser_id() {
        return user_id;
    }




    public static class Meals {
        private List<String> breakfast_foodid;
        private List<String> lunch_foodid;
        private List<String> dinner_foodid;

        // Getters and setters for Meals class fields
        public List<String> getBreakfast_foodid() {
            return breakfast_foodid;
        }

        public void setBreakfast_foodid(List<String> breakfast_foodid) {
            this.breakfast_foodid = breakfast_foodid;
        }

        public List<String> getLunch_foodid() {
            return lunch_foodid;
        }

        public void setLunch_foodid(List<String> lunch_foodid) {
            this.lunch_foodid = lunch_foodid;
        }

        public List<String> getDinner_foodid() {
            return dinner_foodid;
        }

        public void setDinner_foodid(List<String> dinner_foodid) {
            this.dinner_foodid = dinner_foodid;
        }
    }

}

