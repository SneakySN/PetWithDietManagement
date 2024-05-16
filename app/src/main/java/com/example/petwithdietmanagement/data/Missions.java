package com.example.petwithdietmanagement.data;

import java.util.List;

public class Missions {
    private List<Mission> missions;

    // Getters and setters
    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public static class Mission {
        private int mission_id;
        private String name;
        private String description;
        private Goal goal;
        private int reward;

        // Getters and setters for Mission class fields
        public int getMission_id() {
            return mission_id;
        }

        public void setMission_id(int mission_id) {
            this.mission_id = mission_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Goal getGoal() {
            return goal;
        }

        public void setGoal(Goal goal) {
            this.goal = goal;
        }

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public static class Goal {
            private String type;
            private int target;
            private String unit;
            private String food_type; // Optional field
            private String nutrient; // Optional field

            // Getters and setters for Goal class fields
            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getTarget() {
                return target;
            }

            public void setTarget(int target) {
                this.target = target;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getFood_type() {
                return food_type;
            }

            public void setFood_type(String food_type) {
                this.food_type = food_type;
            }

            public String getNutrient() {
                return nutrient;
            }

            public void setNutrient(String nutrient) {
                this.nutrient = nutrient;
            }
        }
    }
}
