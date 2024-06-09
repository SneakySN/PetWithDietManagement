package com.example.petwithdietmanagement.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private Map<String, User> users = new HashMap<>();

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public static class User {
        private int user_id;
        private Map<String, Meals> food_log = new HashMap<>();

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public Map<String, Meals> getFood_log() {
            return food_log;
        }

        public void setFood_log(Map<String, Meals> food_log) {
            this.food_log = food_log;
        }

        public static class Meals {
            private List<String> breakfast_foodid;
            private List<String> lunch_foodid;
            private List<String> dinner_foodid;

            public List<String> getBreakfast_foodid() {
                return breakfast_foodid;
            }

            public void setBreakfast_foodid(List<String> breakfast_foodid) {
                this.breakfast_foodid = breakfast_foodid;
            }

            public void addBreakfast_foodid(String breakfast_foodid) {
                if (this.breakfast_foodid == null) {
                    this.breakfast_foodid = new ArrayList<>();
                }
                this.breakfast_foodid.add(breakfast_foodid);
            }

            public List<String> getLunch_foodid() {
                return lunch_foodid;
            }

            public void setLunch_foodid(List<String> lunch_foodid) {
                this.lunch_foodid = lunch_foodid;
            }

            public void addLunch_foodid(String lunch_foodid) {
                if (this.lunch_foodid == null) {
                    this.lunch_foodid = new ArrayList<>();
                }
                this.lunch_foodid.add(lunch_foodid);
            }

            public List<String> getDinner_foodid() {
                return dinner_foodid;
            }

            public void setDinner_foodid(List<String> dinner_foodid) {
                this.dinner_foodid = dinner_foodid;
            }

            public void addDinner_foodid(String dinner_foodid) {
                if (this.dinner_foodid == null) {
                    this.dinner_foodid = new ArrayList<>();
                }
                this.dinner_foodid.add(dinner_foodid);
            }
        }
    }
}
