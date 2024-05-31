package com.example.petwithdietmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petwithdietmanagement.database.RecipeDBManager;

import java.util.ArrayList;
import java.util.List;

public class DietActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecipeDBManager dbManager;
    private List<String> recipeList;

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipes(query);
                Log.d("DietActivity", "리스트: " + recipeList);
                Toast.makeText(DietActivity.this, "검색 처리됨 : " + query, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.clearFocus(); // 키보드 닫기
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // @TODO: 검색어가 변경되었을 때 수행할 작업
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
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
            }
        });

        // 펫 메뉴 버튼
        ImageButton petMenuButton = findViewById(R.id.ic_petMenu);
        petMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, PetMenuActivity.class); // 펫 메뉴 페이지로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
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
        recipeList.clear();
        if (cursor.moveToFirst()) {
            do {
                String recipeName = cursor.getString(cursor.getColumnIndexOrThrow("recipe_name"));
                recipeList.add(recipeName);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
