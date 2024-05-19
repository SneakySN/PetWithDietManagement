package com.example.petwithdietmanagement.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private Map<String,User> users=new HashMap<>();

    public Map<String,User> getUsers() {
        return users;
    }

    public static class User {
        private int user_id;
        private Map<String, Meals> food_log = new HashMap<>();

        public int getUser_id() {
            return user_id;
        }

        public Map<String, Meals> getFood_log() {
            return food_log;
        }

        public static class Meals {
            private List<String> breakfast_foodid;
            private List<String> lunch_foodid;
            private List<String> dinner_foodid;

            public List<String> getBreakfast_foodid() {
                return breakfast_foodid;
            }

            public List<String> getLunch_foodid() {
                return lunch_foodid;
            }

            public List<String> getDinner_foodid() {
                return dinner_foodid;
            }
        }
    }
}
