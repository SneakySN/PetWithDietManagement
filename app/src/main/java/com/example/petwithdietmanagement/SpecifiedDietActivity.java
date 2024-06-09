package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.GlideApp;
import com.example.petwithdietmanagement.database.RecipeDBManager;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;
import com.example.petwithdietmanagement.jsonFunction.GsonPut;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SpecifiedDietActivity extends AppCompatActivity {

    private TextView dietTitle;
    private ImageView dietImage;
    private TextView dietIngredients;
    private TextView dietNutrients;
    private TextView dietSteps;
    private Spinner mealTimeSpinner;

    private String selectedMealTime;
    private RecipeDBManager dbManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifieddiet);

        dietTitle = findViewById(R.id.dietTitle);
        dietImage = findViewById(R.id.dietImage);
        dietIngredients = findViewById(R.id.dietIngredients);
        dietNutrients = findViewById(R.id.dietNutrients);
        dietSteps = findViewById(R.id.dietSteps);
        mealTimeSpinner = findViewById(R.id.mealTimeSpinner);

        dbManager = new RecipeDBManager(this);  // dbManager 초기화

        // Spinner 어댑터 설정
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTimeSpinner.setAdapter(adapter);

        mealTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMealTime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무 것도 선택되지 않았을 때 처리할 로직
            }
        });

        // 인텐트에서 Recipe 객체 받기
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("Recipe");

        if (recipe != null) {
            dietTitle.setText(recipe.getRecipeName());
            String imageUrl = recipe.getImages().getPreviewImage();

            // URL을 로그로 출력하여 확인
            Log.d("SpecifiedDietActivity", "Image URL: " + imageUrl);

            // Glide로 이미지 로드
            GlideApp.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(dietImage);

            dietIngredients.setText("Ingredients:\n" + recipe.getIngredients());
            dietNutrients.setText("Nutrients:\n" + getNutrientsString(recipe.getNutrients()));

            List<String> manuals = recipe.getManualSteps();
            StringBuilder step = new StringBuilder();
            for (String manual : manuals) {
                String replacedString = manual.replace('"', Character.MIN_VALUE);
                replacedString = replacedString.replaceAll("\n", "");
                for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                    replacedString = replacedString.replace(alphabet, Character.MIN_VALUE);
                }
                step.append(replacedString).append("\n");
            }
            dietSteps.setText("steps:\n" + step.toString());
            Log.d("SpecifiedDietActivity", "Loaded recipe: " + recipe.getRecipeName() + ", Steps: " + manuals);
        } else {
            Log.e("SpecifiedDietActivity", "Recipe is null");
        }

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = "2024-06-09";

                // Gson 객체 생성 및 JSON 파싱
                GsonMapping gsonMapping = new GsonMapping();
                File file = new File(getFilesDir(), "calendar.json");
                Calendar calendarData;

                if (file.exists()) {
                    try (FileReader reader = new FileReader(file)) {
                        calendarData = gsonMapping.getCalendar(reader);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("SpecifiedDietActivity", "Error reading the JSON file: " + e.getMessage());
                        return;
                    }
                } else {
                    calendarData = new Calendar();
                    calendarData.setUsers(new HashMap<>());
                }

                Calendar.User user = calendarData.getUsers().get("1");

                if (user == null) {
                    user = new Calendar.User();
                    user.setFood_log(new HashMap<>());
                    calendarData.getUsers().put("1", user);
                }

                Calendar.User.Meals meals = user.getFood_log().get(date);
                if (meals == null) {
                    meals = new Calendar.User.Meals();
                }

                String selectedRecipe = Integer.toString(dbManager.getRecipeIdByName(recipe.getRecipeName()));
                switch (selectedMealTime) {
                    case "아침":
                        meals.addBreakfast_foodid(selectedRecipe);
                        break;
                    case "점심":
                        meals.addLunch_foodid(selectedRecipe);
                        break;
                    case "저녁":
                        meals.addDinner_foodid(selectedRecipe);
                        break;
                }

                user.getFood_log().put(date, meals);

                // JSON 파일 저장
                GsonPut.saveCalendarToJson(calendarData, file.getPath());

                // 저장되었습니다 토스트 메시지 표시
                Toast.makeText(SpecifiedDietActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();

                // 1초 후에 액티비티 종료
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }, 1000);
            }
        });
    }

    private String getNutrientsString(Recipe.Nutrients nutrients) {
        return "Calories: " + nutrients.getCalories() + " kcal\n" +
                "Protein: " + nutrients.getProtein() + " g\n" +
                "Fat: " + nutrients.getFat() + " g\n" +
                "Carbohydrate: " + nutrients.getCarbohydrate() + " g\n" +
                "Sodium: " + nutrients.getSodium() + " mg";
    }
}
