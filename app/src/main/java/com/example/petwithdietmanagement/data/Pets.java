package com.example.petwithdietmanagement.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pets {
    Map<String,Pet> pets=new HashMap<>();

    public Map<String,Pet> getPets() {
        return pets;
    }

    public class Pet {
        private int user_id;
        private String name;
        private String color;
        private Health health;
        private Happiness happiness;
        private Hunger hunger;
        private List<String> quotes;
        private Hat hat;

        private class Health {
            private int current;
            private int max;

            // Getters and setters
            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }
        }

        private class Happiness {
            private int current;
            private int max;

            // Getters and setters
            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }
        }

        private class Hunger {
            private int current;
            private int max;

            // Getters and setters
            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }
        }

        private class Hat {
            private int hat_id;
            private boolean equipped;

            // Getters and setters
            public int getHat_id() {
                return hat_id;
            }

            public void setHat_id(int hat_id) {
                this.hat_id = hat_id;
            }

            public boolean isEquipped() {
                return equipped;
            }

            public void setEquipped(boolean equipped) {
                this.equipped = equipped;
            }
        }

        // Getters and setters for Pet class fields
        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Health getHealth() {
            return health;
        }

        public void setHealth(Health health) {
            this.health = health;
        }

        public Happiness getHappiness() {
            return happiness;
        }

        public void setHappiness(Happiness happiness) {
            this.happiness = happiness;
        }

        public Hunger getHunger() {
            return hunger;
        }

        public void setHunger(Hunger hunger) {
            this.hunger = hunger;
        }

        public List<String> getQuotes() {
            return quotes;
        }

        public void setQuotes(List<String> quotes) {
            this.quotes = quotes;
        }

        public Hat getHat() {
            return hat;
        }

        public void setHat(Hat hat) {
            this.hat = hat;
        }
    }
}

