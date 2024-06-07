package com.example.petwithdietmanagement;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Item;
import com.example.petwithdietmanagement.data.Mission;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.database.ItemDBManager;
import com.example.petwithdietmanagement.database.MissionDBManager;
import com.example.petwithdietmanagement.database.RecipeDBHelper;
import com.example.petwithdietmanagement.database.RecipeDBManager;
import com.example.petwithdietmanagement.database.UserDBManager;
import com.example.petwithdietmanagement.data.User;
import com.example.petwithdietmanagement.data.User.HealthInfo;
import com.example.petwithdietmanagement.data.User.Items;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;

import org.json.JSONArray;
import org.json.JSONException;

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
    private ViewPager2 viewPager;
    private int[] layouts = {R.layout.breakfast_section, R.layout.lunch_section, R.layout.dinner_section};
    private MealPagerAdapter adapter;
    int[] petImages = {
            R.drawable.slime01,
            R.drawable.slime02,
            R.drawable.slime03,
            R.drawable.slime02
    };
    private RecipeDBManager dbManager;
    private MissionDBManager missionDBM;
    private ItemDBManager itemDBM;
    private UserDBManager userDBManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        petImageView = findViewById(R.id.ic_pet);
        carbsInfoTextView = findViewById(R.id.carbs_info);
        proteinInfoTextView = findViewById(R.id.protein_info);
        fatInfoTextView = findViewById(R.id.fat_info);
        viewPager = findViewById(R.id.view_pager);

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
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 마이 페이지 버튼
        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class); // 마이 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // JSON 데이터 로드
        loadRecipeData();

        // 뷰페이저 설정
        adapter = new MealPagerAdapter(layouts);
        viewPager.setAdapter(adapter);

        // 오늘 날짜에 맞는 데이터 로드
        loadCalendarData();

        // 뷰페이저 초기 데이터 로드 및 페이지 변경 시 데이터 업데이트
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateMealInfo(position);
            }
        });

// DB 관련 파트

        dbManager = new RecipeDBManager(this); // recipeDB를 활용하기 위한 변수 선언

        // recipe 데이터베이스가 비어 있는지 확인
        if (dbManager.isDatabaseEmpty()) {
            // JSON 파일 읽기
            String jsonString = JsonUtils.loadJSONFromAsset(this, "recipeList.json");
            if (jsonString != null) {
                try {
                    dbManager.insertRecipeData(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MainActivity", "Failed to load JSON file.");
            }
        }
    // 예제: "새우 두부 계란찜" 레시피의 칼로리 값을 "220"로 수정
        //dbManager.updateRecipeField("새우 두부 계란찜", RecipeDBHelper.COLUMN_CALORIES, "220");

    // 예제: 레시피 명을 입력해 레시피의 ID 값 가져오기
        //dbManager.getRecipeIdByName("브로콜리 컬리플라워 샐러드와 두유 요거트 소스");

    // 예제: ID를 입력해 레시피의 데이터를 가져오기
        /*try {
            Recipe recipe = dbManager.getRecipeById(dbManager.getRecipeIdByName("L..A갈비구이"));
            Recipe.Nutrients nutrients = recipe.getNutrients();
            Log.d("MainActivity", "메뉴 이름: " + recipe.getRecipeName());
            Log.d("MainActivity", "칼로리: " + nutrients.getCalories());
            Log.d("MainActivity", "메뉴얼 이미지들: " + recipe.getManualImages());
            Log.d("MainActivity", "메뉴얼 스텝들: " + recipe.getManualSteps().get(2));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/

    // 예제: 특정 개수의 필터링되고 정렬된 레시피들 가져오기 (true: 오름차순, false: 내림차순), (cookingMethod: 끓이기, 찌기, 굽기, 볶기, 튀기기, 기타), (dishType: 한식, 양식, 기타)
        /*List<Recipe> recipes = dbManager.getRecipes("", true, 5, "볶기", "양식");

        for (Recipe recipe : recipes) {
            // 각 recipe 객체에 접근하여 원하는 데이터를 사용
            Log.d("Recipe", "Name: " + recipe.getRecipeName());
            Log.d("Recipe", "Calories: " + recipe.getNutrients().getCalories());
        }*/

        missionDBM = new MissionDBManager(this); // missionDB를 활용하기 위한 변수 선언

        // mission 데이터베이스가 비어 있는지 확인 후 mission.json 파일의 내용 저장
        if (missionDBM.isDatabaseEmpty()) {
            // JSON 파일 읽기
            String jsonString = JsonUtils.loadJSONFromAsset(this, "mission.json");
            if (jsonString != null) {
                try {
                    missionDBM.insertMissionDataFromJson(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MainActivity", "Failed to load JSON file.");
            }
        }

    //missionDB의 특정 값 가져오기
        /*Mission mission = missionDBM.getMissionById(1);
        Log.d("MainActivity", "미션 내용: " + mission.getDescription());*/

    //missionDB의 특정 값 수정하기
        /*Mission mission = missionDBM.getMissionById(1);
        mission.setReward(15300);
        missionDBM.updateMission(mission);*/

    //missionDB의 특정 데이터 삭제하기
        //missionDBM.deleteMission(1);

        itemDBM = new ItemDBManager(this); // itemDB를 활용하기 위한 변수 선언

    // item 데이터베이스가 비어 있는지 확인 후 item.json 파일의 내용 저장
        if (itemDBM.isDatabaseEmpty()) {
            // JSON 파일 읽기
            String jsonString = JsonUtils.loadJSONFromAsset(this,"item.json");
            if (jsonString != null) {
                try {
                    itemDBM.insertItemDataFromJson(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MainActivity", "Failed to load JSON file.");
            }
        }

    //itemDB의 특정 값 가져오기
        /*Item item = itemDBM.getItemById(1);
        Log.d("MainActivity", "아이템 내용: " + item.getDescription());*/

    // 아이템 명을 입력해 아이템의 ID 값 가져오기
        //Log.d("MainActivity", "아이템 ID: " + itemDBM.getItemIdByName("orange_carpet"));

    //특정 아이템 타입의 아이템들만 가져오기
        /*List<Item> filteredItems = itemDBM.getItemsByType("Hat");
        for (Item item : filteredItems) {
            Log.d("MainActivity", "Filtered Item: " + item.getItemName() + ", Type: " + item.getItemType());
        }*/
    //itemDB의 특정 값 수정하기
        /*Item item = itemDBM.getItemById(1);
        item.setPurchased(0);
        itemDBM.updateItem(item);*/

    //itemDB의 특정 데이터 삭제하기
        //itemDBM.deleteItem(1);

        userDBManager = new UserDBManager(this);

    // user 데이터베이스가 비어 있는지 확인 후 user_info.json 파일의 내용 저장
        if (userDBManager.isDatabaseEmpty()) {
            // JSON 파일 읽기
            String jsonString = JsonUtils.loadJSONFromAsset(this, "user_info.json");
            if (jsonString != null) {
                try {
                    userDBManager.insertUserData(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MainActivity", "Failed to load JSON file.");
            }
        }

    //userDB에서 데이터 불러오기
        /*User user = userDBManager.getUserById("user123");

        if (user != null) {
            List<Items> items = user.getItems();
            for (User.Items item : items) {
                Log.d("UserItem", "Item ID: " + item.getId() + ", Equipped: " + (item.isEquipped() == 1));
            }
        } else {
            Log.d("UserItem", "User not found.");
        }*/

    //userDB에서 user가 가진 아이템들 삭제(잠시 함수를 public으로 할 것)
        //userDBManager.deleteUserItems("user123");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
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
        for (int i = 0; i < 12; i++) {
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

                // 각 페이지가 준비되면 updateMealInfo 호출
                viewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        updateMealInfo(viewPager.getCurrentItem());
                    }
                });
            } else {
                // meals가 null인 경우 초기화
                updateMealInfo(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
            // 오류가 발생한 경우 초기화
            updateMealInfo(null);
        }
    }

    private void updateMealInfo(Integer position) {
        Calendar.User.Meals meals = getTodayMeals();

        for (int i = 0; i < layouts.length; i++) {
            View view = viewPager.findViewWithTag("page_" + i);
            if (view != null) {
                TextView mealInfoTextView;
                switch (i) {
                    case 0:
                        mealInfoTextView = view.findViewById(R.id.breakfast_info);
                        mealInfoTextView.setText(meals == null ? "단식했어요" : getMealNames(meals.getBreakfast_foodid()));
                        break;
                    case 1:
                        mealInfoTextView = view.findViewById(R.id.lunch_info);
                        mealInfoTextView.setText(meals == null ? "단식했어요" : getMealNames(meals.getLunch_foodid()));
                        break;
                    case 2:
                        mealInfoTextView = view.findViewById(R.id.dinner_info);
                        mealInfoTextView.setText(meals == null ? "단식했어요" : getMealNames(meals.getDinner_foodid()));
                        break;
                }
            }
        }
    }

    private String getMealNames(List<String> foodIds) {
        if (foodIds == null || foodIds.isEmpty()) {
            return "단식했어요";
        }

        StringBuilder mealNames = new StringBuilder();
        for (String foodId : foodIds) {
            Recipe recipe = recipes.get(foodId);
            if (recipe != null) {
                mealNames.append(recipe.getRecipeName()).append(", ");
            }
        }
        if (mealNames.length() > 0) {
            mealNames.setLength(mealNames.length() - 2); // Remove trailing comma and space
        }
        return mealNames.toString();
    }

    private Calendar.User.Meals getTodayMeals() {
        GsonMapping gsonMapping = new GsonMapping();
        AssetManager assetManager = MainActivity.this.getAssets();
        try (InputStream inputStream = assetManager.open("calendar.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            Calendar calendarData = gsonMapping.getCalendar(reader);
            Map<String, Calendar.User> users = calendarData.getUsers();
            return users.get("1").getFood_log().get(getTodayDate());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
            return null;
        }
    }

    private String getTodayDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1; // 0-indexed
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        //return String.format("%04d-%02d-%02d", year, month, day);
        return "2024-05-15";
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
                    sum += nutrients.getCarbohydrate();
                    break;
                case "단백질":
                    sum += nutrients.getProtein();
                    break;
                case "지방":
                    sum += nutrients.getFat();
                    break;
            }
        }
        return sum;
    }
}

