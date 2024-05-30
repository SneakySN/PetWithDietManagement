package com.example.petwithdietmanagement.data;

public class Mission {
    private int id;
    private String name;
    private String description;
    private String type;
    private Integer target;
    private String unit;
    private Integer reward;

    // Getters and setters for each field
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getTarget() { return target; }
    public void setTarget(Integer target) { this.target = target; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getReward() { return reward; }
    public void setReward(Integer reward) { this.reward = reward; }
}
