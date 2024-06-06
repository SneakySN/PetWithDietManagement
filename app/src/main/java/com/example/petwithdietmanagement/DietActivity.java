package com.example.petwithdietmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petwithdietmanagement.data.Recipe;
import com.example.petwithdietmanagement.database.RecipeDBHelper;
import com.example.petwithdietmanagement.database.RecipeDBManager;
import com.example.petwithdietmanagement.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class DietActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecipeDBManager dbManager;
    private List<Recipe> recipeList;
    private RecipeAdapter adapter;
    private RecyclerView recipeRecyclerView;

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipes(query);
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class);
                intent.putParcelableArrayListExtra("Recipes", new ArrayList<>(adapter.getFilteredList())); // 필터링된 Recipe List를 전달
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return true;
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

        // 한식 버튼
        ImageButton koreanFoodButton = findViewById(R.id.ic_korean_food);
        koreanFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class); // 펫 메뉴 페이지로 이동
                intent.putExtra("tag", "0"); // 식단 상세 페이지에 tag값 전달
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 일식 버튼
        ImageButton japaneseFoodButton = findViewById(R.id.ic_japanese_food);
        japaneseFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class); // 펫 메뉴 페이지로 이동
                intent.putExtra("tag", "1"); // 식단 상세 페이지에 tag값 전달
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 양식 버튼
        ImageButton westernFoodButton = findViewById(R.id.ic_western_food);
        westernFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class); // 펫 메뉴 페이지로 이동
                intent.putExtra("tag", "2"); // 식단 상세 페이지에 tag값 전달
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // SearchView 이외의 다른 공간을 누르면 키보드를 숨깁니다.
        RelativeLayout mainLayout = findViewById(R.id.main_layout); // main_layout의 ID를 XML과 일치시킵니다
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
            }
        });
    }

    private void searchRecipes(String query) {
        Cursor cursor = dbManager.getRecipesByName(query);
        List<Recipe> filteredRecipes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDBHelper.COLUMN_RECIPE_NAME)));
                // 필요한 다른 필드를 설정할 수 있습니다.
                filteredRecipes.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.filter(filteredRecipes);
    }

    private void filterRecipes(String query) {
        searchRecipes(query);
    }
}
