package com.example.petwithdietmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView petImageView;
    private TextView carbsInfoTextView;
    private TextView proteinInfoTextView;
    private TextView fatInfoTextView;
    private Handler handler;
    private Runnable imageSwitcher;
    private int currentIndex = 0;
    private boolean isJumping = false;
    private Map<String, Recipe> recipes;
    int[] petImages = {
            R.drawable.slime01,
            R.drawable.slime02,
            R.drawable.slime03,
            R.drawable.slime02
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        petImageView = findViewById(R.id.ic_pet);
        carbsInfoTextView = findViewById(R.id.carbs_info);
        proteinInfoTextView = findViewById(R.id.protein_info);
        fatInfoTextView = findViewById(R.id.fat_info);

        handler = new Handler(Looper.getMainLooper());
        imageSwitcher = new Runnable() {
            @Override
            public void run(){
                if (!isJumping) {
                    petImageView.setImageResource(petImages[currentIndex]);
                    currentIndex = (currentIndex + 1) % petImages.length;
                    handler.postDelayed(this, 750);
                }
            }
        };
        handler.post(imageSwitcher);

        petImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!isJumping)
                    changeJumpImage();
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                finish();
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
            }
        });

        // 마이 페이지 버튼
        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class); // 마이 페이지로 이동
                startActivity(intent);
            }
        });

        // JSON 데이터 로드
        loadRecipeData();

        // 오늘 날짜에 맞는 데이터 로드
        loadCalendarData();
    }

    private void changeJumpImage(){
        isJumping = true;
        handler.removeCallbacks(imageSwitcher);

        // 점프 애니메이션 설정
        petImageView.setImageResource(R.drawable.pet_jump_animation);
        AnimationDrawable petJumpAnimation = (AnimationDrawable) petImageView.getDrawable();
        petJumpAnimation.start();

        // 점프 애니메이션 끝난 후 원래 애니메이션 시퀀스로 돌아가기
        int animationDuration = getAnimationDuration(petJumpAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                petJumpAnimation.stop();
                isJumping = false;
                startOriginalAnimation();
            }
        }, animationDuration);
    }

    private void startOriginalAnimation() {
        currentIndex = 0;
        handler.post(imageSwitcher); // 원래 애니메이션 시퀀스 재시작
    }

    private int getAnimationDuration(AnimationDrawable animationDrawable) {
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        return duration;
    }

    private void loadRecipeData() {
        GsonMapping gsonMapping = new GsonMapping();
        AssetManager assetManager = MainActivity.this.getAssets();
        try (InputStream inputStream = assetManager.open("recipeList.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            recipes = gsonMapping.getRecipes(reader);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
    }

    private void loadCalendarData() {
        GsonMapping gsonMapping = new GsonMapping();
        AssetManager assetManager = MainActivity.this.getAssets();
        try (InputStream inputStream = assetManager.open("calendar.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            Calendar calendarData = gsonMapping.getCalendar(reader);
            Map<String, Calendar.User> users = calendarData.getUsers();
            Calendar.User.Meals meals = users.get("1").getFood_log().get(getTodayDate());

            if (meals != null) {
                List<Recipe.Nutrients> breakfastNutrients = getNutrientValue(meals.getBreakfast_foodid());
                List<Recipe.Nutrients> lunchNutrients = getNutrientValue(meals.getLunch_foodid());
                List<Recipe.Nutrients> dinnerNutrients = getNutrientValue(meals.getDinner_foodid());

                float totalCarbs = calculateNutrientSum(breakfastNutrients, "탄수화물")
                        + calculateNutrientSum(lunchNutrients, "탄수화물")
                        + calculateNutrientSum(dinnerNutrients, "탄수화물");
                float totalProtein = calculateNutrientSum(breakfastNutrients, "단백질")
                        + calculateNutrientSum(lunchNutrients, "단백질")
                        + calculateNutrientSum(dinnerNutrients, "단백질");
                float totalFat = calculateNutrientSum(breakfastNutrients, "지방")
                        + calculateNutrientSum(lunchNutrients, "지방")
                        + calculateNutrientSum(dinnerNutrients, "지방");

                carbsInfoTextView.setText(totalCarbs + "g");
                proteinInfoTextView.setText(totalProtein + "g");
                fatInfoTextView.setText(totalFat + "g");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
    }

    private String getTodayDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1; // 0-indexed
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);

    }

    private List<Recipe.Nutrients> getNutrientValue(List<String> foodIds) {
        List<Recipe.Nutrients> nutrientsList = new ArrayList<>();
        for (String foodId : foodIds) {
            Recipe recipe = recipes.get(foodId);
            if (recipe != null) {
                nutrientsList.add(recipe.getNutrients());
            }
        }
        return nutrientsList;
    }

    private float calculateNutrientSum(List<Recipe.Nutrients> nutrientsList, String nutrient) {
        float sum = 0;
        for (Recipe.Nutrients nutrients : nutrientsList) {
            switch (nutrient) {
                case "탄수화물":
                    sum += Float.parseFloat(nutrients.getCarbohydrate());
                    break;
                case "단백질":
                    sum += Float.parseFloat(nutrients.getProtein());
                    break;
                case "지방":
                    sum += Float.parseFloat(nutrients.getFat());
                    break;
            }
        }
        return sum;
    }
}
