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

import com.bumptech.glide.Glide;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.database.CalendarDBManager;
import com.example.petwithdietmanagement.database.RecipeDBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SpecifiedDietActivity extends AppCompatActivity {

    private TextView dietTitle;
    private ImageView dietImage;
    private TextView dietIngredients;
    private TextView dietNutrients;
    private TextView dietSteps;
    private Spinner mealTimeSpinner;

    private String selectedMealTime;
    private RecipeDBManager recipeDbManager;
    private CalendarDBManager calendarDbManager;

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

        recipeDbManager = new RecipeDBManager(this);
        calendarDbManager = new CalendarDBManager(this);

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
            Glide.with(this)
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
                try {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String userId = "1"; // 사용자 ID가 필요하다면 적절히 설정합니다.
                    int recipeId = recipeDbManager.getRecipeIdByName(recipe.getRecipeName());

                    if (recipeId != -1) {
                        calendarDbManager.insertCalendarData(userId, date, selectedMealTime, recipeId);
                        Toast.makeText(SpecifiedDietActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();

                        // 1초 후에 액티비티 종료
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                overridePendingTransition(0, 0);
                            }
                        }, 1000);
                    } else {
                        Log.e("SpecifiedDietActivity", "Recipe ID not found");
                    }
                } catch (Exception e) {
                    Log.e("SpecifiedDietActivity", "Error adding recipe to calendar", e);
                    Toast.makeText(SpecifiedDietActivity.this, "오류가 발생했습니다", Toast.LENGTH_SHORT).show();
                }
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
