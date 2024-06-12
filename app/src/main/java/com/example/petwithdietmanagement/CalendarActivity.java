package com.example.petwithdietmanagement;

import android.content.Intent;
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
import com.example.petwithdietmanagement.database.CalendarDBManager;
import com.example.petwithdietmanagement.database.RecipeDBManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private CalendarView calendarView;
    private BarChart barChart;
    private Spinner nutrientSpinner;
    private String selectedNutrient = "칼로리"; // Default selection
    private String selectedDate = null;
    private RecipeDBManager recipeDbManager;
    private CalendarDBManager calendarDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendar_view);
        barChart = findViewById(R.id.bar_chart);
        nutrientSpinner = findViewById(R.id.nutrient_spinner);

        recipeDbManager = new RecipeDBManager(this);
        calendarDbManager = new CalendarDBManager(this);

        nutrientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNutrient = (String) parent.getItemAtPosition(position);
                try {
                    updateGraphData(selectedDate, selectedNutrient);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작 (기본 선택)
                selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                calendarView.setDate(new Date().getTime(), false, true);
                try {
                    updateGraphData(selectedDate, selectedNutrient);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
                try {
                    updateGraphData(selectedDate, selectedNutrient);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setupNavigationButtons();

        // 초기 로드 시 오늘 날짜의 데이터를 표시
        selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        calendarView.setDate(new Date().getTime(), false, true);
        try {
            updateGraphData(selectedDate, selectedNutrient);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupNavigationButtons() {
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(v -> navigateTo(MainActivity.class));

        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(v -> navigateTo(DietActivity.class));

        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(v -> {
            navigateTo(CalendarActivity.class);
            finish();
        });

        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(v -> navigateTo(PetMenuActivity.class));

        ImageButton myPageButton = findViewById(R.id.ic_myPage);
        myPageButton.setOnClickListener(v -> navigateTo(MyPageActivity.class));
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(CalendarActivity.this, cls);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void updateGraphData(String date, String nutrient) throws JSONException {
        if (date == null || nutrient == null) {
            return;
        }

        List<Calendar> calendarData = calendarDbManager.getCalendarData("1", date); // 사용자 ID를 "1"로 가정
        if (calendarData.isEmpty()) {
            barChart.clear();
            barChart.setNoDataText("No chart data available");
            barChart.invalidate();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();

        List<String> breakfastFoodIds = new ArrayList<>();
        List<String> lunchFoodIds = new ArrayList<>();
        List<String> dinnerFoodIds = new ArrayList<>();

        for (Calendar calendar : calendarData) {
            switch (calendar.getMealtime()) {
                case "아침":
                    breakfastFoodIds.add(String.valueOf(calendar.getFoodId()));
                    break;
                case "점심":
                    lunchFoodIds.add(String.valueOf(calendar.getFoodId()));
                    break;
                case "저녁":
                    dinnerFoodIds.add(String.valueOf(calendar.getFoodId()));
                    break;
            }
        }

        List<Recipe.Nutrients> breakfastNutrients = getNutrientValue(breakfastFoodIds);
        List<Recipe.Nutrients> lunchNutrients = getNutrientValue(lunchFoodIds);
        List<Recipe.Nutrients> dinnerNutrients = getNutrientValue(dinnerFoodIds);

        float breakfastSum = calculateNutrientSum(breakfastNutrients, nutrient);
        float lunchSum = calculateNutrientSum(lunchNutrients, nutrient);
        float dinnerSum = calculateNutrientSum(dinnerNutrients, nutrient);

        entries.add(new BarEntry(0f, breakfastSum));
        entries.add(new BarEntry(1f, lunchSum));
        entries.add(new BarEntry(2f, dinnerSum));

        BarDataSet dataSet = new BarDataSet(entries, nutrient);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(true);

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
        barChart.setScaleEnabled(false);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.invalidate();
    }

    private List<Recipe.Nutrients> getNutrientValue(List<String> foodIds) throws JSONException {
        List<Recipe.Nutrients> nutrientsList = new ArrayList<>();
        for (String foodId : foodIds) {
            int id = Integer.parseInt(foodId); // foodId가 문자열이므로 정수로 변환
            Recipe recipe = recipeDbManager.getRecipeById(id);
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
