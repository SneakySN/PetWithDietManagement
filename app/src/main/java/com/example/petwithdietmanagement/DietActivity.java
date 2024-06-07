package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.database.RecipeDBManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DietActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecipeDBManager dbManager;
    private List<Recipe> recipeList;
    private RecipeAdapter adapter;
    private RecyclerView recipeRecyclerView;
    private Spinner cookMethodSpinner, categorySpinner, nutrientSpinner;
    private String selectedCookMethod = "조리";
    private String selectedCategory = "전체";
    private String selectedNutrient = "영양소";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        searchView = findViewById(R.id.search_view);
        recipeRecyclerView = findViewById(R.id.recipe_recycler_view);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cookMethodSpinner = findViewById(R.id.cook_method_spinner);
        categorySpinner = findViewById(R.id.category_spinner);
        nutrientSpinner = findViewById(R.id.nutrient_spinner);

        // Set the custom adapter for mealTimeSpinner
        ArrayAdapter<CharSequence> cookMethodAdapter = ArrayAdapter.createFromResource(this,
                R.array.cook_method, R.layout.spinner_item_center);
        cookMethodAdapter.setDropDownViewResource(R.layout.spinner_item_center);
        cookMethodSpinner.setAdapter(cookMethodAdapter);

        // Set the custom adapter for categorySpinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.food_categories, R.layout.spinner_item_center);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item_center);
        categorySpinner.setAdapter(categoryAdapter);

        // Set the custom adapter for nutrientSpinner
        ArrayAdapter<CharSequence> nutrientAdapter = ArrayAdapter.createFromResource(this,
                R.array.nutrient_array, R.layout.spinner_item_center);
        nutrientAdapter.setDropDownViewResource(R.layout.spinner_item_center);
        nutrientSpinner.setAdapter(nutrientAdapter);

        // 스피너 선택 리스너 설정
        cookMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCookMethod = parent.getItemAtPosition(position).toString();
                filterRecipes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCookMethod = "조리";
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                filterRecipes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "전체";
            }
        });

        nutrientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNutrient = parent.getItemAtPosition(position).toString();
                filterRecipes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedNutrient = "영양소";
            }
        });

        // 검색 버튼
        ImageButton searchButton = findViewById(R.id.ic_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery(searchView.getQuery(), true);
                searchView.clearFocus(); // 키보드 닫기
            }
        });

        dbManager = new RecipeDBManager(this);
        recipeList = new ArrayList<>();

        adapter = new RecipeAdapter(recipeList);
        recipeRecyclerView.setAdapter(adapter);

        // 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                searchView.setQuery(recipe.getRecipeName(), false);
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class);
                intent.putExtra("Recipe",recipe ); // 필터링된 Recipe List를 전달
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRecipes();
                searchView.clearFocus(); // 키보드 닫기
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes();
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && searchView.getQuery().length() == 0) {
                    searchView.clearFocus();
                }
            }
        });

        // RecyclerView의 빈 공간을 누르면 포커스 해제
        recipeRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchView.clearFocus();
                return false;
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        // 음식 버튼
        ImageButton dietButton = findViewById(R.id.ic_diet);
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, DietActivity.class); // 음식 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // SearchView 이외의 다른 공간을 누르면 키보드를 숨기고 검색창을 숨깁니다.
        RelativeLayout mainLayout = findViewById(R.id.main_layout); // main_layout의 ID를 XML과 일치시킵니다
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
            }
        });

        // 레시피 데이터 초기 로드
        loadRecipes();
    }

    private void loadRecipes() {
        try {
            recipeList = dbManager.getRecipesByQuery("");
            adapter.filter(recipeList);
        } catch (Exception e) {
            Log.e("DietActivity", "Error loading recipes", e);
        }
    }

    private void filterRecipes() {
        String query = searchView.getQuery().toString();
        List<Recipe> filteredRecipes = new ArrayList<>();
        int condition=0;
        switch (selectedNutrient) {
            case "칼로리":
                selectedNutrient = "calories";
                break;
            case "단백질":
                selectedNutrient = "protein";
                break;
            case "지방":
                selectedNutrient = "fat";
                break;
            case "탄수화물":
                selectedNutrient = "carbohydrate";
                break;
            case "나트륨":
                selectedNutrient = "sodium";
                break;
        }
        // 초기 필터링: 조리 방법과 카테고리로 필터링된 레시피 리스트 가져오기
        if (!Objects.equals(selectedCookMethod, "조리") ){
            condition+=1;
        }

        if(!Objects.equals(selectedCategory, "전체")){
            condition+=2;
        }

        if(!Objects.equals(selectedNutrient, "영양소")){
            condition+=4;
        }

        if(condition==0) {
            filteredRecipes = dbManager.getRecipes(null, true, 1000, null, null);
        }
        if(condition==1){
            filteredRecipes = dbManager.getRecipes(null, true, 1000, selectedCookMethod, null);
        } else if(condition==2){
            filteredRecipes = dbManager.getRecipes(null, true, 1000, null, selectedCategory);
        } else if(condition==3){
            filteredRecipes = dbManager.getRecipes(null, true, 1000, selectedCookMethod, selectedCategory);
        } else if(condition==4){
            filteredRecipes = dbManager.getRecipes(selectedNutrient, true, 1000, null, null);
        } else if(condition==5){
            filteredRecipes = dbManager.getRecipes(selectedNutrient, true, 1000, selectedCookMethod, null);
        } else if(condition==6){
            filteredRecipes = dbManager.getRecipes(selectedNutrient, true, 1000, null, selectedCategory);
        } else if(condition==7){
            filteredRecipes = dbManager.getRecipes(selectedNutrient, true, 1000, selectedCookMethod, selectedCategory);
        }

        // 이름으로 추가 필터링
        if (!query.isEmpty()) {
            filteredRecipes = filterByName(filteredRecipes, query);
        }

        adapter.filter(filteredRecipes);

    }

    private List<Recipe> filterByName(List<Recipe> recipes, String name) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeName() != null && recipe.getRecipeName().toLowerCase().contains(name.toLowerCase())) {
                filteredRecipes.add(recipe);
            }
        }
        return filteredRecipes;
    }

    private List<Recipe> filterByNutrient(List<Recipe> recipes, String nutrient) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            Recipe.Nutrients nutrients = recipe.getNutrients();
            if (nutrients != null) {
                double value = 0;
                switch (nutrient) {
                    case "칼로리":
                        value = nutrients.getCalories();
                        break;
                    case "단백질":
                        value = nutrients.getProtein();
                        break;
                    case "지방":
                        value = nutrients.getFat();
                        break;
                    case "탄수화물":
                        value = nutrients.getCarbohydrate();
                        break;
                    case "나트륨":
                        value = nutrients.getSodium();
                        break;
                }
                if (value > 0) {
                    filteredRecipes.add(recipe);
                }
            }
        }
        return filteredRecipes;
    }
}
