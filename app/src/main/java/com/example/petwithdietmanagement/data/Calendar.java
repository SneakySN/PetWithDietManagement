package com.example.petwithdietmanagement.data;

public class Calendar {
    private String userId;
    private String date;
    private String mealtime;
    private int foodId;

    // 기본 생성자
    public Calendar() {
    }

    // 모든 필드를 인수로 받는 생성자
    public Calendar(String userId, String date, String mealtime, int foodId) {
        this.userId = userId;
        this.date = date;
        this.mealtime = mealtime;
        this.foodId = foodId;
    }

    // getter 및 setter 메서드
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMealtime() {
        return mealtime;
    }

    public void setMealtime(String mealtime) {
        this.mealtime = mealtime;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", mealtime='" + mealtime + '\'' +
                ", foodId=" + foodId +
                '}';
    }
}
