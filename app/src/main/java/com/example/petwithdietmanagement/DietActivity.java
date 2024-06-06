package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
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

public class DietActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecipeDBManager dbManager;
    private List<Recipe> recipeList;
    private RecipeAdapter adapter;
    private RecyclerView recipeRecyclerView;
    private Spinner mealTimeSpinner, categorySpinner;
    private String selectedMealTime, selectedCategory;

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

        mealTimeSpinner = findViewById(R.id.meal_time_spinner);
        categorySpinner = findViewById(R.id.category_spinner);

        // 스피너 선택 리스너 설정
        mealTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMealTime = parent.getItemAtPosition(position).toString();
                filterRecipes(searchView.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMealTime = null;
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                filterRecipes(searchView.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = null;
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
                intent.putExtra("Recipe", searchView.getQuery()); // 필터링된 Recipe List를 전달
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipes(query);
                ArrayList<Recipe> filteredRecipes = new ArrayList<>(adapter.getFilteredList());
                String RecipeName = null;
                // 검색어와 일치하는 레시피가 있는지 확인
                boolean isRecipeFound = false;
                for (Recipe recipe : filteredRecipes) {
                    if (query.equals(recipe.getRecipeName())) {
                        isRecipeFound = true;
                        RecipeName = recipe.getRecipeName();
                        break;
                    }
                }

                // 일치하는 레시피가 있으면 포커스 해제
                if (isRecipeFound) {
                    Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class);
                    intent.putExtra("Recipe", RecipeName); // 필터링된 Recipe List를 전달
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                searchView.clearFocus(); // 검색 후 포커스 해제
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
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
    }

    private void searchRecipes(String query) {
        List<Recipe> filteredRecipes = dbManager.getRecipesByQuery(query);
        adapter.filter(filteredRecipes);
    }

    private void filterRecipes(String query) {
        searchRecipes(query);
        searchView.setVisibility(View.VISIBLE); // 검색 중에는 검색창 보이기
    }
}
