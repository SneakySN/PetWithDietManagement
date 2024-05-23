package com.example.petwithdietmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

/*
import com.example.petwithdietmanagement.CalendarActivity;
import com.example.petwithdietmanagement.DietActivity;
import com.example.petwithdietmanagement.MenuPageActivity;
import com.example.petwithdietmanagement.MyPageActivity;
import com.example.petwithdietmanagement.PetMenuActivity;
import com.example.petwithdietmanagement.MainActivity;
import com.example.petwithdietmanagement.R;
 */

public class DietActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet); // XML 레이아웃 이름을 입력하세요



        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, MainActivity.class); // 홈으로 이동
                startActivity(intent);
                overridePendingTransition(0,0);
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
                overridePendingTransition(0,0);
            }
        });

        // 캘린더 버튼
        ImageButton calendarButton = findViewById(R.id.ic_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, CalendarActivity.class); // 캘린더 페이지로 이동
                intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
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




        // 한식
        ImageButton koreanFoodButton = findViewById(R.id.ic_korean_food);
        koreanFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class); // 펫 메뉴 페이지로 이동
                intent.putExtra("tag","0"); // 식단 상세 페이지에 tag값 전달
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        // 일식
        ImageButton japaneseFoodButton = findViewById(R.id.ic_japanese_food);
        japaneseFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, SpecifiedDietActivity.class); // 펫 메뉴 페이지로 이동
                intent.putExtra("tag","1"); // 식단 상세 페이지에 tag값 전달
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
                intent.putExtra("tag","2"); // 식단 상세 페이지에 tag값 전달
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });



    }
}
