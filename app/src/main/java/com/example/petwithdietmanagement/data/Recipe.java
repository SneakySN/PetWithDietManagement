package com.example.petwithdietmanagement.data;

public class Recipe {
    // 요리 이름
    private String name;
    // 조리 시간(hour)
    private int cookingTime;
    // 육식 점수
    private int point;
    // 조리 방법(순서)
    private String cookingMethod;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getCookingTime(){
        return cookingTime;
    }
    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public String getCookingMethod() {
        return cookingMethod;
    }
    public void setCookingMethod(String cookingMethod) {
        this.cookingTime = cookingTime;
    }
}
