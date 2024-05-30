package com.example.petwithdietmanagement;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.data.Calendar;
import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.jsonFunction.GsonMapping;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private CalendarView calendarView;
    private BarChart barChart;
    private Spinner nutrientSpinner;
    private String selectedNutrient = "칼로리"; // Default selection
    private String selectedDate = null;
    private Calendar.User.Meals selectedMeals = null;
    private Map<String, Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 캘린더 뷰와 그래프 초기화
        calendarView = findViewById(R.id.calendar_view);
        barChart = findViewById(R.id.bar_chart);
        nutrientSpinner = findViewById(R.id.nutrient_spinner);

        // JSON 데이터 로드
        loadRecipeData();

        // Spinner 항목 선택 이벤트 설정
        nutrientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNutrient = (String) parent.getItemAtPosition(position);
                // 현재 선택된 날짜에 대해 그래프를 업데이트합니다.
                updateGraphData(selectedDate, selectedNutrient, selectedMeals);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작 (기본 선택)
            }
        });

        // 캘린더 날짜 변경 이벤트 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
                // Gson 객체 생성 및 JSON 파싱
                GsonMapping gsonMapping = new GsonMapping();
                AssetManager assetManager = CalendarActivity.this.getAssets();
                try (InputStream inputStream = assetManager.open("calendar.json");
                     InputStreamReader reader = new InputStreamReader(inputStream)) {
                    Calendar calendarData = gsonMapping.getCalendar(reader);
                    Map<String, Calendar.User> users = calendarData.getUsers();
                    selectedMeals = users.get("1").getFood_log().get(selectedDate);

                    updateGraphData(selectedDate, selectedNutrient, selectedMeals);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error reading the JSON file: " + e.getMessage());
                }
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
                overridePendingTransition(0,0);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // 마이 페이지 버튼
        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MyPageActivity.class); // 마이 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    private void loadRecipeData() {
        GsonMapping gsonMapping = new GsonMapping();
        AssetManager assetManager = CalendarActivity.this.getAssets();
        try (InputStream inputStream = assetManager.open("recipeList.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            recipes = gsonMapping.getRecipes(reader);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
    }

    private void updateGraphData(String date, String nutrient, Calendar.User.Meals meals) {
        if (date == null || nutrient == null) {
            // 날짜나 영양소가 선택되지 않은 경우 아무것도 하지 않음
            return;
        }

        List<BarEntry> entries = new ArrayList<>();

        if (meals != null) {
            List<Recipe.Nutrients> breakfastNutrients = getNutrientValue(meals.getBreakfast_foodid());
            List<Recipe.Nutrients> lunchNutrients = getNutrientValue(meals.getLunch_foodid());
            List<Recipe.Nutrients> dinnerNutrients = getNutrientValue(meals.getDinner_foodid());

            float breakfastSum = calculateNutrientSum(breakfastNutrients, nutrient);
            float lunchSum = calculateNutrientSum(lunchNutrients, nutrient);
            float dinnerSum = calculateNutrientSum(dinnerNutrients, nutrient);

            entries.add(new BarEntry(0f, breakfastSum));
            entries.add(new BarEntry(1f, lunchSum));
            entries.add(new BarEntry(2f, dinnerSum));
        }

        BarDataSet dataSet = new BarDataSet(entries, nutrient);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSet.setValueTextColor(Color.BLACK); // 값의 색상을 검정색으로 설정
        dataSet.setValueTextSize(12f); // 값의 글씨 크기 설정
        dataSet.setDrawValues(true); // 값을 그리도록 설정

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        List<String> labels = new ArrayList<>();
        labels.add("아침");
        labels.add("점심");
        labels.add("저녁");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getAxisLeft().setDrawGridLines(false);

        // 그래프의 크기 고정
        barChart.setScaleEnabled(false); // 줌을 비활성화하여 크기 고정
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false); // 범례 비활성화

        barChart.invalidate(); // refresh
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
                case "칼로리":
                    sum += nutrients.getCalories();
                    break;
                case "단백질":
                    sum += nutrients.getProtein();
                    break;
                case "지방":
                    sum += nutrients.getFat();
                    break;
                case "탄수화물":
                    sum += nutrients.getCarbohydrate();
                    break;
                case "나트륨":
                    sum += nutrients.getSodium();
                    break;
            }
        }
        return sum;
    }
}
